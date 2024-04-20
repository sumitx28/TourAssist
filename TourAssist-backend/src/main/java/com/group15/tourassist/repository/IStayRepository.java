package com.group15.tourassist.repository;

import com.group15.tourassist.entity.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IStayRepository extends JpaRepository<Stay, Long> {

    @Query("SELECT s FROM Stay s WHERE s.packageId = :packageId")
    Stay getStayDetailsByPackageId(@Param("packageId") Long packageId);
}
