package lk.ranaweera.api.exception;

public class UserAlreadyRegisteredException extends Exception{
    public UserAlreadyRegisteredException(String message){
        super(message);
    }
}
