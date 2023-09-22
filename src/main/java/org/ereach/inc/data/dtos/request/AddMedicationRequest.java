package org.ereach.inc.data.dtos.request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AddMedicationRequest {
    private BigDecimal price;
    private LocalDate dateAdded;
    private LocalTime timeAdded;
    private String drugName;

}
