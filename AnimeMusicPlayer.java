/**
  * @author Zachary Ferguson
  * @version 0.2.0
  * Music Player for playing anime music.
  */

import sun.audio.*; /* Needs to be replaced to negate warning messages. */
//import javax.sound.sampled.*;
// import javafx.scene.media.Media;
// import javafx.scene.media.MediaPlayer;

import java.io.*;

public class AnimeMusicPlayer implements MusicPlayer
{  
   /** Boolean for if the Player is currently playing. */
   private boolean playing;
   
   /** AudioStream of the music that is played. */
   private AudioStream aStream; // The music stream

//    private MediaPlayer song;
   
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
         if(this.playing) this.stopMusic();
         FileInputStream musicStream = new FileInputStream(filename);
         this.aStream = new AudioStream(musicStream);
         AudioPlayer.player.start(aStream);
         //File musicFile = new File(filename);
         //Clip clip = AudioSystem.getClip();
         //AudioInputStream ais = AudioSystem.getAudioInputStream(musicFile);
         //clip.open(ais);
//       Media song_media = new Media(filename);
//       this.song = new MediaPlayer(song_media);
//       this.song.play();
         this.playing = true;
      }
      catch(IOException ioe)
      {
        System.out.printf("No file, %s, found. Please try again.\n" + ioe, filename);
      }
      catch(NoSongPlayingException nsp)
      {
         this.playing = false;
      }
      //catch(LineUnavailableException lue)
      //{
      //   System.out.printf("Unable to read file, %s, please try again.\n" + lue, filename);
      //}
      //   catch(UnsupportedAudioFileException uafe)
      //{
      //   System.out.printf("Unsupported file format, %s, please try a different format.\n" + uafe, filename);
      //}
   }
   
   /**
     * Stops the music currently playing.
     * @throws NoSongPlayingException Throws exception if no song is currently playing.
     */
   public void stopMusic() throws NoSongPlayingException
   {
      if(!this.isPlaying()) throw new NoSongPlayingException();
      //this.song.stop();
      AudioPlayer.player.stop(aStream);
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