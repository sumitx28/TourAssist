package com.group15.tourassist.service;

import com.group15.tourassist.dto.AgentDetailsDTO;
import com.group15.tourassist.entity.Agent;

public interface IAgentService {

    /**
     * @param agentId id of the agent
     * @return the agent through id
     */
    Agent getAgentById(Long agentId);

    /**
     * @param agent
     * @return AgentDetailsDTO from the agent
     */
    AgentDetailsDTO populateAgentDetails(Agent agent);

    Agent getAgentByAppUserId(Long appUserId);

}
