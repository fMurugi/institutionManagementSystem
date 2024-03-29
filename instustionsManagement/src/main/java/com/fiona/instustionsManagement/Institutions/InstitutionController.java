package com.fiona.instustionsManagement.Institutions;

import com.fiona.instustionsManagement.Exceptions.CannotDeleteInstitutionException;
import com.fiona.instustionsManagement.Exceptions.DuplicateInstitutionException;
import com.fiona.instustionsManagement.Exceptions.ResourceNotFoundException;
import com.fiona.instustionsManagement.Utility.ApiResponse;
import com.fiona.instustionsManagement.courses.CoursesModel;
import com.fiona.instustionsManagement.students.StudentsModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.fiona.instustionsManagement.Utility.ApiResponseBuilder.buildResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/institutions")
public class InstitutionController {
    private final InstitutionsService institutionsService;
    @PostMapping("/create_institution")
    private ResponseEntity<ApiResponse> createInstitution(@Valid @RequestBody InstitutionsDTO institutionsDTO, HttpServletRequest request){
        try{
            institutionsService.createInstitution(institutionsDTO);
            return buildResponseEntity(HttpStatus.CREATED,"created successfully",request.getRequestURI());

        }catch (DuplicateInstitutionException ex){
            return buildResponseEntity(HttpStatus.BAD_REQUEST,ex.getMessage(),request.getRequestURI());
        }
    }

    @GetMapping("/get_institutions")
    public ResponseEntity<ApiResponse> getAllInstitutions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "asc") String sortDirection,
            HttpServletRequest request
    ){
        List<InstitutionsModel> institutionsModels = institutionsService.getAllInstitutionsSortedByNAme(page,sortDirection);

        return buildResponseEntity(HttpStatus.OK,institutionsModels,request.getRequestURI());
    }
    @PutMapping("/update_institution/{institutionId}")
    public  ResponseEntity<ApiResponse> updateInstitutions(
            @PathVariable UUID institutionId,
            @RequestParam String newName,
            HttpServletRequest request
            ){
        String message = institutionsService.editInstitution(institutionId,newName);

        return buildResponseEntity(HttpStatus.ACCEPTED,message,request.getRequestURI());

    }

    @DeleteMapping("/delete_institution/{institutionId}")
    public  ResponseEntity<ApiResponse>  deleteInstitution(@PathVariable UUID institutionId,HttpServletRequest request){
        try{
            String message = institutionsService.deleteInstitution(institutionId);
            return buildResponseEntity(HttpStatus.OK,message,request.getRequestURI());
        }catch (ResourceNotFoundException ex){
            return buildResponseEntity(HttpStatus.NOT_FOUND,ex.getMessage(), request.getContextPath());
        }catch (CannotDeleteInstitutionException ex){
            return  buildResponseEntity(HttpStatus.BAD_REQUEST,ex.getMessage(),request.getRequestURI());
        }
    }

    @GetMapping("/courses/{institutionName}")
    public ResponseEntity<ApiResponse> getCoursesInAnInstitution(@PathVariable String institutionName,
            @RequestParam(defaultValue = "asc") String sortDirection,
            HttpServletRequest request){
        List<CoursesModel> coursesModelList = institutionsService.getAllCoursesByInstitution(institutionName,sortDirection);

        return buildResponseEntity(HttpStatus.OK,coursesModelList,request.getRequestURI());
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> search(@RequestParam String keyword,HttpServletRequest request){
        List<InstitutionsModel> institutionsModels = institutionsService.searchByKeyword(keyword);

        return buildResponseEntity(HttpStatus.OK,institutionsModels,request.getRequestURI());
    }
    @GetMapping("/list_students_by_Institution_and_Course")
    public ResponseEntity<ApiResponse> listStudentsByInstitutionAndCourse(
            @RequestParam String InstitutionName,
            @RequestParam String CourseName,
//            @RequestParam int Page,
            HttpServletRequest request
    ){
        List<StudentsModel> studentsModels = institutionsService.getStudentsInstitution(InstitutionName,CourseName);

        return buildResponseEntity(HttpStatus.OK,studentsModels,request.getRequestURI());
    }


}
