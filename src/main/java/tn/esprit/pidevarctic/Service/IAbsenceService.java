package tn.esprit.pidevarctic.Service;

import tn.esprit.pidevarctic.entities.Absence;
import tn.esprit.pidevarctic.entities.Role;

import java.util.List;

public interface IAbsenceService {
    Absence addAbsence(Absence absence);
    Absence updateAbsence(Absence absence);
    void deleteAbsence(Long idAbsence);
    Absence getAbsenceById(Long idAbsence);
    List<Absence> getAllAbsence();


}
