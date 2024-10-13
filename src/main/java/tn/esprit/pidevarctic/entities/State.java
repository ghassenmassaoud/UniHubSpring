package tn.esprit.pidevarctic.entities;

public enum State {
    ACTIVE,BANNED;

    public static State fromString(String state) {
        for (State s : State.values()) {
            if (s.name().equalsIgnoreCase(state)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown state: " + state);
    }



}
