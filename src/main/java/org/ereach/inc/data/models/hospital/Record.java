package org.ereach.inc.data.models.hospital;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    @OneToMany
    private List<MedicalLog> medicalLogs;
    private LocalDate dateCreated;
    private LocalTime lastTimeUpdated;
}
