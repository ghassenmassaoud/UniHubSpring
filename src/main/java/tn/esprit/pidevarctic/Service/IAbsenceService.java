package tn.esprit.pidevarctic.Service;

import tn.esprit.pidevarctic.entities.Absence;
import tn.esprit.pidevarctic.entities.Role;
import tn.esprit.pidevarctic.entities.StatusAbsence;

import java.time.LocalDate;
import java.util.List;

public interface IAbsenceService {
    Absence addAbsence(Absence absence, Long classroom,Long student);
    Absence updateAbsence(Absence absence, Long absenceId);
    void deleteAbsence(Long idAbsence);
    Absence getAbsenceById(Long idAbsence);
    List<Absence> getAllAbsence();
    List<Absence> searchByStatus(StatusAbsence status);
    List<Absence> searchByDate(LocalDate date);
}
