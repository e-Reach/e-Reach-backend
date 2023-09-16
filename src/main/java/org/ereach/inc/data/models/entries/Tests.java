package org.ereach.inc.data.models.entries;

import jakarta.persistence.*;
import lombok.*;
import org.ereach.inc.data.models.entries.Entry;
import org.ereach.inc.data.models.entries.TestResult;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tests extends Entry {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    private String testName;
    @OneToOne
    private TestResult testResult;

}
