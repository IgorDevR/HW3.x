package ru.hogwarts.school.Exceptions;


public class NotFoundExceptionStudent extends RuntimeException{

//    public NotFoundException(String message) {
//        super(message);
//    }

    public NotFoundExceptionStudent() {
        super("Student with this id not found");
    }
    public NotFoundExceptionStudent(long id) {
        super("Student with this id " + id + " not found");
    }
}
