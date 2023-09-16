package org.ereach.inc.data.models.entries;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.ereach.inc.data.models.hospital.Medication;

import java.util.List;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Prescription extends Entry {
    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    @OneToMany
    private List<Medication> medications;

}
