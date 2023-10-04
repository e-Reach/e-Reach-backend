package org.ereach.inc.data.dtos.response.entries;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ereach.inc.data.models.hospital.Medication;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class GetMedicationResponse {
    private List<Medication> medications;
}
