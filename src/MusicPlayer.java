/**
  * Music Player for playing music.
  * @author Zachary Ferguson
  * @version 0.1.0
  */
public abstract class MusicPlayer
{
	/**
	  * Plays the music contained in the file with the given name.
	  * @param filename String for the name of the file containing the music.
	  */
	public abstract void playMusic(String filename);
	
	/**
	  * Stops the music currently playing.
	  * @throws NoSongPlayingException Throws exception if no song is currently
	  * 	playing.
	  */
	public abstract void stopMusic() throws NoSongPlayingException;
	
	/**
	  * Determines if music is playing or not.
	  * @return Returns a boolean for if the music is playing. 
	  */
	public abstract boolean isPlaying();
}
