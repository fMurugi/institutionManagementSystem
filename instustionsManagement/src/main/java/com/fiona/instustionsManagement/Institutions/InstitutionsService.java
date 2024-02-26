package com.fiona.instustionsManagement.Institutions;

import com.fiona.instustionsManagement.Exceptions.CannotDeleteInstitutionException;
import com.fiona.instustionsManagement.Exceptions.DuplicateInstitutionException;
import com.fiona.instustionsManagement.Exceptions.ResourceNotFoundException;
import com.fiona.instustionsManagement.courses.CoursesModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InstitutionsService {
    private final InstitutionsRepository institutionsRepository;

    public void createInstitution(InstitutionsDTO institutionsDTO){
        String institutionName = institutionsDTO.getInstitutionsName();

        if(institutionsRepository.existsByInstitutionName(institutionName)){
            throw new DuplicateInstitutionException("Another institution with the same name already exists");
        }
        InstitutionsModel institution = InstitutionsModel.builder()
                .institutionName(institutionsDTO.getInstitutionsName())
                .build();
        institutionsRepository.save(institution);

    }

    public List<InstitutionsModel> getAllInstitutions(int page){
        Pageable pageable = PageRequest.of(page,10);
        Page<InstitutionsModel> institutionsModels = institutionsRepository.findAll(pageable);

        return institutionsModels.hasContent() ? institutionsModels.getContent(): Collections.emptyList();
    }

    public List<InstitutionsModel> searchAndFilterInstitutions(String keyword,String sortBy,int page){
        Pageable pageable = PageRequest.of(page,10);
        Page<InstitutionsModel> institutionsModels = institutionsRepository.findByInstitutionNameContainingIgnoreCase(keyword,pageable);
        return  institutionsModels.hasContent() ? institutionsModels.getContent(): Collections.emptyList();

    }

    public List<InstitutionsModel> getAllInstitutionsSortedByNAme(int page,String sortDirection){
        Sort sort  = sortDirection.equalsIgnoreCase("asc")?
                Sort.by("institutionName").ascending():
                Sort.by("institutionName").descending();
        Pageable pageable = PageRequest.of(page,10,sort);
        Page<InstitutionsModel> institutionsModels = institutionsRepository.findAll(pageable);

        return institutionsModels.hasContent() ? institutionsModels.getContent(): Collections.emptyList();

    }

    public String editInstitution(UUID institutionId,String newName){
        if (institutionsRepository.existsByInstitutionName(newName)){
            throw new DuplicateInstitutionException("Another institution with the same name already exists");
        }
        InstitutionsModel institutionsModel = institutionsRepository.findById(institutionId)
                .orElseThrow(()-> new ResourceNotFoundException("Institution not found with Id: " + institutionId));

        institutionsModel.setInstitutionName(newName);
        institutionsRepository.save(institutionsModel);
        return "Institution updated successfully";
    }

    public String deleteInstitution(UUID institutionId){
        InstitutionsModel institutionsModel = institutionsRepository.findById(institutionId)
                .orElseThrow(()-> new ResourceNotFoundException("Institution not found with id: " + institutionId));

        if (!institutionsModel.getCoursesModelList().isEmpty()){
            throw new CannotDeleteInstitutionException("Cannot delete institution because it is assigned to courses");
        }

        institutionsRepository.delete(institutionsModel);

        return "Institution deleted successfully";
    }

    public  InstitutionsModel findInstitutionById(UUID institutionId){

        InstitutionsModel institution = institutionsRepository.findById(institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Institution not found with id: " + institutionId));
        return  institution;
    }

    public List<CoursesModel> getAllCoursesByInstitution(UUID institutionId, int page, int size,String sortDirection) {
        InstitutionsModel institution = institutionsRepository.findById(institutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Institution not found with id: " + institutionId));
        List<CoursesModel> coursesModelList = institution.getCoursesModelList();
        return  coursesModelList;
    }


//    public List<CoursesModel> getAllCoursesByInstitution(String institutionName, String sortDirection) {
//        InstitutionsModel institutionsModel = institutionsRepository.findByInstitutionName(institutionName);
//        List<CoursesModel> coursesModelList = institutionsModel.getCoursesModelList();
//
//        Sort.Direction direction = Sort.Direction.ASC;
//        if ("desc".equalsIgnoreCase(sortDirection)) {
//           Collections.sort(coursesModelList,Comparator.comparing(CoursesModel::getCourseName).reversed());
//        }else{
//            Collections.sort(coursesModelList,Comparator.comparing(CoursesModel::getCourseName));
//        }
//
//        return coursesModelList;
//    }

    public List<CoursesModel> getAllCoursesByInstitution(String institutionName, String sortDirection) {
        InstitutionsModel institutionsModel = institutionsRepository.findByInstitutionName(institutionName);
        List<CoursesModel> coursesModelList = institutionsModel.getCoursesModelList();

        Comparator<CoursesModel> comparator = Comparator.nullsFirst(Comparator.comparing(CoursesModel::getCourseName));

        if ("desc".equalsIgnoreCase(sortDirection)) {
            comparator = comparator.reversed();
        }

        coursesModelList.sort(comparator);

        return coursesModelList;
    }


    public List<InstitutionsModel> searchByKeyword(String keyword){
       return institutionsRepository.findByInstitutionNameContaining(keyword);

    }

}
