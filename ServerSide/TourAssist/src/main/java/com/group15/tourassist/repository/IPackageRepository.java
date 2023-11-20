package com.group15.tourassist.repository;

import com.group15.tourassist.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface IPackageRepository extends JpaRepository<Package, Long> {

    @Query("SELECT p FROM Package p INNER JOIN DestinationMaster source ON p.sourceId = source.id INNER JOIN DestinationMaster destination ON p.destinationId = destination.id WHERE source.city = :sourceCity AND destination.city = :destinationCity AND p.tripStartDate >= :startDate AND p.tripEndDate <= :endDate")
    List<Package> getPackagesForDateRange(String sourceCity, String destinationCity, Instant startDate, Instant endDate);

    @Query("SELECT p FROM Package p INNER JOIN DestinationMaster source ON p.sourceId = source.id INNER JOIN DestinationMaster destination ON p.destinationId = destination.id WHERE source.city = :sourceCity AND destination.city = :destinationCity AND p.tripStartDate >= :startDate AND p.tripEndDate <= :endDate ORDER BY p.packageName")
    List<Package> getSortedPackagesForDateRange(String sourceCity, String destinationCity, Instant startDate, Instant endDate);

}

