package com.group15.tourassist.repository;

import com.group15.tourassist.entity.TravelModeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITravelModeMasterRepository extends JpaRepository<TravelModeMaster, Long> {

    List<TravelModeMaster> findAll();

}
