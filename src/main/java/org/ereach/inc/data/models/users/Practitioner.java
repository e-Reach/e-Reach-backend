package org.ereach.inc.data.models.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ereach.inc.data.models.Role;

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
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    @Enumerated(STRING)
    private Role role;

}