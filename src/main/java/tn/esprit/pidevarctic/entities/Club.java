package tn.esprit.pidevarctic.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Club implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idClub;
    String clubName;
    @Enumerated(EnumType.STRING)
    ClubType clubType;
    @OneToMany(mappedBy = "pId.club")
    Set<Profile> profiles;
    @OneToMany(mappedBy = "club")
    Set<Event> events;
}
