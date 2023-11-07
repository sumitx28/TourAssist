package com.group15.tourassist.repository;

import com.group15.tourassist.dto.SourceDestinationDTO;
import com.group15.tourassist.entity.Package;
import com.group15.tourassist.request.CustomerSearchPackageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class PackageSearchRepository implements IPackageSearchRepository {

    Logger log = LoggerFactory.getLogger(PackageSearchRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Package> getPackagesForDateRange(CustomerSearchPackageRequest customerSearchPackageRequest, SourceDestinationDTO sourceDestinationDTO) {
        String jpql = "SELECT p " +
                "FROM Package p " +
                "INNER JOIN DestinationMaster source ON p.sourceId = source.id " +
                "INNER JOIN DestinationMaster destination ON p.destinationId = destination.id " +
                "WHERE source.city = :sourceCity " +
                "AND destination.city = :destinationCity " +
                "AND p.tripStartDate >= :startDate " +
                "AND p.tripEndDate <= :endDate";

        log.info("inside  getPackagesForDateRange: customerSearchPackageRequest {}",customerSearchPackageRequest);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        log.info("enttyiManage: {}", entityManager);

        TypedQuery<Package> query = entityManager.createQuery(jpql, Package.class);

        query.setParameter("sourceCity", customerSearchPackageRequest.getSourceCity());
        query.setParameter("destinationCity", customerSearchPackageRequest.getDestinationCity());
        query.setParameter("startDate", customerSearchPackageRequest.getPackageStartDate());
        query.setParameter("endDate", customerSearchPackageRequest.getPackageEndDate());

        return query.getResultList();

    }
}
