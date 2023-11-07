package com.group15.tourassist.service;

import com.group15.tourassist.dto.AgentDetailsDTO;
import com.group15.tourassist.entity.Agent;
import com.group15.tourassist.repository.IAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentService implements IAgentService {

    private final IAgentRepository agentRepository;

    @Autowired
    public AgentService(IAgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    /**
     * @param agentId id of the agent
     * @return the agent through id
     */
    @Override
    public Agent getAgentById(Long agentId) {
        return agentRepository.findById(agentId)
                .orElse(null);
    }

    /**
     * @param agent
     * @return AgentDetailsDTO from the agent
     */
    @Override
    public AgentDetailsDTO populateAgentDetails(Agent agent) {
        AgentDetailsDTO agentDetailsDTO = new AgentDetailsDTO();
        if (agent == null) {
            return agentDetailsDTO;
        }
        agentDetailsDTO.setAgentId(agent.getId());
        agentDetailsDTO.setCompanyName(agent.getCompanyName());
        return agentDetailsDTO;

    }
}
