package kz.itstep.customauth.Exceptions;

public class UserException extends  RuntimeException{
    public UserException(String statement){
        super(statement);
    }
}
