package org.ereach.inc.services.hospital;

import org.ereach.inc.data.dtos.request.AddMedicationRequest;
import org.ereach.inc.data.dtos.response.AddMedicationResponse;
import org.ereach.inc.data.models.hospital.Medication;

public interface MedicationService {

     AddMedicationResponse createMedication(AddMedicationRequest addMedicationRequest);


}
