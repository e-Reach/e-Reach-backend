package org.ereach.inc.data.models.users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.ereach.inc.data.models.Role;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HospitalAdmin {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    private Role role;
    private String adminEmail;
    private String adminPhoneNumber;
    private Role userRole;
    private String email;
    private String phoneNumber;
}
