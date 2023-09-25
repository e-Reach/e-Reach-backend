package org.ereach.inc.data.models.entries;

import jakarta.persistence.*;
import lombok.*;
import org.ereach.inc.data.models.hospital.Hospital;

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
