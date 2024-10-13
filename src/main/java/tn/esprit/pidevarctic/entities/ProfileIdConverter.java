package tn.esprit.pidevarctic.entities;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import org.springframework.stereotype.Component;


@Component
public class ProfileIdConverter implements Converter<String, ProfileId> {
    @Override
    public ProfileId convert(String source) {
        String[] parts = source.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid ProfileId format");
        }

        Long clubId = Long.parseLong(parts[0]);
        Long userId = Long.parseLong(parts[1]);

        Club club = new Club();
        club.setIdClub(clubId);
        User user = new User();
        user.setIdUser(userId);

        ProfileId profileId = new ProfileId();
        profileId.setClub(club);
        profileId.setStudent(user);

        return profileId;
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructType(String.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructType(ProfileId.class);
    }


}