package com.fiona.instustionsManagement.courses;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CoursesRepository extends JpaRepository<CoursesModel, UUID> {
    List<CoursesModel> findByCourseNameContainingAndInstitutionsModelInstitutionName(String keyword, String institutionName);
}

