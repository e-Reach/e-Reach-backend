package org.ereach.inc.data.models.entries;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private LocalDate testDate;
    private String practitionerEmail;

}
