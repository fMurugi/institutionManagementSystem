package com.fiona.instustionsManagement.students;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fiona.instustionsManagement.Institutions.InstitutionsModel;
import com.fiona.instustionsManagement.courses.CoursesModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "students")
public class StudentsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="studentId")
    private UUID studentId;

    private  String fullName;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "courseId")
    private CoursesModel coursesModel;

//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "institutionId")
//    private InstitutionsModel institutionsModel;
}
