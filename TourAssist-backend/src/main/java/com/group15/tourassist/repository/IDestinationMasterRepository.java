package com.group15.tourassist.repository;

import com.group15.tourassist.dto.SourceDestinationDTO;
import com.group15.tourassist.entity.DestinationMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDestinationMasterRepository extends JpaRepository<DestinationMaster, Long> {
    List<DestinationMaster> findAll();

//    /**
//     *  filters based on the city names provided as
//     *  parameters (sourceCity and destinationCity), selects the id values,
//     *  and constructs instances of the SourceDestinationDTO class with those values
//     * @param sourceCity
//     * @param destinationCity
//     * @return retrieves data from the DestinationMaster table
//     */
//    @Query("SELECT NEW com.group15.tourassist.dto.SourceDestinationDTO(d.id, d.id) FROM DestinationMaster d WHERE d.city = :sourceCity OR d.city = :destinationCity")
//    SourceDestinationDTO getSourceDestinationId(String sourceCity, String destinationCity);
}
