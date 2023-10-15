package com.group15.tourassist.repository;

import com.group15.tourassist.entity.ActivityMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IActivityMasterRepository extends JpaRepository<ActivityMaster, Long> {
    List<ActivityMaster> findAll();
}
