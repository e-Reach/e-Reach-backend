package org.ereach.inc.data.models.hospital;

import jakarta.persistence.*;
import lombok.*;
import org.ereach.inc.data.models.Address;
import org.ereach.inc.data.models.Role;
import org.ereach.inc.data.models.users.HospitalAdmin;
import org.ereach.inc.data.models.users.Practitioner;

import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Health_Center")
public class Hospital {
    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    private String HEFAMAA_ID;
    private String hospitalName;
    @OneToOne
    private Address address;
    private String email;
    private String phoneNumber;
    @OneToMany
    private List<Practitioner> practitioners;
    @OneToMany
    private Set<HospitalAdmin> admin;
    @OneToMany
    private Set<Record> records;
}
