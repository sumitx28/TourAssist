package com.group15.tourassist.repository;


import com.group15.tourassist.entity.ResortMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IResortMasterRepository extends JpaRepository<ResortMaster, Long> {

    List<ResortMaster> findAllByDestinationMaster_Id(Long id);
}
