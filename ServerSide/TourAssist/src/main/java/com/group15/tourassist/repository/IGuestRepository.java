package com.group15.tourassist.repository;

import com.group15.tourassist.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGuestRepository extends JpaRepository<Guest, Long> {
}
