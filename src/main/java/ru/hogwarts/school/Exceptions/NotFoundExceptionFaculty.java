package ru.hogwarts.school.Exceptions;


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
