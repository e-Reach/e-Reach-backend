package org.ereach.inc.services.entries;

import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MedicalLogServiceTest {

    @Autowired
    private MedicalLogService medicalLogService;

    @Test
    public void addMedicalLogTest(){

    }

    @Test void deactivateMedicalLogByMidnightTest(){

    }

    @Test void deactivateMedicalLog_LogsWhosePatientsAreAdmissionAreNotDeactivated(){

    }

    @Test void testGetMedicalLogByPatientIdentificationNumber(){

    }

    @Test void testGetAllMedicalLogs(){

    }
}