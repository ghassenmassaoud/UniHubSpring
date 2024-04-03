package tn.esprit.pidevarctic.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordObj {
    private String oldPassword;
    private String newPassword;
    private String confirmationPassword;
}
