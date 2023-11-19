package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.BookedItem;
import com.group15.tourassist.core.enums.BookingStatus;
import com.group15.tourassist.core.enums.TransactionStatus;
import com.group15.tourassist.dto.ActivityMinDTO;
import com.group15.tourassist.dto.BookingDetailsDTO;
import com.group15.tourassist.dto.GuideDTO;
import com.group15.tourassist.dto.TransportationMinDTO;
import com.group15.tourassist.entity.*;
import com.group15.tourassist.entity.Package;
import com.group15.tourassist.entityToDto.AgentEntityToDto;
import com.group15.tourassist.entityToDto.CustomerEntityToDto;
import com.group15.tourassist.entityToDto.PackageEntityToDto;
import com.group15.tourassist.repository.*;
import com.group15.tourassist.request.BookingRequest;
import com.group15.tourassist.response.BookingDetailsWebResponse;
import com.group15.tourassist.response.BookingResponse;
import com.group15.tourassist.response.CustomerDetailsBookedByAgentIDResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements IBookingService {
    private final IResortMasterRepository resortMasterRepository;
    private final IActivityRepository activityRepository;
    private final ITourGuideRepository tourGuideRepository;
    private final IGuideMasterRepository guideMasterRepository;
    private final IBookingLineItemService bookingLineItemService;
    private final IBookingRepository bookingRepository;
    private final IGuestService guestService;
    private final ICustomerRepository customerRepository;
    private final IPackageRepository packageRepository;
    private final IActivityMasterRepository activityMasterRepository;
    private final ITransportationRepository transportationRepository;
    private final ITravelModeMasterRepository travelModeMasterRepository;
    private final IAgentRepository agentRepository;
    private final PackageEntityToDto packageEntityToDto;
    private final CustomerEntityToDto customerEntityToDto;
    private final AgentEntityToDto agentEntityToDto;
    Logger log = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    public BookingService(
            IResortMasterRepository resortMasterRepository,
            IActivityRepository activityRepository,
            ITourGuideRepository tourGuideRepository,
            IGuideMasterRepository guideMasterRepository,
            IBookingLineItemService bookingLineItemService,
            IBookingRepository bookingRepository,
            IGuestService guestService,
            ICustomerRepository customerRepository,
            IPackageRepository packageRepository,
            IActivityMasterRepository activityMasterRepository,
            ITransportationRepository transportationRepository,
            ITravelModeMasterRepository travelModeMasterRepository,
            IAgentRepository agentRepository, PackageEntityToDto packageEntityToDto, CustomerEntityToDto customerEntityToDto, AgentEntityToDto agentEntityToDto) {
        this.resortMasterRepository = resortMasterRepository;
        this.activityRepository = activityRepository;
        this.tourGuideRepository = tourGuideRepository;
        this.guideMasterRepository = guideMasterRepository;
        this.bookingLineItemService = bookingLineItemService;
        this.bookingRepository = bookingRepository;
        this.guestService = guestService;
        this.customerRepository = customerRepository;
        this.packageRepository = packageRepository;
        this.activityMasterRepository = activityMasterRepository;
        this.transportationRepository = transportationRepository;
        this.travelModeMasterRepository = travelModeMasterRepository;
        this.agentRepository = agentRepository;
        this.packageEntityToDto = packageEntityToDto;
        this.customerEntityToDto = customerEntityToDto;
        this.agentEntityToDto = agentEntityToDto;
    }

    /**
     * @param bookingRequest request to create booking for
     * @return booking id
     */
    @Override
    public Long createBooking(BookingRequest bookingRequest) {
        // compute total price
        Double totalPrice = bookingLineItemService.computeTotalPrice(bookingRequest.getBookingItemRequests());

        // persist booking entity
        Booking booking = Booking.getBookingFromRequest(bookingRequest, totalPrice, BookingStatus.PENDING);
        booking = bookingRepository.save(booking);

        // persist booking line items
        bookingLineItemService.createBookingLineItems(bookingRequest.getBookingItemRequests(), booking);

        // persists guests
        guestService.createGuests(bookingRequest.getGuests(), booking);

        return booking.getId();
    }


    /**
     * @param bookingId bookingId to be queried
     * @return Booking entity corresponding to the id
     */
    @Override
    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId).get();
    }

    /**
     * @param bookingId         bookingId for which status should be updated
     * @param transactionStatus corresponding transaction status
     */
    @Override
    public void updateBookingStatus(Long bookingId, TransactionStatus transactionStatus) {
        BookingStatus bookingStatus = transactionStatus.equals(TransactionStatus.SUCCESS) ? BookingStatus.CONFIRM : BookingStatus.PENDING;
        bookingRepository.updateBookingStatus(bookingId, bookingStatus.toString());
    }

    @Override
    public BookingDetailsWebResponse getAllBookingForCustomer(Long appUserId) {
        BookingDetailsWebResponse bookingDetailsWebResponse = new BookingDetailsWebResponse();

        List<BookingDetailsDTO> bookingDetailsDTOList = new LinkedList<>();
        Optional<Customer> customerOptional = customerRepository.getCustomerByAppUserId(appUserId);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            List<Booking> bookingDetailsList = bookingRepository.getBookingsByCustomerId(customer.getId());

            if (!bookingDetailsList.isEmpty()) {
                for (Booking booking : bookingDetailsList) {
                    BookingDetailsDTO bookingDetailsDTO = new BookingDetailsDTO();
                    populateEachBookingDetailDTO(booking, bookingDetailsDTO);
                    bookingDetailsDTOList.add(bookingDetailsDTO);
                }
                bookingDetailsWebResponse.setBookingDetailsList(bookingDetailsDTOList);
            } else {
                log.info("No Bookings found for appUserId: {} ", appUserId);
            }

        } else {
            log.info("Invalid appUseId: {}, no customer found !", appUserId);
        }
        return bookingDetailsWebResponse;
    }

    /**
     * populates each booking (both pending and confirmed) of the customer
     *
     * @param booking
     * @param bookingDetailsDTO
     */
    private void populateEachBookingDetailDTO(Booking booking, BookingDetailsDTO bookingDetailsDTO) {
        bookingDetailsDTO.setPackageId(booking.getPackageId());
        bookingDetailsDTO.setBookingDate(booking.getBookingDate());
        bookingDetailsDTO.setTotalPrice(booking.getTotalPrice());
        bookingDetailsDTO.setBookingStatus(booking.getBookingStatus());

        Optional<Package> packageOptional = packageRepository.findById(booking.getPackageId());
        if (packageOptional.isPresent()) {
            log.info("package is present, will populate data");
            Package travelPackage = packageOptional.get();
            bookingDetailsDTO.setPackageName(travelPackage.getPackageName());

            List<BookingLineItem> bookingLineItemList = bookingLineItemService.getBookingLineItemsByBookingId(booking.getId());
            List<ActivityMinDTO> activityMinDTOS = new LinkedList<>();
            List<GuideDTO> guideDTOS = new LinkedList<>();
            List<TransportationMinDTO> transportationMinDTOS = new LinkedList<>();

            for (BookingLineItem bookingLineItem : bookingLineItemList) {
                populateActivityDTOs(activityMinDTOS, bookingLineItem);
                populateGuideDTOs(guideDTOS, bookingLineItem);
                populateTransportationDTOs(transportationMinDTOS, bookingLineItem);
                populateGuideDTO(bookingDetailsDTO, bookingLineItem);

            }
            bookingDetailsDTO.setActivityMinDTOS(activityMinDTOS);
            bookingDetailsDTO.setGuideDTOS(guideDTOS);
            bookingDetailsDTO.setTransportationMinDTOS(transportationMinDTOS);
        }
    }

    private void populateTransportationDTOs(List<TransportationMinDTO> transportationMinDTOS, BookingLineItem bookingLineItem) {
        if (BookedItem.TRANSPORTATION.equals(bookingLineItem.getBookedItem())) {
            TransportationMinDTO transportationMinDTO = new TransportationMinDTO();
            Long transportationId = bookingLineItem.getBookedItemId();
            Optional<Transportation> transportationOptional = transportationRepository.findById(transportationId);
            if (transportationOptional.isPresent()) {
                Transportation transportation = transportationOptional.get();
                Optional<TravelModeMaster> travelModeMasterOptional = travelModeMasterRepository.findById(transportation.getModeMasterId());
                if (travelModeMasterOptional.isPresent()) {
                    TravelModeMaster transportationMaster = travelModeMasterOptional.get();
                    transportationMinDTO.setTransportationId(transportation.getId());
                    transportationMinDTO.setTransportationName(transportationMaster.getMode());
                }
            }
            transportationMinDTOS.add(transportationMinDTO);
        }
    }

    private void populateGuideDTO(BookingDetailsDTO bookingDetailsDTO, BookingLineItem bookingLineItem) {
        if (BookedItem.RESORT.equals(bookingLineItem.getBookedItem())) {
            Long resortId = bookingLineItem.getBookedItemId();
            Optional<ResortMaster> resortMasterOptional = resortMasterRepository.findById(resortId);
            if (resortMasterOptional.isPresent()) {
                ResortMaster resortMaster = resortMasterOptional.get();
                bookingDetailsDTO.setResortDetails(resortMaster);
            }
        }
    }

    private void populateGuideDTOs(List<GuideDTO> guideDTOS, BookingLineItem bookingLineItem) {
        if (BookedItem.GUIDE.equals(bookingLineItem.getBookedItem())) {
            GuideDTO guideDTO = new GuideDTO();
            Long guideId = bookingLineItem.getBookedItemId();
            Optional<TourGuide> tourGuideOptional = tourGuideRepository.findById(guideId);
            if (tourGuideOptional.isPresent()) {
                TourGuide tourGuide = tourGuideOptional.get();
                Optional<GuideMaster> guideMasterOptional = guideMasterRepository.findById(tourGuide.getGuideMasterId());
                if (guideMasterOptional.isPresent()) {
                    GuideMaster guideMaster = guideMasterOptional.get();
                    guideDTO.setGuideName(guideMaster.getGuideName());
                    guideDTO.setGuideId(tourGuide.getId());
                }
            }
            guideDTOS.add(guideDTO);
        }
    }

    private void populateActivityDTOs(List<ActivityMinDTO> activityMinDTOS, BookingLineItem bookingLineItem) {
        if (BookedItem.ACTIVITY.equals(bookingLineItem.getBookedItem())) {
            ActivityMinDTO activityMinDTO = new ActivityMinDTO();
            Long activityId = bookingLineItem.getBookedItemId();

            if (activityRepository.findById(activityId).isPresent()) {
                Activity activity = activityRepository.findById(activityId).get();
                Optional<ActivityMaster> activityMasterOptional = activityMasterRepository.findById(activity.getActivityMasterId());
                if (activityMasterOptional.isPresent()) {
                    ActivityMaster activityMaster = activityMasterOptional.get();
                    activityMinDTO.setActivityId(activity.getId());
                    activityMinDTO.setActivityName(activityMaster.getActivityName());
                }
            }
            activityMinDTOS.add(activityMinDTO);
        }
    }
    @Override
    public List<BookingResponse> getPastBookings(Long agentId) {
        java.sql.Date date=new java.sql.Date(System.currentTimeMillis());
        List<BookingResponse> response= new ArrayList<>();
        List<Booking> allBookings= bookingRepository.findAllByPastBookingDates(date, agentId);
        for (Booking booking:allBookings) {
            BookingResponse bookingResponse= new BookingResponse();
            bookingResponse.setId(booking.getId());
            bookingResponse.setPackageD(packageEntityToDto.packageEntityToDto(packageRepository.findById(booking.getPackageId()).get()));
            log.info("completed package");
            log.info(packageRepository.findById(booking.getPackageId()).get().toString());
            bookingResponse.setCustomer(customerEntityToDto.customerEntityToDto(customerRepository.findById(booking.getCustomerId()).get()));
            log.info("completed customer");

            bookingResponse.setAgent(agentEntityToDto.agentEntityToDto(agentRepository.findById(booking.getAgentId()).get()));
            log.info("3");

            bookingResponse.setBookingDate(booking.getBookingDate());
            log.info("4");

            bookingResponse.setTotalPrice(booking.getTotalPrice());
            log.info("5");

            bookingResponse.setBookingStatus(booking.getBookingStatus());
            log.info("6");
            response.add(bookingResponse);
        }
        log.info(response.toString());
        return response;
    }

    @Override
    public List<BookingResponse> getUpcomingBookings(Long agentId) {
        java.sql.Date date=new java.sql.Date(System.currentTimeMillis());
        List<BookingResponse> response= new ArrayList<>();
        List<Booking> allBookings= bookingRepository.findAllByUpcomingBookingDates(date,agentId);
        for (Booking booking1:allBookings
        ) {
            BookingResponse bookingResponse= new BookingResponse();
            bookingResponse.setId(booking1.getId());
            bookingResponse.setPackageD(packageEntityToDto.packageEntityToDto(packageRepository.findById(booking1.getPackageId()).get()));
            bookingResponse.setCustomer(customerEntityToDto.customerEntityToDto(customerRepository.findById(booking1.getCustomerId()).get()));
            bookingResponse.setAgent(agentEntityToDto.agentEntityToDto(agentRepository.findById(booking1.getAgentId()).get()));
            bookingResponse.setBookingDate(booking1.getBookingDate());
            bookingResponse.setTotalPrice(booking1.getTotalPrice());
            bookingResponse.setBookingStatus(booking1.getBookingStatus());
            response.add(bookingResponse);
        }
        return response;
    }

    @Override
    public List<CustomerDetailsBookedByAgentIDResponse> getCustomersBookedByAgentID(Long agentId) {
        var bookings= bookingRepository.getCustomersBookedByAgentID(agentId);
        List<CustomerDetailsBookedByAgentIDResponse> response= new ArrayList<>();
        for (Booking booking: bookings){
            CustomerDetailsBookedByAgentIDResponse customerDetailsBookedByAgentIDResponse= new CustomerDetailsBookedByAgentIDResponse();
            customerDetailsBookedByAgentIDResponse.setAgent(agentEntityToDto.agentEntityToDto(agentRepository.findById(booking.getAgentId()).get()));
            customerDetailsBookedByAgentIDResponse.setCustomer(customerRepository.findById(booking.getCustomerId()).get());
            response.add(customerDetailsBookedByAgentIDResponse);
        }
        return response;
    }
}
