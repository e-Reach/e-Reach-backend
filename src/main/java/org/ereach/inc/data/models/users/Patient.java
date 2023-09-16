package org.ereach.inc.data.models.users;

import jakarta.persistence.*;
import lombok.*;
import org.ereach.inc.data.models.PersonalInfo;
import org.ereach.inc.data.models.hospital.Record;
import org.ereach.inc.data.models.Role;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Patient {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    private String firstName;
    private String lastName;
    private String patientIdentificationNumber;
    private String nin;
    @OneToOne
    private Record record;
    @Enumerated(STRING)
    private Role role;
    @OneToOne
    private PersonalInfo personalInfo;
    private String eReachUsername;

}
