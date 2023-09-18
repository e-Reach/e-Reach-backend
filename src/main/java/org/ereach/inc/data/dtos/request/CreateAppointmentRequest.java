package org.ereach.inc.data.dtos.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.ereach.inc.data.models.hospital.AppointmentType;

import java.time.LocalDate;

@Getter
@Setter
@Builder


public class CreateAppointmentRequest {
    private LocalDate sendDate;
    private  String patientEmail;
    private String patientIdentificationNumber;
    private LocalDate preferredDateAndTime;
    private String typeOfAppointment;
    private String hospitalEmail;
    private String hospitalName;
    private String description;


}
