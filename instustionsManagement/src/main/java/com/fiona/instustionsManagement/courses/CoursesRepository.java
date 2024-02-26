package com.fiona.instustionsManagement.courses;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CoursesRepository extends JpaRepository<CoursesModel, UUID> {
}
