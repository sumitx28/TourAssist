package com.group15.tourassist.request;

import com.group15.tourassist.core.enums.BookedItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BookingItem request to denote which customizable items are being booked.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookingItemRequest {

    private BookedItem itemName;

    private Long itemId;
}
