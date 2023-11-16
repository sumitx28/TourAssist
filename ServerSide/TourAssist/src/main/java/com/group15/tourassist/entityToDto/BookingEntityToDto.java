package com.group15.tourassist.entityToDto;

import com.group15.tourassist.dto.AgentDetailsDTO;
import com.group15.tourassist.dto.BookingDTO;
import com.group15.tourassist.entity.Agent;
import com.group15.tourassist.entity.Booking;
import com.group15.tourassist.repository.IAgentRepository;
import com.group15.tourassist.repository.ICustomerRepository;
import com.group15.tourassist.repository.IPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingEntityToDto {

    @Autowired
    private IPackageRepository packageRepository;

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private IAgentRepository agentRepository;

    public BookingDTO bookingEntityToDto(Booking booking){
    BookingDTO bookingDTO= new BookingDTO();
    bookingDTO.setId(booking.getId());
    bookingDTO.setPackageD(packageRepository.findById(booking.getPackageId()));
    bookingDTO.setCustomer(customerRepository.findById(booking.getCustomerId()));
    bookingDTO.setAgent(agentRepository.findById(booking.getAgentId()));
    bookingDTO.setBookingDate(booking.getBookingDate());
    bookingDTO.setTotalPrice(booking.getTotalPrice());
    bookingDTO.setBookingStatus(booking.getBookingStatus());

    return bookingDTO;
    }
}
