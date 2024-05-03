package tn.esprit.pidevarctic.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idEvent;
    String eventName;
    LocalDate eventDate;
    String decription;
    @Enumerated(EnumType.STRING)
    Access access;
    @ManyToMany
    Set<User> students;
    @ManyToOne
    @JsonIgnore
    Club club;
}
