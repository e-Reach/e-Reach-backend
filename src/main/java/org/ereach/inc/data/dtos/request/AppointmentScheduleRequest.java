package org.ereach.inc.data.dtos.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder


public class AppointmentScheduleRequest {
    private LocalDate sendDate;
    private  String patientEmail;
    private String patientIdentificationNumber;
    private LocalDate preferredDateAndTime;
    private String typeOfAppointment;
    private String hospitalEmail;
    private String hospitalName;
    private String description;


}
