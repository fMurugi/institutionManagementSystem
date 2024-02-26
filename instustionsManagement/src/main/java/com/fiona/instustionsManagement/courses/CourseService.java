package com.fiona.instustionsManagement.courses;

import com.fiona.instustionsManagement.Exceptions.DuplicateCourseException;
import com.fiona.instustionsManagement.Exceptions.ResourceNotFoundException;
import com.fiona.instustionsManagement.Institutions.InstitutionsModel;
import com.fiona.instustionsManagement.Institutions.InstitutionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CoursesRepository coursesRepository;
    private final InstitutionsService institutionsService;

    public String addCourseToInstitution(UUID institutionId, CourseDTO courseDTO){
        InstitutionsModel institutionsModel =institutionsService.findInstitutionById(institutionId);
        String courseName = courseDTO.getCourseName();

        if (institutionsModel.getCoursesModelList().stream().anyMatch(
                course -> course.getCourseName().equals(courseName)
        )){
            throw new DuplicateCourseException("A course with the same name already exists in the institution");
        }

        CoursesModel newCourse = CoursesModel.builder()
                .courseName(courseDTO.courseName)
                .institutionsModel(institutionsModel)
                .build();

        coursesRepository.save(newCourse);
        return  "Course successfully added";

    }

    public String editCourse(UUID institutionId,UUID courseId,CourseDTO courseDTO){
        CoursesModel coursesModel = findCourseById(courseId);
        InstitutionsModel institutionsModel =institutionsService.findInstitutionById(institutionId);
        String newCourseName = courseDTO.getCourseName();

        if (institutionsModel.getCoursesModelList().stream().anyMatch(
                course -> course.getCourseName().equals(newCourseName)
        )){
            throw new DuplicateCourseException("A course with the same name already exists in the institution");
        }
        coursesModel.setCourseName(newCourseName);
        return "Course successfully edited";
    }

    public CoursesModel findCourseById(UUID courseId){
        CoursesModel coursesModel = coursesRepository.findById(courseId)
                .orElseThrow(()->new ResourceNotFoundException("No course with that ID"));
        return coursesModel;
    }

    //delete course



}
