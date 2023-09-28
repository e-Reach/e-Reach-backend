package org.ereach.inc.services.hospital;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.ereach.inc.data.dtos.request.AddMedicationRequest;
import org.ereach.inc.data.dtos.response.AddMedicationResponse;
import org.ereach.inc.data.models.hospital.Medication;
import org.ereach.inc.data.repositories.hospital.EReachMedicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.ereach.inc.utilities.Constants.MEDICATION_ADDED_SUCCESSFULLY;


@AllArgsConstructor
@Service
public class EreachMedicationService implements MedicationService {

    private final EReachMedicationRepository medicationRepository;
    ModelMapper modelMapper;
    @Override
    public AddMedicationResponse createMedication(AddMedicationRequest addMedicationRequest) {
        Medication savedMedication  =  medicationRepository.save(mapMedicationRequestToPharmacist(addMedicationRequest));
        AddMedicationResponse response = modelMapper.map(savedMedication, AddMedicationResponse.class);
        response.setMessage(MEDICATION_ADDED_SUCCESSFULLY);
        return response;
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
