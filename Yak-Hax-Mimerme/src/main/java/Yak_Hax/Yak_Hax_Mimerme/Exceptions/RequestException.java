package Yak_Hax.Yak_Hax_Mimerme.Exceptions;

//401 - Bad parameters, or requests made too fast
//500 - Bad User Agent or improper headers
public class RequestException extends Exception {
	public RequestException(){
		
	}
	
    public RequestException (String message) {
        super (message);
    }

    public RequestException (Throwable cause) {
        super (cause);
    }

    public RequestException (String message, Throwable cause) {
        super (message, cause);
    }
}