package com.group15.tourassist.repository;

import com.group15.tourassist.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAgentRepository extends JpaRepository<Agent, Long> {

}
