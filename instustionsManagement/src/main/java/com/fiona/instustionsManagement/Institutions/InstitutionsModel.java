package com.fiona.instustionsManagement.Institutions;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fiona.instustionsManagement.courses.CoursesModel;
import com.fiona.instustionsManagement.students.StudentsModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name="institutions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstitutionsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="institutionId")
    private UUID institutionId;
    @Column(name="institutionName",unique = true)
    private String institutionName;

    @JsonManagedReference
    @OneToMany(mappedBy = "institutionsModel",cascade = CascadeType.ALL)
    private List<CoursesModel> coursesModelList;
//
//    @OneToMany(mappedBy = "",cascade = CascadeType.ALL)
//    private List<StudentsModel> studentsModels;

}
