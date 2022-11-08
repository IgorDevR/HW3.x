package ru.hogwarts.school.Exceprions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class NotFoundExceptionAvatar extends RuntimeException{

//    public NotFoundException(String message) {
//        super(message);
//    }

    public NotFoundExceptionAvatar() {
        super("Avatar with this id not found");
    }
}
