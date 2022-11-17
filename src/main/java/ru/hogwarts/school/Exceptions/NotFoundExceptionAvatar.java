package ru.hogwarts.school.Exceptions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hogwarts.school.service.FacultyService;

public class NotFoundExceptionAvatar extends RuntimeException{

    public NotFoundExceptionAvatar() {
        super("Avatar with this id not found");
    }
    public NotFoundExceptionAvatar(long id) {
        super("Avatar with this id " + id + " not found");
    }
}
