package org.ereach.inc.data.models.hospital;

import jakarta.persistence.*;
import lombok.*;
import org.ereach.inc.data.models.Address;
import org.ereach.inc.data.models.Role;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.data.models.users.HospitalAdmin;
import org.ereach.inc.data.models.users.Patient;
import org.ereach.inc.data.models.users.Practitioner;

import java.util.Set;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;
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
    @OneToOne
    private Address address;
    @Column(unique = true)
    private String HEFAMAA_ID;
    private String hospitalName;
    @Column(unique = true)
    private String hospitalPhoneNumber;
    @Column(unique = true)
    private String hospitalEmail;
    private Role userRole;
    @OneToMany(fetch = EAGER)
    private Set<MedicalLog> logsCreated;
    @OneToMany(fetch = EAGER)
    private Set<Practitioner> practitioners;
    @OneToMany(fetch = EAGER)
    private Set<HospitalAdmin> admins;
    @OneToMany(fetch = EAGER)
    private Set<Record> records;
}
