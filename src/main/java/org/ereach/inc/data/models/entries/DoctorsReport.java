package org.ereach.inc.data.models.entries;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String reportContent;
    private LocalDateTime date;
    private String diagnosis;
    private String treatment;
    private String practitionerEmail;
}
