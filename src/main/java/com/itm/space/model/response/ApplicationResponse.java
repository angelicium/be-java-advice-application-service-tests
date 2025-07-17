package com.itm.space.model.response;

import com.itm.space.model.enums.ApplicationStatusName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponse {
    private UUID id;
    private UUID userId;
    private String specialization;
    private String skills;
    private String experience;
    private ApplicationStatusName status;
    private String comment;
    private LocalDateTime createdAt;
}
