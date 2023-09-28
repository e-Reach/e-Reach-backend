package org.ereach.inc.data.models.entries;

import jakarta.persistence.*;
import lombok.*;
import org.ereach.inc.data.models.users.Practitioner;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.List;

import static jakarta.persistence.GenerationType.UUID;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Entry  {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;
    @OneToMany
    private List<Practitioner> practitioner;
    private LocalTime timeCreated;
    @ElementCollection
//    @CollectionTable
    private List<String> fileCloudUrl;
}
