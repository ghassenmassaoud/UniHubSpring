package tn.esprit.pidevarctic.Config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

public class UserException extends AuthenticationException {
    public UserException(String msg) {
        super(msg);
    }
}
