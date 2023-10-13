package org.ereach.inc.data.dtos.request;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RemovePractitionerRequest {
    private String practitionerEmail;
    private String hospitalEmail;
    private String hospitalHefamaaId;
    private String practitionerIdentificationNumber;
}
