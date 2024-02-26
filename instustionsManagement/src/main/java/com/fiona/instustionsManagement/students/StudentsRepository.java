package com.fiona.instustionsManagement.students;

import com.fiona.instustionsManagement.courses.CoursesModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface StudentsRepository extends JpaRepository<StudentsModel, UUID> {

    @Query("SELECT s FROM StudentsModel s JOIN s.coursesModel c JOIN c.institutionsModel i WHERE i.institutionName = :institutionName AND c.courseName = :courseName")
    Page<StudentsModel> findByInstitutionNameAndCourseName(String institutionName, String courseName, Pageable pageable);

    @Query("SELECT s FROM StudentsModel s JOIN s.coursesModel c JOIN c.institutionsModel i WHERE i.institutionName = :institutionName")
    Page<StudentsModel> findByInstitutionName(String institutionName, Pageable pageable);

    Page<StudentsModel> findByCoursesModel_InstitutionsModel_InstitutionNameAndCoursesModel_CourseName(String institutionName, String courseName, Pageable pageable);

    Page<StudentsModel> findByCoursesModel_InstitutionsModel_InstitutionName(String institutionName, Pageable pageable);

    Page<StudentsModel> findByCoursesModel_CourseName(String courseName, Pageable pageable);

    List<StudentsModel> findByCoursesModel(CoursesModel coursesModel);

    Page<StudentsModel> findByCoursesModel_CourseNameAndCoursesModel_InstitutionsModel_InstitutionName(String courseName, String institutionName, Pageable pageable);
}




