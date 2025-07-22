package com.itm.space.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChangeApplicationRequest {

    @NotBlank(message = "Поле не может быть пустым")
    private String specialization;

    @NotBlank(message = "Поле не может быть пустым")
    private String skills;

    @NotBlank(message = "Поле не может быть пустым")
    private String experience;
}
