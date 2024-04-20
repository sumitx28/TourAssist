package com.group15.tourassist.repository;

import com.group15.tourassist.entity.Transportation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransportationRepository extends JpaRepository<Transportation, Long> {

    @Query("SELECT t FROM Transportation t WHERE t.packageId = :packageId")
    Transportation getTransportationDetailsByPackageId(@Param("packageId") Long packageId);
}
