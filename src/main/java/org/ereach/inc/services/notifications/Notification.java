package org.ereach.inc.services.notifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Notification {
    private String message;
    @JsonProperty("sender")
    private Sender sender;
    @JsonProperty("to")
    private List<Recipient> recipients;
    @JsonProperty("htmlContent")
    private String htmlContent;
    @JsonProperty(value = "subject")
    private String subject;
}
