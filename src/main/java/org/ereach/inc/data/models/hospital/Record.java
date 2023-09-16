package org.ereach.inc.data.models.hospital;

import jakarta.persistence.*;
import lombok.*;
import org.ereach.inc.data.models.entries.MedicalLog;

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
    @OneToMany
    private List<MedicalLog> medicalLogs;
    @ManyToOne
    private Hospital centreCreated;
    private LocalDate dateCreated;
    private LocalTime lastTimeUpdated;

    public void dateRecordCreated(){
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        dateCreated = LocalDate.parse(currentDate.format(formatter));
    }

}
