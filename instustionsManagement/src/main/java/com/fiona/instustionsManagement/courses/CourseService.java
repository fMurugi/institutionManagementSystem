package com.fiona.instustionsManagement.courses;

import com.fiona.instustionsManagement.Exceptions.DuplicateCourseException;
import com.fiona.instustionsManagement.Exceptions.ResourceNotFoundException;
import com.fiona.instustionsManagement.Institutions.InstitutionsModel;
import com.fiona.instustionsManagement.Institutions.InstitutionsService;
import com.fiona.instustionsManagement.students.StudentsModel;
import com.fiona.instustionsManagement.students.StudentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CoursesRepository coursesRepository;
    private final InstitutionsService institutionsService;
    private final StudentsRepository studentsRepository;

    public String addCourseToInstitution(UUID institutionId, CourseDTO courseDTO){
        InstitutionsModel institutionsModel =institutionsService.findInstitutionById(institutionId);
        String courseName = courseDTO.getCourseName();

        List<CoursesModel> coursesModelList = institutionsModel.getCoursesModelList();


        if (institutionsModel.getCoursesModelList().stream().anyMatch(course -> {
            String name = course.getCourseName();
            return name != null && name.equals(courseName);
        })) {
            throw new DuplicateCourseException("A course with the same name already exists in the institution");
        }


        CoursesModel newCourse = CoursesModel.builder()
                .courseName(courseDTO.getCourseName())
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
        coursesRepository.save(coursesModel);
        return "Course successfully edited";
    }


    public String deleteCourse(UUID courseId) {
        CoursesModel course = coursesRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + courseId));

        List<StudentsModel> students = studentsRepository.findByCoursesModel(course);
        if (!students.isEmpty()) {
            return "Cannot delete course. This course has students.";
        }

        coursesRepository.delete(course);
        return "Course deleted successfully";
    }

    public CoursesModel findCourseById(UUID courseId){
        CoursesModel coursesModel = coursesRepository.findById(courseId)
                .orElseThrow(()->new ResourceNotFoundException("No course with that ID"));
        return coursesModel;
    }

    //delete course



}
