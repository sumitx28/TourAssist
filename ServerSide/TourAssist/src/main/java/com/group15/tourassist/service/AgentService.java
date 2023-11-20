package com.group15.tourassist.service;

import com.group15.tourassist.dto.AgentDetailsDTO;
import com.group15.tourassist.entity.Agent;
import com.group15.tourassist.repository.IAgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentService implements IAgentService {

    private final IAgentRepository agentRepository;

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

    /**
     * @param appUserId appUserId to fetch Agent details
     * @return Agent for the appUserId
     */
    @Override
    public Agent getAgentByAppUserId(Long appUserId) {
        return agentRepository.findByAppUserId(appUserId);
    }
}
