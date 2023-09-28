package org.ereach.inc.data.models.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ereach.inc.data.models.AccountStatus;
import org.ereach.inc.data.models.Role;
import org.hibernate.annotations.NaturalId;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.UUID;
import static jakarta.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity
@Inheritance(strategy = TABLE_PER_CLASS)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Practitioner {
    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Enumerated(value = STRING)
    private Role userRole;
    @Column(unique = true)
    @NaturalId(mutable = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private boolean isActive;
    private AccountStatus status;
    @Column(unique = true)
    private String practitionerIdentificationNumber;
}
