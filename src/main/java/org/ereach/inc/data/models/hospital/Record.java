package org.ereach.inc.data.models.hospital;

import jakarta.persistence.*;
import lombok.*;
import org.ereach.inc.data.models.entries.MedicalLog;

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
public class Record {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    private String patientIdentificationNumber;
    
    private LocalDate dateCreated;
    private LocalTime lastTimeUpdated;
    @OneToMany(fetch = FetchType.EAGER)
    private List<MedicalLog> medicalLogs;
}
