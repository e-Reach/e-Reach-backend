package org.ereach.inc.data.models.entries;

import jakarta.persistence.*;
import lombok.*;
import org.ereach.inc.data.models.hospital.Record;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MedicalLog {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    @OneToMany
    private List<Entry> entries ;
    @OneToMany
    private List<Prescription> prescriptions;
    @OneToOne
    private Vitals vitals;
    @OneToMany
    private List<Tests> tests;
    @OneToOne
    private DoctorsReport doctorsReport;
    private LocalDate dateCreated;
    private LocalTime timeCreated;
    private String patientIdentificationNumber;
    private boolean isActive;
    @ManyToOne
    private Record medicalRecord;
    private String hospitalEmail;
}
