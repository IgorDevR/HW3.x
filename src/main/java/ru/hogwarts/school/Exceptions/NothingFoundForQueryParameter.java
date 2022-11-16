package ru.hogwarts.school.Exceptions;


public class NothingFoundForQueryParameter extends RuntimeException{

//    public NotFoundException(String message) {
//        super(message);
//    }

    public NothingFoundForQueryParameter() {
        super("Nothing found for this query parameter");
    }
}
