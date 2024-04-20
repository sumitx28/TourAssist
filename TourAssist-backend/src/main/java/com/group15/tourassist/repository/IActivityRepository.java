package com.group15.tourassist.repository;

import com.group15.tourassist.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IActivityRepository extends JpaRepository<Activity, Long> {

    @Query("SELECT a FROM Activity a WHERE a.packageId = :packageId")
    List<Activity> getActivityDetailsByPackageId(@Param("packageId") Long packageId);
}
