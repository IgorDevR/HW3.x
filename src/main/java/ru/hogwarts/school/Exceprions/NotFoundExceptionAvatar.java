package ru.hogwarts.school.Exceprions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class NotFoundExceptionAvatar extends RuntimeException{

    public NotFoundExceptionAvatar() {
        super("Avatar with this id not found");
    }

    public NotFoundExceptionAvatar(long id) {
        super("Avatar with this id " + id + " not found");
    }
}
