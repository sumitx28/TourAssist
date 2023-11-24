package com.group15.tourassist.service;

import com.group15.tourassist.core.enums.*;
import com.group15.tourassist.dto.*;
import com.group15.tourassist.entity.Package;
import com.group15.tourassist.entity.*;
import com.group15.tourassist.entityToDto.AgentEntityToDto;
import com.group15.tourassist.entityToDto.CustomerEntityToDto;
import com.group15.tourassist.entityToDto.PackageEntityToDto;
import com.group15.tourassist.repository.*;
import com.group15.tourassist.request.BookingItemRequest;
import com.group15.tourassist.request.BookingRequest;
import com.group15.tourassist.response.BookingDetailsWebResponse;
import com.group15.tourassist.response.CustomerDetailsBookedByAgentIDResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private ITransportationRepository transportationRepository;
    @Mock
    private IResortMasterRepository resortMasterRepository;
    @Mock
    private ITourGuideRepository tourGuideRepository;
    @Mock
    private IGuideMasterRepository guideMasterRepository;
    @Mock
    private ITravelModeMasterRepository travelModeMasterRepository;
    @InjectMocks
    private BookingService bookingService;
    @Mock
    private IBookingRepository bookingRepository;
    @Mock
    private IActivityRepository activityRepository;
    @Mock
    private IActivityMasterRepository activityMasterRepository;
    @Mock
    private ICustomerRepository customerRepository;
    @Mock
    private BookingLineItemService bookingLineItemService;
    @Mock
    private GuestService guestService;
    @Mock
    private IGuestRepository guestRepository;
    @Mock
    private IPackageRepository packageRepository;
    @Mock
    private IAgentRepository agentRepository;
    @Mock
    private AgentEntityToDto agentEntityToDto;
    @Mock
    private PackageEntityToDto packageEntityToDto;

    @Mock
    private CustomerEntityToDto customerEntityToDto;

    private Booking confirmBooking;

    private BookingRequest bookingRequest;

    private BookingItemRequest bookingItemRequest;

    private Guest guest;

    private Booking pendingBooking;


    @BeforeEach
    public void setup() {
        confirmBooking = new Booking(1L, 1L, 2L, 3L, Instant.parse("2023-08-20T00:00:00Z"), 100D, BookingStatus.CONFIRM);
        bookingItemRequest = new BookingItemRequest(BookedItem.ACTIVITY, 1L);
        guest = new Guest(1L, null, "Raj", "Patel", Instant.parse("1996-02-11T00:00:00Z"));
        bookingRequest = new BookingRequest(1L, 2L, 3L, Collections.singletonList(bookingItemRequest), Collections.singletonList(guest));
        pendingBooking = new Booking(2L, 1L, 2L, 3L, Instant.parse("2023-08-20T00:00:00Z"), 100D, BookingStatus.PENDING);
        //MockitoAnnotations.initMocks(this);

    }

    @Test
    void testCreateBooking_PositiveCase() {
        // Arrange
        when(bookingRepository.save(any())).thenReturn(confirmBooking);
        when(bookingLineItemService.computeTotalPrice(any())).thenReturn(10d);

        // Act
        Long bookingId = bookingService.createBooking(bookingRequest);

        // Assert
        assertEquals(1L, bookingId);   // should return a non null booking_id (1 in this case) which gets stored in db.
    }

    @Test
    void testGetBookingById() {
        // Arrange
        when(bookingRepository.findById(1L)).thenReturn(Optional.ofNullable(confirmBooking));

        // Act
        Booking savedBooking = bookingService.getBookingById(1L);

        // Assert
        assertEquals(confirmBooking, savedBooking);
    }

    @Test
    void testUpdateBookingStatus() {
        // Arrange
        doNothing().when(bookingRepository).updateBookingStatus(pendingBooking.getId(), "CONFIRM");

        // Act
        bookingService.updateBookingStatus(pendingBooking.getId(), TransactionStatus.SUCCESS);

        // Assert -- Verify that updateBookingStatus gets called 1 time.
        verify(bookingRepository, times(1)).updateBookingStatus(pendingBooking.getId(), "CONFIRM");
    }

    @Test
    void testGetAllBookingForCustomer() {
        // Arrange
        long appUserId = 1L;
        Customer customer = new Customer();
        customer.setId(1L);
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setCustomerId(customer.getId());
        when(customerRepository.getCustomerByAppUserId(appUserId)).thenReturn(Optional.of(customer));
        when(bookingRepository.getBookingsByCustomerId(customer.getId())).thenReturn(Collections.singletonList(booking));

        // Act
        BookingDetailsWebResponse result = bookingService.getAllBookingForCustomer(appUserId);

        // Assert
        verify(customerRepository, times(1)).getCustomerByAppUserId(appUserId);
        verify(bookingRepository, times(1)).getBookingsByCustomerId(customer.getId());
    }

    @Test
    void testGetAllBookingForCustomerNoBookings() {
        // Arrange
        long appUserId = 1L;
        Customer customer = new Customer();
        customer.setId(1L);
        when(customerRepository.getCustomerByAppUserId(appUserId)).thenReturn(Optional.of(customer));
        when(bookingRepository.getBookingsByCustomerId(customer.getId())).thenReturn(Collections.emptyList());

        // Act
        BookingDetailsWebResponse result = bookingService.getAllBookingForCustomer(appUserId);

        // Assert
        assertNull(result.getBookingDetailsList());
        verify(customerRepository, times(1)).getCustomerByAppUserId(appUserId);
        verify(bookingRepository, times(1)).getBookingsByCustomerId(customer.getId());
    }


    @Test
    void testGetAllBookingForCustomerInvalidCustomer() {
        // Arrange
        long appUserId = 1L;
        when(customerRepository.getCustomerByAppUserId(appUserId)).thenReturn(Optional.empty());

        // Act
        BookingDetailsWebResponse result = bookingService.getAllBookingForCustomer(appUserId);

        // Assert
        assertNull(result.getBookingDetailsList());
        verify(customerRepository, times(1)).getCustomerByAppUserId(appUserId);
        verify(bookingRepository, never()).getBookingsByCustomerId(anyLong());
    }

    @Test
    void testPopulateEachBookingDetailDTO() {
        // Arrange
        Booking booking = new Booking();
        booking.setPackageId(1L);
        booking.setBookingDate(Instant.now());
        booking.setTotalPrice(10.0);
        booking.setBookingStatus(BookingStatus.CONFIRM);

        Package travelPackage = new Package();
        travelPackage.setPackageName("Test Package");

        BookingLineItem bookingLineItem = new BookingLineItem();
        bookingLineItem.setId(1L);

        // Mocking packageRepository
        when(packageRepository.findById(booking.getPackageId())).thenReturn(Optional.of(travelPackage));

        // Mocking bookingLineItemService
        when(bookingLineItemService.getBookingLineItemsByBookingId(booking.getId())).thenReturn(Collections.singletonList(bookingLineItem));

        // Act
        BookingDetailsDTO bookingDetailsDTO = new BookingDetailsDTO();
        bookingService.populateEachBookingDetailDTO(booking, bookingDetailsDTO);

        // Assert
        assertNotNull(bookingDetailsDTO.getPackageName());
        assertEquals("Test Package", bookingDetailsDTO.getPackageName());
        assertNotNull(bookingDetailsDTO.getActivityMinDTOS());
        assertNotNull(bookingDetailsDTO.getGuideDTOS());
        assertNotNull(bookingDetailsDTO.getTransportationMinDTOS());
        // Add more specific assertions based on your implementation.

        // Verify interactions with mocks
        verify(packageRepository, times(1)).findById(booking.getPackageId());
        verify(bookingLineItemService, times(1)).getBookingLineItemsByBookingId(booking.getId());
    }

    @Test
    void testPopulateTransportationDTOs() {
        // Arrange
        BookingLineItem bookingLineItem = new BookingLineItem();
        bookingLineItem.setBookedItem(BookedItem.TRANSPORTATION);
        bookingLineItem.setBookedItemId(1L);

        Transportation transportation = new Transportation();
        transportation.setId(1L);
        transportation.setModeMasterId(1L);

        TravelModeMaster travelModeMaster = new TravelModeMaster();
        travelModeMaster.setId(1L);
        travelModeMaster.setMode("Test Mode");

        // Mocking repositories
        when(transportationRepository.findById(bookingLineItem.getBookedItemId())).thenReturn(Optional.of(transportation));
        when(travelModeMasterRepository.findById(transportation.getModeMasterId())).thenReturn(Optional.of(travelModeMaster));

        // Act
        List<TransportationMinDTO> transportationMinDTOS = new ArrayList<>();
        bookingService.populateTransportationDTOs(transportationMinDTOS, bookingLineItem);

        // Assert
        assertEquals(1, transportationMinDTOS.size());
        TransportationMinDTO resultDTO = transportationMinDTOS.get(0);
        assertNotNull(resultDTO);
        assertEquals(transportation.getId(), resultDTO.getTransportationId());
        assertEquals(travelModeMaster.getMode(), resultDTO.getTransportationName());

        // Verify interactions with mocks
        verify(transportationRepository, times(1)).findById(bookingLineItem.getBookedItemId());
        verify(travelModeMasterRepository, times(1)).findById(transportation.getModeMasterId());
    }

    @Test
    void testPopulateGuideDTOs() {
        // Arrange
        List<GuideDTO> guideDTOs = new ArrayList<>();
        BookingLineItem bookingLineItem = new BookingLineItem();
        bookingLineItem.setBookedItem(BookedItem.GUIDE);
        bookingLineItem.setBookedItemId(1L);

        TourGuide tourGuide = new TourGuide();
        tourGuide.setId(1L);
        tourGuide.setGuideMasterId(1L);

        GuideMaster guideMaster = new GuideMaster();
        guideMaster.setId(1L);
        guideMaster.setGuideName("Test Guide");

        // Mocking repositories
        when(tourGuideRepository.findById(bookingLineItem.getBookedItemId())).thenReturn(Optional.of(tourGuide));
        when(guideMasterRepository.findById(tourGuide.getGuideMasterId())).thenReturn(Optional.of(guideMaster));

        // Act
        bookingService.populateGuideDTOs(guideDTOs, bookingLineItem);

        // Assert
        assertEquals(1, guideDTOs.size());
        GuideDTO resultDTO = guideDTOs.get(0);
        assertNotNull(resultDTO);
        assertEquals(guideMaster.getGuideName(), resultDTO.getGuideName());
        assertEquals(tourGuide.getId(), resultDTO.getGuideId());

        // Verify interactions with mocks
        verify(tourGuideRepository, times(1)).findById(bookingLineItem.getBookedItemId());
        verify(guideMasterRepository, times(1)).findById(tourGuide.getGuideMasterId());
    }

    @Test
    void testPopulateGuideDTO() {
        // Arrange
        BookingDetailsDTO bookingDetailsDTO = new BookingDetailsDTO();
        BookingLineItem bookingLineItem = new BookingLineItem();
        bookingLineItem.setBookedItem(BookedItem.RESORT);
        bookingLineItem.setBookedItemId(1L);

        ResortMaster resortMaster = new ResortMaster();
        resortMaster.setId(1L);
        resortMaster.setResortName("Test Resort");

        // Mocking repository
        when(resortMasterRepository.findById(bookingLineItem.getBookedItemId())).thenReturn(Optional.of(resortMaster));

        // Act
        bookingService.populateGuideDTO(bookingDetailsDTO, bookingLineItem);

        // Assert
        assertNotNull(bookingDetailsDTO.getResortDetails());
        assertEquals(resortMaster, bookingDetailsDTO.getResortDetails());

        // Verify interactions with mocks
        verify(resortMasterRepository, times(1)).findById(bookingLineItem.getBookedItemId());
    }

    @Test
    void testPopulateActivityDTOs() {
        // Arrange
        List<ActivityMinDTO> activityMinDTOs = new ArrayList<>();
        BookingLineItem bookingLineItem = new BookingLineItem();
        bookingLineItem.setBookedItem(BookedItem.ACTIVITY);
        bookingLineItem.setBookedItemId(1L);

        Activity activity = new Activity();
        activity.setId(1L);

        ActivityMaster activityMaster = new ActivityMaster();
        activityMaster.setId(1L);
        activityMaster.setActivityName("Test Activity");

        // Mocking repositories
        when(activityRepository.findById(bookingLineItem.getBookedItemId())).thenReturn(Optional.of(activity));
        when(activityMasterRepository.findById(activity.getActivityMasterId())).thenReturn(Optional.of(activityMaster));

        // Act
        bookingService.populateActivityDTOs(activityMinDTOs, bookingLineItem);

        // Assert
        assertEquals(1, activityMinDTOs.size());
        ActivityMinDTO resultDTO = activityMinDTOs.get(0);
        assertNotNull(resultDTO);
        assertEquals(activityMaster.getActivityName(), resultDTO.getActivityName());
        assertEquals(activity.getId(), resultDTO.getActivityId());

        // Verify interactions with mocks
        verify(activityRepository, times(2)).findById(bookingLineItem.getBookedItemId());
        verify(activityMasterRepository, times(1)).findById(activity.getActivityMasterId());
    }

    @Test
    void testGetCustomersBookedByAgentID() {
        Long agentId = 1L;
        Booking booking1 = Booking.builder()
                .packageId(1L)
                .customerId(2L)
                .agentId(3L)
                .bookingDate(Instant.now())
                .totalPrice(150.0)
                .bookingStatus(BookingStatus.CONFIRM)
                .build();

        Booking booking2 = Booking.builder()
                .packageId(1L)
                .customerId(2L)
                .agentId(3L)
                .bookingDate(Instant.now())
                .totalPrice(250.0)
                .bookingStatus(BookingStatus.CONFIRM)
                .build();

        Token token1 = Token.builder()
                .token("exampleToken123")
                .tokenType(TokenType.BEARER)
                .isRevoked(false)
                .isExpired(false)
                .build();

        Token token2 = Token.builder()
                .token("exampleToken1234")
                .tokenType(TokenType.BEARER)
                .isRevoked(false)
                .isExpired(false)
                .build();
        AppUser appUser1 = AppUser.builder()
                .email("user@example.com")
                .role(Role.CUSTOMER)
                .password("password123")
                .tokens(List.of(token1))
                .build();

        AppUser appUser2 = AppUser.builder()
                .email("user1@example.com")
                .role(Role.CUSTOMER)
                .password("password1234")
                .tokens(List.of(token1))
                .build();

        Agent agent1 = Agent.builder()
                .appUser(appUser1)
                .companyName("ABC Company")
                .mobile("1234567890")
                .employeeCount(10)
                .verificationId("ABC123")
                .verificationDocLink("https://example.com/doc.pdf")
                .build();

        Customer customer1 = Customer.builder()
                .appUser(appUser2)
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(Instant.parse("1990-01-01T00:00:00Z"))
                .country("USA")
                .mobile("1234567890")
                .build();

        AgentDetailsDTO agentDetailsDTO1 = AgentDetailsDTO.builder()
                .agentId(1L) // Replace with a valid agent ID
                .companyName("ABC Company") // Replace with a valid company name
                .mobile("1234567890") // Replace with a valid mobile number
                .build();


        List<Booking> bookings = List.of(booking1, booking2);

        Mockito.when(bookingRepository.getCustomersBookedByAgentID(anyLong())).thenReturn(bookings);
        Mockito.when(agentRepository.findById(anyLong())).thenReturn(Optional.of(agent1));
        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer1));
        Mockito.when(agentEntityToDto.agentEntityToDto(Mockito.any())).thenReturn(agentDetailsDTO1);

        List<CustomerDetailsBookedByAgentIDResponse> result = bookingService.getCustomersBookedByAgentID(agentId);

        assertEquals(2, result.size());
    }

}
