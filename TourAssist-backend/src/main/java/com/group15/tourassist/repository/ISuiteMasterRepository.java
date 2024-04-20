package com.group15.tourassist.repository;

import com.group15.tourassist.entity.SuiteMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISuiteMasterRepository extends JpaRepository<SuiteMaster, Long> {
    List<SuiteMaster> findAll();
}
