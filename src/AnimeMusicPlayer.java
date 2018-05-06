import java.io.*;
import javax.sound.sampled.*;

/**
  * Music Player for playing anime music.
  * @author Zachary Ferguson
  * @version 0.2.0
  */
public class AnimeMusicPlayer extends MusicPlayer
{
	/** Boolean for if the Player is currently playing. */
	private boolean playing;

	/** AudioInputStream of the music that is played. */
	private AudioInputStream audioStream; // The music stream

	/** Clip of the music that is played. */
	private Clip song; // The music stream

	/**
	  * Default constructor for AnimeMusicPlayer.
	  * Sets the input as System.in, and the playing state to false.
	  */
	public AnimeMusicPlayer()
	{
		this.playing = false;
	}

	/**
	  * Plays the music contained in the file with the given name.
	  * @param filename String for the name of the file containing the music.
	  */
	public void playMusic(String filename)
	{
		try
		{
			if(this.playing)
			{
				this.stopMusic();
			}

			this.audioStream = AudioSystem.getAudioInputStream(
				new File(filename).getAbsoluteFile());
		    this.song = AudioSystem.getClip();
		    this.song.open(this.audioStream);
		    this.song.start();

			this.playing = true;
		}
		catch(IOException ioe)
		{
		  System.out.printf("No file, %s, found. Please try again.\n" + ioe,
			filename);
		}
		catch(NoSongPlayingException nsp)
		{
			this.playing = false;
		}
		catch(UnsupportedAudioFileException e)
		{
			System.out.printf("Unsupported audio format: %s." +
				" Please try again.\n" + e, filename);
		}
		catch(Exception e){
			System.out.print("Something went wrong!" +
				" Please try again.\n" + e);
		}
	}

	/**
	  * Stops the music currently playing.
	  * @throws NoSongPlayingException Throws exception if no song is currently
	  * 	playing.
	  */
	public void stopMusic() throws NoSongPlayingException
	{
		if(!this.isPlaying()) throw new NoSongPlayingException();
		this.song.stop();
		this.song.close();
		this.playing = false;
	}

	/**
	  * Determines if music is playing or not.
	  * @return Returns a boolean for if the music is playing.
	  */
	public boolean isPlaying()
	{
		return playing;
	}

	/**
	  * Creates a String representaton of the player.
	  * @return Returns a String representaton of the player.
	  */
	public String toString()
	{
		return "Anime Music Player ^_^";
	}
}
