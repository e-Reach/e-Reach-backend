package org.ereach.inc.data.models.hospital;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class Appointments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @NotEmpty
    @NonNull
    private LocalDate sendDate;
    @NotEmpty
    @NonNull
    private  String patientEmail;
    @NotEmpty
    @NonNull
    private String patientIdentificationNumber;
    @NotEmpty
    @NonNull
    private LocalDate preferredDateAndTime;
    @NotEmpty
    @NonNull
    private AppointmentType typeOfAppointment;
    @NotEmpty
    @NonNull
    private String hospitalEmail;
    @NotEmpty
    @NonNull
    private String hospitalName;
    @NotEmpty
    @NonNull
    private String description;

}
