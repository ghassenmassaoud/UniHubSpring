package tn.esprit.pidevarctic.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidevarctic.entities.Profile;
import tn.esprit.pidevarctic.entities.ProfileId;

public interface ProfileRepository extends JpaRepository<Profile, ProfileId> {
}