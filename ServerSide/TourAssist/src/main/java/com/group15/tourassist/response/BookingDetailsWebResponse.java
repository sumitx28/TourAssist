package com.group15.tourassist.response;

import com.group15.tourassist.dto.BookingDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailsWebResponse {

    private List<BookingDetailsDTO> bookingDetailsList;
}
