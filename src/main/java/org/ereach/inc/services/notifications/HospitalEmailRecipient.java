package org.ereach.inc.services.notifications;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class HospitalEmailRecipient {

        private LocalDate sendDate;
        private  String patientEmail;
        private String patientIdentificationNumber;
        private LocalDate preferredDateAndTime;
        private String typeOfAppointment;
        private String hospitalEmail;
        private String hospitalName;
        private String description;

}
