package com.fiona.instustionsManagement.students;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/students")
public class StudentsController {

    private final StudentsService studentsService;



    @PostMapping
    public ResponseEntity<String> addStudent(@RequestBody StudentDTO studentDTO,
                                             @RequestParam UUID institutionId,
                                             @RequestParam UUID courseId) {
        String message = studentsService.addStudent(studentDTO, institutionId, courseId);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable UUID studentId) {
        String message = studentsService.deleteStudent(studentId);
        return ResponseEntity.ok(message);
    }


    @PutMapping("/{studentId}")
    public ResponseEntity<String> editStudentName(@PathVariable UUID studentId,
                                                  @RequestBody StudentDTO studentDTO) {
        String message = studentsService.editStudentName(studentId, studentDTO);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/{studentId}/courses/{newCourseId}")
    public ResponseEntity<String> changeStudentCourseWithinInstitution(@PathVariable UUID studentId,
                                                                       @PathVariable UUID newCourseId) {
        String message = studentsService.changeStudentCourseWithinInstitution(studentId, newCourseId);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/{studentId}/transfer")
    public ResponseEntity<String> transferStudent(@PathVariable UUID studentId,
                                                  @RequestParam UUID newInstitutionId,
                                                  @RequestParam UUID newCourseId) {
        String message = studentsService.transferStudent(studentId, newInstitutionId, newCourseId);
        return ResponseEntity.ok(message);
    }

    @GetMapping
    public ResponseEntity<Page<StudentsModel>> getAllStudents(@RequestParam(required = false) String institutionName,
                                                              @RequestParam(required = false) String courseName,
                                                              @RequestParam(defaultValue = "0") int page) {
        Page<StudentsModel> students = studentsService.getAllStudentsByInstitutionAndCourse(institutionName, courseName, page);
        return ResponseEntity.ok(students);
    }
}
