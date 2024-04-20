package entityToDto;

import com.group15.tourassist.dto.CustomerDTO;
import com.group15.tourassist.entity.Customer;
import com.group15.tourassist.entityToDto.CustomerEntityToDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerEntityToDtoTest {

    private CustomerEntityToDto customerEntityToDto;

    @BeforeEach
    public void setUp() {
        customerEntityToDto = new CustomerEntityToDto();
    }

    @Test
    public void testCustomerEntityToDto() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setMobile("987-654-3210");
        customer.setDateOfBirth(Instant.parse("1990-01-01T00:00:00Z"));
        customer.setCountry("USA");

        CustomerDTO customerDTO = customerEntityToDto.customerEntityToDto(customer);

        assertEquals(1L, customerDTO.getCustomerId());
        assertEquals("John", customerDTO.getFirstName());
        assertEquals("Doe", customerDTO.getLastName());
        assertEquals("987-654-3210", customerDTO.getMobile());
        assertEquals(Instant.parse("1990-01-01T00:00:00Z"), customerDTO.getDateOfBirth());
        assertEquals("USA", customerDTO.getCountry());
    }
}

