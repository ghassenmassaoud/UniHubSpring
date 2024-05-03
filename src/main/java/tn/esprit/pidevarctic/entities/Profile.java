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
public class Profile implements Serializable {
    @Id
    ProfileId pId;
    @Enumerated(EnumType.STRING)
    ProfileRole profileRole;
    @Enumerated(EnumType.STRING)
    State profileState;
    int mark;
    @OneToMany(mappedBy = "profile")
    Set<Absence> absences;
    public Club getClub(){
        return getPId().getClub();
    }
    void setClub(Club club){
        getPId().setClub(club);
    }
    public User getStudent(){
        return getPId().getStudent();
    }
    void setStudent(User student){
        getPId().setStudent(student);
    }
}
