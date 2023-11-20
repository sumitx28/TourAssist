package com.group15.tourassist.repository;

import com.group15.tourassist.entity.PackageMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IPackageMediaRepository extends JpaRepository<PackageMedia, Long> {
    List<PackageMedia> findByPackageId(Long id);
}
