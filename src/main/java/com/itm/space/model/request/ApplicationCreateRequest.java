package com.itm.space.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationCreateRequest {
    @NotBlank
    private String specialization;
    @NotBlank
    private String skills;
    @NotBlank
    private String experience;
}
