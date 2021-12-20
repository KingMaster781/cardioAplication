package pt.ipleiria.estg.dei.ei.dae.cardioaplication.exceptions;

public class MyEntityExistsException extends Exception{
    public MyEntityExistsException(String errorMessage) {
        super(errorMessage);
    }
}
