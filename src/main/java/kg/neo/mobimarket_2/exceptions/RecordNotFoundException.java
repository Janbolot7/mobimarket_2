package kg.neo.mobimarket_2.exceptions;

public class RecordNotFoundException extends  RuntimeException{
    public RecordNotFoundException(String message){
        super(message);
    }
}
