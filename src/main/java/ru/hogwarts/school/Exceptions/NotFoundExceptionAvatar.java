package ru.hogwarts.school.Exceptions;


public class NotFoundExceptionAvatar extends RuntimeException{

    public NotFoundExceptionAvatar() {
        super("Avatar with this id not found");
    }

    public NotFoundExceptionAvatar(long id) {
        super("Avatar with this id " + id + " not found");
    }
}
