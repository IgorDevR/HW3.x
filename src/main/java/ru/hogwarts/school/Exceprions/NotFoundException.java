package ru.hogwarts.school.Exceprions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

//    public NotFoundException(String message) {
//        super(message);
//    }

    public NotFoundException() {
        super("request with this id not found");
    }
}
