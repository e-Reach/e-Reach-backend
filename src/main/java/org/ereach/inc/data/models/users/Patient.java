package org.ereach.inc.data.models.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    @NotBlank
    @NonNull
    private String firstName;
    @NotBlank
    @NonNull
    private String lastName;
    @NotBlank
    @NonNull
    @Column(unique = true)
    private String patientIdentificationNumber;
    @Column(unique = true)
    private String nin;
    @NotEmpty
    @NonNull
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @Column(unique = true)
    private String email;
    @NotBlank
    @NonNull
    private String phoneNumber;
    @OneToOne
    private Record record;
    @Enumerated(STRING)
    private Role userRole;
    @OneToOne
    private PersonalInfo personalInfo;
    @Column(unique = true)
    private String eReachUsername;

}
