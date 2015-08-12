package Yak_Hax.Yak_Hax_Mimerme.Exceptions;

public class AuthorizationErrorException extends Exception {
	public AuthorizationErrorException(){
		
	}
	
    public AuthorizationErrorException (String message) {
        super (message);
    }

    public AuthorizationErrorException (Throwable cause) {
        super (cause);
    }

    public AuthorizationErrorException (String message, Throwable cause) {
        super (message, cause);
    }
}