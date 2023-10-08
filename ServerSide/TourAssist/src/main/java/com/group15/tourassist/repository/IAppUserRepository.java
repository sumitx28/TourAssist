package com.group15.tourassist.repository;


import com.group15.tourassist.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAppUserRepository extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findByEmail(String email);

}
