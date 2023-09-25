package org.ereach.inc.data.models.hospital;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Medication {
    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    private BigDecimal price;
    private LocalDate dateAdded;
    private LocalTime timeAdded;
    private String drugName;
}
