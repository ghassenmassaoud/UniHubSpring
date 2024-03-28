package tn.esprit.pidevarctic.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidevarctic.Repository.AbsenceRepository;
import tn.esprit.pidevarctic.Repository.RoleRepository;
import tn.esprit.pidevarctic.Repository.UserRepository;
import tn.esprit.pidevarctic.entities.Absence;
import tn.esprit.pidevarctic.entities.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AbsenceService implements IAbsenceService {
    private AbsenceRepository absenceRepository;

    @Override
    public Absence addAbsence(Absence absence) {
        return absenceRepository.save(absence);
    }

    @Override
    public Absence updateAbsence(Absence absence) {
        return absenceRepository.save(absence);
    }



    @Override
    public void deleteAbsence(Long idAbsence) {
        absenceRepository.deleteById(idAbsence);
    }

    @Override
    public Absence getAbsenceById(Long idAbsence) {
        return absenceRepository.findById(idAbsence).orElse(null);
    }

    @Override
    public List<Absence> getAllAbsence() {
        return absenceRepository.findAll();
    }


}
