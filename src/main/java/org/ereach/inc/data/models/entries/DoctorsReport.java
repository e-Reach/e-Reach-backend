package org.ereach.inc.data.models.entries;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.ereach.inc.data.models.users.Doctor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorsReport extends Entry {
    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    @OneToOne
    private Doctor doctor;
    private String consultationReason;
    private LocalDateTime date;
    private String diagnosis;
}
