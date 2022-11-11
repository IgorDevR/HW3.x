package ru.hogwarts.school.Exceprions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class NotFoundExceptionFaculty extends RuntimeException{

//    public NotFoundException(String message) {
//        super(message);
//    }

    public NotFoundExceptionFaculty(long id) {
        super("Faculty with this id " + id + " not found");
    }
    public NotFoundExceptionFaculty() {
        super("Faculty with this id not found");
    }
}
