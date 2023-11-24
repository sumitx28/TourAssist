package entityToDto;

import com.group15.tourassist.dto.AgentDetailsDTO;
import com.group15.tourassist.entity.Agent;
import com.group15.tourassist.entityToDto.AgentEntityToDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AgentEntityToDtoTest {

    private AgentEntityToDto agentEntityToDto;

    @BeforeEach
    public void setUp() {
        agentEntityToDto = new AgentEntityToDto();
    }

    @Test
    public void testAgentEntityToDto() {
        Agent agent = new Agent();
        agent.setId(1L);
        agent.setCompanyName("Test Company");
        agent.setMobile("123-456-7890");

        AgentDetailsDTO agentDetailsDTO = agentEntityToDto.agentEntityToDto(agent);

        assertEquals(1L, agentDetailsDTO.getAgentId());
        assertEquals("Test Company", agentDetailsDTO.getCompanyName());
        assertEquals("123-456-7890", agentDetailsDTO.getMobile());
    }
}