package com.fiona.instustionsManagement.Institutions;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionsDTO {
    @NotBlank(message = "Institution name is required")
    private String institutionsName;

}
