package org.ereach.inc.services.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.data.repositories.users.EReachPractitionerRepository;
import org.ereach.inc.services.validators.EmailValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EreachDoctorService implements DoctorService {
    private final EReachPractitionerRepository practitionerRepository;
    private ModelMapper modelMapper;
    private EmailValidator validator;
    @Getter
    private static String testDIN;
    
   
}
