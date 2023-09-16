package org.ereach.inc.data.models.entries;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MedicalLog {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    @OneToMany
    private List<Entry> entries;
    private LocalDate dateCreated;
    private LocalTime timeCreated;
    private String patientIdentificationNumber;
    private boolean isActive;


}
