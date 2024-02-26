package com.fiona.instustionsManagement.courses;

import com.fiona.instustionsManagement.Institutions.InstitutionsService;
import com.fiona.instustionsManagement.Utility.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.fiona.instustionsManagement.Utility.ApiResponseBuilder.buildResponseEntity;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/add_course_to_institution/{institutionId}")
    public ResponseEntity<ApiResponse> addCourseToInstitution(
            @PathVariable UUID institutionId,
            @Valid @RequestBody CourseDTO courseDTO,
            HttpServletRequest request
            ){

            String message = courseService.addCourseToInstitution(institutionId,courseDTO);
            return buildResponseEntity(HttpStatus.CREATED,message,request.getRequestURI());

    }

    @PutMapping("/edit_course/{institutionId}/{courseId}")
    public ResponseEntity<ApiResponse> editCourse(
            @PathVariable UUID institutionId,
            @PathVariable UUID courseId,
            @Valid @RequestBody CourseDTO courseDTO,
            HttpServletRequest request
    ){
        String message = courseService.editCourse(institutionId,courseId,courseDTO);
        return  buildResponseEntity(HttpStatus.ACCEPTED,message,request.getRequestURI());

    }



    @DeleteMapping("/{courseId}")
    public ResponseEntity<ApiResponse> deleteCourse(
            @PathVariable UUID courseId,
            HttpServletRequest request
    ){
        String message = courseService.deleteCourse(courseId);
        return  buildResponseEntity(HttpStatus.ACCEPTED,message,request.getRequestURI());
    }

    @GetMapping("/search")
    public  ResponseEntity<ApiResponse> searchCourse(
            @RequestParam String InstitutionName,
            @RequestParam String Keyword,
            HttpServletRequest request
    ){
        List<CoursesModel> coursesModelList = courseService.searchCourse(InstitutionName,Keyword);
        return buildResponseEntity(HttpStatus.OK,coursesModelList,request.getRequestURI());
    }


}
