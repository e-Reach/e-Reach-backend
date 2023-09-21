package org.ereach.inc.data.models.entries;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static jakarta.persistence.GenerationType.UUID;

public class MedicalRecord {
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
