package com.group15.tourassist.repository;

import com.group15.tourassist.entity.DestinationMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDestinationMasterRepository extends JpaRepository<DestinationMaster, Long> {
    List<DestinationMaster> findAll();
}
