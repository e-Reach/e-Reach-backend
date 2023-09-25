package org.ereach.inc.data.models.entries;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.ereach.inc.data.models.hospital.Medication;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Prescription extends Entry {
    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
<<<<<<< HEAD
    @OneToMany
    private List<Medication> medications;
    private String dosage;
=======
    private String medicationName;
    private String dosage;
    private String dosageFrequency;
    private LocalDate startDate;
    private LocalDate prescriptionDate;
    private String practitionerEmail;
>>>>>>> 4388856c0b1f399176692d67c0ddda414886aef2
}
