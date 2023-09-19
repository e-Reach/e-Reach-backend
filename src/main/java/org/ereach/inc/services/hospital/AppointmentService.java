package org.ereach.inc.services.hospital;

import org.ereach.inc.data.dtos.request.CreateAppointmentRequest;
import org.ereach.inc.data.dtos.response.CreateAppointmentResponse;
import org.ereach.inc.exceptions.FieldInvalidException;

public interface AppointmentService {
    CreateAppointmentResponse createAppointment(CreateAppointmentRequest appointmentRequest) throws FieldInvalidException;
}
