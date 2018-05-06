/**
  * Exception for if no song is playing and the music is tried to be stopped.
  * @author Zachary Ferguson
  * @version 0.1.0
  */
public class NoSongPlayingException extends Exception{
	/**
	  * Creates a String representation of the NoSongPlayingException.
	  * @return Returns the String error message "NoSongPlayingException: There
	 * is no song currently playing".
	  */
	public String toString()
	{
		return "NoSongPlayingException: There is no song currently playing.";
	}
}
