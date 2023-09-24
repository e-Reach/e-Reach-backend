package org.ereach.inc.services.hospital;

import org.ereach.inc.data.dtos.request.AppointmentScheduleRequest;
import org.ereach.inc.data.dtos.response.AppointmentScheduleResponse;
import org.ereach.inc.exceptions.FieldInvalidException;

public interface AppointmentService {
    AppointmentScheduleResponse createAppointment(AppointmentScheduleRequest appointmentRequest) throws FieldInvalidException;
}
