package org.ereach.inc.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetRecordResponse {

    private String id;
    private LocalDate dateCreated;
    private LocalTime timeCreated;
    private boolean isActive;
}
