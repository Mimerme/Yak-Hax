package Yak_Hax.Yak_Hax_Mimerme.Exceptions;

public class SleepyServerException extends Exception{
	public SleepyServerException(){
		
	}
	
    public SleepyServerException (String message) {
        super (message);
    }

    public SleepyServerException (Throwable cause) {
        super (cause);
    }

    public SleepyServerException (String message, Throwable cause) {
        super (message, cause);
    }
}
