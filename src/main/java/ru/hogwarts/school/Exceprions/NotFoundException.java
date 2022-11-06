package ru.hogwarts.school.Exceprions;

public class NotFoundException extends RuntimeException{

//    public NotFoundException(String message) {
//        super(message);
//    }

    public NotFoundException() {
        super("request with this id not found");
    }
}
