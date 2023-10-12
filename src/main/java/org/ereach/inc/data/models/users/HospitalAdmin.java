package org.ereach.inc.data.models.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.ereach.inc.data.models.AccountStatus;
import org.ereach.inc.data.models.Role;
import org.ereach.inc.data.models.annotations.PhoneNumber;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HospitalAdmin {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    private Role adminRole;
    private boolean active;
    private AccountStatus accountStatus;
    private String adminFirstName;
    private String adminLastName;
    @Email
    @NotEmpty
    @Column(unique = true)
    private String adminEmail;
    @Email(regexp = "")
    @PhoneNumber(region = "ZZ")
    private String adminPhoneNumber;
    
}
