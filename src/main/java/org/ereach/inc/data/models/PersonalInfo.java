package org.ereach.inc.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonalInfo {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    private String email;
    private String phoneNumber;
    private String allergy;
    @OneToOne
    private Address address;
    private String knownHealthConditions;
    private LocalDate dateOfBirth;

}
