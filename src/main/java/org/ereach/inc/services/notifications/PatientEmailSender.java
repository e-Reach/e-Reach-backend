package org.ereach.inc.services.notifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data


public class PatientEmailSender {

        @JsonProperty("patientID")
        private String patientIdentificationNumber;
        @JsonProperty("email")
        private String email;
    }

