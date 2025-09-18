package org.example.backend.exceptions;

public class TeaNotFoundException extends RuntimeException {
    public TeaNotFoundException() {
        super("O dear, I haven't found any tea, unfortunately I ran out. Sorry.");
    }
}
