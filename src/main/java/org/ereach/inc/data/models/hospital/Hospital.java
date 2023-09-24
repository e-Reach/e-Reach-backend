package org.ereach.inc.data.models.hospital;

import jakarta.persistence.*;
import lombok.*;
import org.ereach.inc.data.models.Address;
import org.ereach.inc.data.models.Role;
import org.ereach.inc.data.models.users.HospitalAdmin;
import org.ereach.inc.data.models.users.Practitioner;
import org.hibernate.annotations.NaturalId;

import java.util.Set;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Health_Center")
@ToString
public class Hospital {
    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @Column(unique = true)
    private String HEFAMAA_ID;
    private String hospitalName;
    @Column(unique = true)
    private String hospitalPhoneNumber;
    @Column(unique = true)
    private String hospitalEmail;
    private Role role;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Practitioner> practitioners;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<HospitalAdmin> admins;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Record> records;
}
