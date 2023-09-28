package org.ereach.inc.services.notifications;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recipient{
  
    private String id;
    private String email;
    private String phoneNumber;
}
