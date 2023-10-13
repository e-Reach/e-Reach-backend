package org.ereach.inc.data.models.entries;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TestResult {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    private String fileCloudUrl;
    private String testReport;
}
