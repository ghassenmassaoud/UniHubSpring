package tn.esprit.pidevarctic.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
//@JsonIgnoreProperties({"absences", "profile"})

@Entity
public class Profile implements Serializable {
    @Id
    ProfileId pId;
    @Enumerated(EnumType.STRING)
    ProfileRole profileRole;
    @Enumerated(EnumType.STRING)
    State profileState;
    int mark;
    @JsonIgnore
    @OneToMany(mappedBy = "profile")
    Set<Absence> absences;



    public Club getClub(){
        return getPId().getClub();

    }
    void setClub(Club club){getPId().setClub(club);}
    public User getStudent(){
        return getPId().getStudent();
    }
    public void setStudent(User student){
        getPId().setStudent(student);
    }


    @Override
    public String toString() {
        return "Profile{" +
                "pId=" + pId +
                ", profileRole=" + profileRole +
                ", profileState=" + profileState +
                ", mark=" + mark +
                '}';
    }
}
