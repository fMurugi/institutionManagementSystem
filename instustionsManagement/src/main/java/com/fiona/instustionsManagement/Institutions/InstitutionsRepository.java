package com.fiona.instustionsManagement.Institutions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InstitutionsRepository extends JpaRepository<InstitutionsModel, UUID> {
    boolean existsByInstitutionName(String institutionName);
    Page<InstitutionsModel> findByInstitutionNameContainingIgnoreCase(String name, Pageable pageable);

    InstitutionsModel findByInstitutionName(String institutionName);

}
