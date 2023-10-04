package org.ereach.inc.data.models.entries;

import jakarta.persistence.*;
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
@ToString
public class MedicalLog {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
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


}
