package org.ereach.inc.services.entries;

import lombok.AllArgsConstructor;
import org.ereach.inc.data.dtos.request.CreateMedicalLogRequest;
import org.ereach.inc.data.dtos.response.MedicalLogResponse;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.data.repositories.entries.EReachMedicalLogRepository;
import org.ereach.inc.data.repositories.users.EReachPatientsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EreachMedicalLogService implements MedicalLogService {
    private EReachMedicalLogRepository medicalLogService;
    private EReachPatientsRepository patientsRepository;
    private ModelMapper modelMapper;


    @Override
    public MedicalLogResponse createNewLog(CreateMedicalLogRequest createLogRequest) {
        MedicalLog medicalLog = new MedicalLog();
        return null;
    }

    @Override
    public void deActivateAllActiveLogs() {

    }
}
