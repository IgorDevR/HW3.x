package ru.hogwarts.school.Exceprions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class NotFoundExceptionStudent extends RuntimeException{

//    public NotFoundException(String message) {
//        super(message);
//    }

    public NotFoundExceptionStudent() {
        super("Student with this id not found");
    }
}
