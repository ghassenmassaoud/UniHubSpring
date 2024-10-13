package tn.esprit.pidevarctic.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
//@JsonIgnoreProperties({"students", "club"})
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
    @JsonIgnore
    Set<User> students;
    @ManyToOne
    @JsonIgnore
    Club club;


    @Setter
    @Getter
    private int remainingDays;

//    @Column(name = "days_remaining", nullable = false, columnDefinition = "int default 0")
//    private int daysRemaining;

    // ...

//    public void calculateRemainingDays() {
//        LocalDate now = LocalDate.now();
//        long daysBetween = ChronoUnit.DAYS.between(now, eventDate);
//        remainingDays = (int) Math.max(0, daysBetween);
//    }
public void calculateRemainingDays() {
    LocalDate now = LocalDate.now();
    long daysBetween = ChronoUnit.DAYS.between(now, eventDate);
    remainingDays = (int) Math.max(0, daysBetween);
}

    // ...

    //    @Setter
//    private int daysRemaining;
//
//    private int remainingDays;

    // Constructors, getters, and setters





    @Override
    public String toString() {
        return "Event{" +
                "id=" + idEvent +
                ", eventName='" + eventName + '\'' +
                ", eventDate=" + eventDate +
                ", description='" + decription + '\'' +
                '}';
    }

//    public int getDaysRemaining() {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime eventDate = getEventDate().atStartOfDay();
//        long daysBetween = ChronoUnit.DAYS.between(now, eventDate);
//        return (int) Math.max(0, daysBetween);
//    }
//
//
//    public void setDaysRemaining(int daysRemaining) {
//        this.remainingDays=remainingDays;
//    }
}
