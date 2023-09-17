package org.ereach.inc.data.dtos.request;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.data.models.hospital.Hospital;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static jakarta.persistence.GenerationType.UUID;

@Setter
@Getter
@Builder

public class CreateRecordRequest {

    private String id;
    private List<MedicalLog> medicalLogs;
    private Hospital centreCreated;
    private LocalDate dateCreated;
    private LocalTime lastTimeUpdated;
}
