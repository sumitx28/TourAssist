package com.group15.tourassist.repository;


import com.group15.tourassist.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface IAppUserRepository extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findByEmail(String email);

    /**
     * @param userId   id of the appUser for which update is to be performed
     * @param newEmail new email which is to be updated
     */
    @Transactional
    @Modifying
    @Query("UPDATE AppUser u SET u.email = :newEmail WHERE u.id = :userId")
    void updateAppUserEmailById(@Param("userId") Long userId, @Param("newEmail") String newEmail);

}
