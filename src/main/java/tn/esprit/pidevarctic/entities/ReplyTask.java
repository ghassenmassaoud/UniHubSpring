package tn.esprit.pidevarctic.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ReplyTask implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idTaskrep;
    @Enumerated(EnumType.STRING)
    TaskState taskState;
    float mark;
    @ManyToOne
    @JsonIgnore
    Task task;
    @ManyToOne
    @JsonIgnore
    User student;
    @OneToMany(mappedBy = "replyTask")
    Set<Document>documents ;

}