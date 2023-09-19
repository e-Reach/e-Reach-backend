package org.ereach.inc.services.hospital;

import lombok.RequiredArgsConstructor;
import org.ereach.inc.data.dtos.request.AddMedicationRequest;
import org.ereach.inc.data.models.hospital.Medication;
import org.ereach.inc.data.repositories.hospital.EReachMedicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;


@RequiredArgsConstructor
@Service
public class EreachMedicationService implements MedicationService {
    private final EReachMedicationRepository medicationRepository;
    @Override
    public Medication createMedication(AddMedicationRequest addMedicationRequest) {
        return medicationRepository.save(mapMedicationRequestToPharmacist(addMedicationRequest));
    }
    private Medication mapMedicationRequestToPharmacist(AddMedicationRequest addMedicationRequest){
        return Medication.builder()
                .dateAdded(LocalDate.now())
                .drugName(addMedicationRequest.getDrugName())
                .price(addMedicationRequest.getPrice())
                .timeAdded(LocalTime.now())
                .build();
    }
}
