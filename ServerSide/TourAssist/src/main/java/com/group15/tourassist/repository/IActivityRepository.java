package com.group15.tourassist.repository;

import com.group15.tourassist.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IActivityRepository extends JpaRepository<Activity, Long> {
}
