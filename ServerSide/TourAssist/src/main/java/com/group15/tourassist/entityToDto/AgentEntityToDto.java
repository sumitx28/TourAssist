package com.group15.tourassist.entityToDto;

import com.group15.tourassist.dto.AgentDetailsDTO;
import com.group15.tourassist.entity.Agent;
import org.springframework.stereotype.Service;

@Service
public class AgentEntityToDto {

    public AgentDetailsDTO agentEntityToDto(Agent agent){
        AgentDetailsDTO agentDetailsDTO = new AgentDetailsDTO();
        agentDetailsDTO.setAgentId(agent.getId());
        agentDetailsDTO.setCompanyName(agent.getCompanyName());
        agentDetailsDTO.setMobile(agent.getMobile());

        return agentDetailsDTO;
    }
}
