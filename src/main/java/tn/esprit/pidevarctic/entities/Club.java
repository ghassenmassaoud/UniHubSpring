package tn.esprit.pidevarctic.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
//@JsonIgnoreProperties({"profiles", "events"})
public class Club implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idClub;
    String clubName;
    @Enumerated(EnumType.STRING)
    ClubType clubType;

    @OneToMany(mappedBy = "pId.club")
    @JsonIgnore
    Set<Profile> profiles;

    @OneToMany(mappedBy = "club")
    @JsonIgnore
    Set<Event> events;

    @Override
    public String toString() {
        return "Club{" +
                ", clubName='" + clubName + '\'' +
                ", clubType=" + clubType +
                '}';
    }




}
