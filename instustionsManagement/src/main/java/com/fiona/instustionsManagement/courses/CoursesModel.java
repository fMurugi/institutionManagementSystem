package com.fiona.instustionsManagement.courses;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fiona.instustionsManagement.Institutions.InstitutionsModel;
import com.fiona.instustionsManagement.students.StudentsModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="courses",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"courseName","institutionId"})
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoursesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="courseId")
    private UUID courseId;
    @Column(name="courseName")
    private String courseName;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "institutionId")
    private InstitutionsModel institutionsModel;

    @OneToMany(mappedBy = "coursesModel",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<StudentsModel> studentsModel;
}
