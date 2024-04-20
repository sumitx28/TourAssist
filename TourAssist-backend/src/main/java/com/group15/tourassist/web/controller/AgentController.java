package com.group15.tourassist.web.controller;

import com.group15.tourassist.entity.Agent;
import com.group15.tourassist.service.IAgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class AgentController {

    Logger log = LoggerFactory.getLogger(AgentController.class);

    @Autowired
    IAgentService agentService;

    @GetMapping("/agent/{appUserId}")
    private ResponseEntity<Agent> getAgentDetails(@PathVariable Long appUserId) {
        log.info("** get customer request for app_user_id {}", appUserId);

        Agent agent = agentService.getAgentByAppUserId(appUserId);
        return ResponseEntity.of(Optional.of(agent));
    }
}
