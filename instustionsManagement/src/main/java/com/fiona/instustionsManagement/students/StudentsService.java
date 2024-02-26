package com.fiona.instustionsManagement.students;

import com.fiona.instustionsManagement.Exceptions.IllegalCourseAndInstitutionException;
import com.fiona.instustionsManagement.Exceptions.InvalidCourseChangeException;
import com.fiona.instustionsManagement.Exceptions.ResourceNotFoundException;
import com.fiona.instustionsManagement.Institutions.InstitutionsModel;
import com.fiona.instustionsManagement.Institutions.InstitutionsRepository;
import com.fiona.instustionsManagement.courses.CoursesModel;
import com.fiona.instustionsManagement.courses.CoursesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentsService {
    private final StudentsRepository studentsRepository;
    private final CoursesRepository coursesRepository;
    private final InstitutionsRepository institutionsRepository;


    public String addStudent(StudentDTO studentDTO,UUID institutionId,UUID courseId){
        if (!checkIfCourseBelongsToInstitution(institutionId, courseId)) {
            throw new IllegalCourseAndInstitutionException("The course does not belong to the institution.");
        }
        CoursesModel coursesModel = coursesRepository.findById(courseId)
                .orElseThrow(()-> new ResourceNotFoundException("No course with that Id"));

        StudentsModel studentsModel = StudentsModel.builder()
                .fullName(studentDTO.getFullName())
                .coursesModel(coursesModel)
                .build();
        studentsRepository.save(studentsModel);
        return "student added successfully";
    }

    public String deleteStudent(UUID studentId){
        studentsRepository.deleteById(studentId);

        return "student deleted successfully";
    }

    public String editStudentName(UUID studentId,StudentDTO studentDTO){
        StudentsModel studentsModel = studentsRepository.findById(studentId)
                .orElseThrow(()->new ResourceNotFoundException("No student found with id: " + studentId));

        studentsModel.setFullName(studentDTO.getFullName());
        studentsRepository.save(studentsModel);

        return "student's name edited successfully";
    }

    public String changeStudentCourseWithinInstitution(UUID studentId,UUID newCourseId){
        StudentsModel student = studentsRepository.findById(studentId)
                .orElseThrow(()->new ResourceNotFoundException("Student not found with ID: " + studentId));

        CoursesModel newCourse = coursesRepository.findById(newCourseId)
                .orElseThrow(()-> new ResourceNotFoundException("Course not found with ID: " + newCourseId));

        if (!newCourse.getInstitutionsModel().getInstitutionId().equals(student.getCoursesModel().getInstitutionsModel().getInstitutionId())){
            throw new InvalidCourseChangeException("Cannot change course to a different institution");

        }
        student.setCoursesModel(newCourse);
        studentsRepository.save(student);
        return "student's course changed successfully";

    }

    public String transferStudent(UUID studentId,UUID newInstitutionId,UUID newCourseId) {
        StudentsModel student = studentsRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));

        CoursesModel newCourse = coursesRepository.findById(newCourseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + newCourseId));

        InstitutionsModel newInstituion = institutionsRepository.findById(newInstitutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Institution not found with ID: " + newInstitutionId));

        if (!newCourse.getInstitutionsModel().getInstitutionId().equals(newInstituion.getInstitutionId())) {
            throw new InvalidCourseChangeException("This course is not offered in this university");
        }

        student.setCoursesModel(newCourse);
        studentsRepository.save(student);

        return "student transferred successfully";
    }

    public Page<StudentsModel> getAllStudentsByInstitutionAndCourse(String institutionName, String courseName, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        if (institutionName != null && courseName != null) {
            return studentsRepository.findByInstitutionNameAndCourseName(institutionName, courseName, pageable);
        } else if (institutionName != null) {
            return studentsRepository.findByInstitutionName(institutionName, pageable);
        } else if (courseName != null) {

            return studentsRepository.findByCoursesModel_CourseName(courseName, pageable);
        } else {
            return studentsRepository.findAll(pageable);
        }
    }

    public Boolean checkIfCourseBelongsToInstitution(UUID institutionId,UUID courseId){
        Optional<InstitutionsModel> institutionOptional = institutionsRepository.findById(institutionId);
        if (institutionOptional.isEmpty()) {
            throw new ResourceNotFoundException("Institution not found with ID: " + institutionId);
        }

        Optional<CoursesModel> courseOptional = coursesRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            CoursesModel course = courseOptional.get();
            InstitutionsModel institution = institutionOptional.get();
            return course.getInstitutionsModel().getInstitutionId().equals(institutionId);
        } else {
            throw new ResourceNotFoundException("Course not found with ID: " + courseId);
        }

    }


}
