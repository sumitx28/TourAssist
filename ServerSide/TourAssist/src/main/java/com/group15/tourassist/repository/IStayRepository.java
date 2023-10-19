package com.group15.tourassist.repository;

import com.group15.tourassist.entity.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStayRepository extends JpaRepository<Stay, Long> {
}
