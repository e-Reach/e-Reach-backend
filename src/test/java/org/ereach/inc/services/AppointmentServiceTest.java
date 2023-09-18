package org.ereach.inc.services;


import org.ereach.inc.data.dtos.request.CreateAppointmentRequest;
import org.ereach.inc.data.dtos.response.CreateAppointmentResponse;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.services.hospital.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.ereach.inc.data.models.hospital.AppointmentType.GENERAL_CHECK_UP;

@SpringBootTest

public class AppointmentServiceTest {
    private AppointmentService appointmentService;




    @Test
    void testThatAppointmentCanBeCreated() throws FieldInvalidException {
        LocalDateTime fixedDateTime =
                LocalDateTime.of(2023, Month.JANUARY, 15, 10, 0);
        CreateAppointmentRequest appointmentRequest = CreateAppointmentRequest.builder()
                .typeOfAppointment(String.valueOf(GENERAL_CHECK_UP))
                .sendDate(LocalDate.from(fixedDateTime))
                .build();
        CreateAppointmentResponse response = appointmentService.createAppointment(appointmentRequest);
        assertThat(response.getMessage()).contains("Appointment Created Successfully");
    }

}
