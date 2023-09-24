package org.ereach.inc.data.models.hospital;

import jakarta.persistence.*;
import lombok.*;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.data.models.users.Patient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    @OneToMany
    private List<MedicalLog> medicalLogs;
    @ManyToOne
    private Hospital centreCreated;
    private LocalDate dateCreated;
    private LocalTime lastTimeUpdated;

    public void recordCreationDate(){
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        dateCreated = LocalDate.parse(currentDate.format(formatter));
    }

    public void lastTimeRecordUpdated(){
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        dateCreated = LocalDate.parse(currentDate.format(formatter));
    }

}
