package org.ereach.inc.data.models.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.ereach.inc.data.models.AccountStatus;
import org.ereach.inc.data.models.Role;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.UUID;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Doctor extends Practitioner {
    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
}
