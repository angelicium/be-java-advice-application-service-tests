package com.itm.space.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChangeApplicationResponse {

    private UUID id;

    private UUID userId;

    private String specialization;

    private String skills;

    private String experience;

    private String status;

    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
