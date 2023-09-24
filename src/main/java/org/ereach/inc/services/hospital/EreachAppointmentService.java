package org.ereach.inc.services.hospital;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.data.dtos.request.AppointmentScheduleRequest;
import org.ereach.inc.data.dtos.response.AppointmentScheduleResponse;
import org.ereach.inc.data.models.hospital.AppointmentType;
import org.ereach.inc.data.models.hospital.Appointments;
import org.ereach.inc.data.models.hospital.Hospital;
import org.ereach.inc.data.models.users.Patient;
import org.ereach.inc.data.repositories.hospital.EReachHospitalRepository;
import org.ereach.inc.data.repositories.hospital.EreachAppointmentRepository;
import org.ereach.inc.data.repositories.users.EReachPatientsRepository;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static org.ereach.inc.utilities.Constants.INVALID_HOSPITAL_EMAIL;
import static org.ereach.inc.utilities.Constants.INVALID_PATIENT_IDENTIFICATION_NUMBER;


@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class EreachAppointmentService implements AppointmentService{
    private ModelMapper modelMapper;
    private EReachPatientsRepository patientsRepository;
    private EReachHospitalRepository hospitalRepository;
    private EreachAppointmentRepository appointmentRepository;
    @Override
    public AppointmentScheduleResponse createAppointment(AppointmentScheduleRequest appointmentRequest) throws FieldInvalidException {
      Appointments mappedAppointment = modelMapper.map(appointmentRequest, Appointments.class);
      String patientIdentificationNumber = appointmentRequest.getPatientIdentificationNumber();

      Patient foundPatient = patientsRepository.findByPatientIdentificationNumber(patientIdentificationNumber)
              .orElseThrow( () -> new FieldInvalidException(INVALID_PATIENT_IDENTIFICATION_NUMBER));
     String patientEmail = foundPatient.getEmail();

      Hospital foundHospital = hospitalRepository.findByHospitalEmail(appointmentRequest.getHospitalEmail())
              .orElseThrow(()-> new FieldInvalidException(INVALID_HOSPITAL_EMAIL) );

      modelMapper.map(foundHospital, mappedAppointment);
      mappedAppointment.setPatientEmail(patientEmail);

      mappedAppointment.setTypeOfAppointment(AppointmentType.valueOf(appointmentRequest.getTypeOfAppointment()));

      Appointments appointment = appointmentRepository.save(mappedAppointment);


      return null;
    }
}
