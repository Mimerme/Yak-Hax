package Yak_Hax.Yak_Hax_Mimerme.Exceptions;

public class ImproperRequestException extends Exception {
	public ImproperRequestException(){
		
	}
	
    public ImproperRequestException (String message) {
        super (message);
    }

    public ImproperRequestException (Throwable cause) {
        super (cause);
    }

    public ImproperRequestException (String message, Throwable cause) {
        super (message, cause);
    }
}