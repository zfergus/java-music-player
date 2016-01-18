import java.io.*;
import java.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

/**
  * Prompt the user for the song they want to play and plays it, displaying 
  * artwork too.
  * @author Zachary Ferguson
  * @version 0.2.0
  */
public class PlayMusic
{
	/** Scanner for getting user input from the cmd line. */
	private static Scanner input = new Scanner(System.in);

	/**
	* Runs the PlayMusic program, prompting for various inputs, playing music, 
	* and displaying artwork.
	* @param args Command line arguments(not used).
	*/
	public static void main(String[] args)
	{
		Scanner playlist;                 /* Playlist of songs to play by number.    */ 
		ArrayList<SongDetails> songinfos; /* List of all available songs' song data. */
		AnimeMusicPlayer kawaii;          /* Music player that plays the songs.      */
		ArtFrame frame = null;            /* Frame to display the song artwork.      */
		SongDetails song;                 /* Details of the song to be played.       */
		String musdir = "./mus/",         /* Directory containing the music files.   */
		       musExt = ".wav",           /* Extension of the music files.           */
		       picdir = "./art/",         /* Directory containing the artwork files. */
		       picExt = ".png";           /* Extension of the artwork files.         */
		boolean painted = false;          /* Whether or not art is painted.          */


		try /* Possible Exceptions from IO */
		{

			//songinfos = buildSonglist("./songlists/default.songlist");
			System.out.print(
				"Songlist path [default: ./songlists/default.sl2]: ");
			String songlistPath = input.next();
			
			File tmp = new File(songlistPath);
			if(tmp.exists())
			{
				songinfos = buildSonglist2(songlistPath);
			}
			else
			{
				songinfos = buildSonglist2("./songlists/default.sl2");
			}

			//saveSonglist("./songlists/test.sl2", songinfos);

			playlist = getPlaylist(songinfos.size());  /* Get playlist scanner            */
			kawaii = new AnimeMusicPlayer();           /* Create AMP from playlist        */
			song = getSongChoice(playlist, songinfos); /* Get the first song name to play */

			/* While the user wants to play a song. */
			while(!song.getFilename().equals("exit"))
			{
				/************** Play music of choice **************/
				/* If playing: stop */
				if(kawaii.isPlaying())
				{
					kawaii.stopMusic();
				}
				
				kawaii.playMusic(musdir + song.getMusicFilename() + musExt);
				
				/************** Display artwork **************/
				if(painted)
				{
					frame.repaint(song.getTitle(), new File(picdir + 
					song.getArtFilename() + picExt));
					frame.setVisible(true);
				}
				else
				{
					frame = new ArtFrame(song.getTitle(), new File(picdir + 
					song.getArtFilename() + picExt));
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setResizable(false);
					frame.setVisible(true);
					painted = true;
				}
				
				/* Pause for next command. */
				System.out.printf("\n(Type anything to go back to the menu)");
				input.next();
				System.out.printf("\n");

				/* Get next song */
				song = getSongChoice(playlist, songinfos);
			}
			
			/* Stop music before exiting */
			if(kawaii.isPlaying())
			{
				kawaii.stopMusic();
			}
			System.exit(0); /* Forces everything to close */
		}
		catch(Exception e) /* Catch any IO or SongNotPlaying Exceptions */
		{
		System.out.printf("\n\nSomething went wrong.\n%s\n", e.toString());
		}
	}

	/**
	* Constructs a list of SongDetails from the File with the given name.
	* @param filename Name of the songlist file containing the song titles and filenames.
	* @return Returns a new ArrayList of SongDetails aquired from the File.
	* @throws IOException Throws an exception if something goes wrong while reading the songlist file.
	*/
	private static ArrayList<SongDetails> buildSonglist(String filename) 
		throws IOException
	{
		try
		{
			ArrayList<SongDetails> songinfos = new ArrayList<SongDetails>();
			Scanner songin = new Scanner(new File(filename));
			String title, songfilename;
			if(!songin.hasNextLine())
			{
				throw new IOException(String.format("Empty file, %s!", 
					filename));
			}
			
			while(songin.hasNextLine())
			{
				title = songin.nextLine();
				if(!songin.hasNextLine())
				{
					throw new IOException(String.format(
						"No music filename for file, %s.", title));
				}
				songfilename = songin.nextLine();
				songinfos.add(new SongDetails(title, songfilename));
			}
			//return sortSonglist(songinfos);
			return songinfos;
		}
		catch(IOException ioe)
		{
			System.out.printf(
				"Fatal error: unable to open songlist file, %s.\n%s\n\n" +
				"Exiting buildSonglist2()\n\n", filename,  ioe.toString());
			throw ioe;
		}
	}

	/**
	* Constructs a list of SongDetails from the File with the given name.
	* @param filename Name of the songlist file containing the song titles and 
	*	filenames.
	* @return Returns a new ArrayList of SongDetails aquired from the File.
	* @throws IOException Throws an exception if something goes wrong while 
	*	reading the songlist file.
	*/
	private static ArrayList<SongDetails> buildSonglist2(String filename) 
		throws IOException
	{
		try
		{
			ArrayList<SongDetails> songinfos = new ArrayList<SongDetails>();
			Scanner songin = new Scanner(new File(filename));
			String title, songfilename, artfilename, album;
			if(!songin.hasNextLine())
			{
				throw new IOException(String.format("Empty file, %s!", 
					filename));
			}
			
			while(songin.hasNextLine())
			{
				title = songin.nextLine();
				if(!songin.hasNextLine())
				{
					throw new IOException(String.format(
						"No music filename for file, %s.", title));
				}
				
				songfilename = songin.nextLine();
				if(!songin.hasNextLine())
				{
					throw new IOException(String.format(
						"No art filename for file, %s.", title));
				}
				
				artfilename  = songin.nextLine();
				if(!songin.hasNextLine())
				{
					throw new IOException(String.format(
						"No album for, %s.", title));
				}
				
				album = songin.nextLine();
				songinfos.add(
					new SongDetails(title, songfilename, artfilename, album));
			}
			return sortSonglist(songinfos);
			//return songinfos;
		}
		catch(IOException ioe)
		{
			System.out.printf(
				"Fatal error: unable to open songlist file, %s.\n%s\n\n" +
				"Exiting buildSonglist2()\n\n", filename,  ioe.toString());
			throw ioe;
		}
	}

	/**
	* Gets a Scanner to get the playlist from.
	* The playlist is a list of songs to play by number.
	* @param songCount The number of songs available. 
	*		Used to make a playlist of all song and to make a shuffled playlist.
	* @return Returns a Scanner that scans over the playlist selected.
	*/
	private static Scanner getPlaylist(int songCount)
	{
		int choice; /* User's menu choice */
		while(true) /* Until valid choice */
		{
			/* Get choice */
			System.out.printf("Which playlist source?\n" +
				"1) All: Play all songs available\n" +
				"2) Create one: Create a playlist of songs\n\t(Note: you can " +
					"save this playlist as a file too)\n" +
				"3) File: Read a pre-built playlist from a file\n" + 
				"4) Shuffle: Play a random number of songs in random order\n" +
				"5) Custom: Select songs to play from the menu\n" +
				"#");
			
			try
			{
				choice = input.nextInt();
			}
			catch(Exception e)
			{
				input.next();
				System.out.printf("Invalid choice, please try again.\n\n");
				continue;
			}
			System.out.printf("\n");
			input.nextLine();
			
			/* Make playlist */
			switch(choice)
			{
				case 1:  /* Play all */
					return makeAllPlaylist(songCount);
				case 2:  /* Make playlist from string */
					return getStringPlaylist();
				case 3:  /* Read playlist from file */
					return getFilePlaylist();
				case 4:  /* Make a shuffled playlist */
					return makeShufflePlaylist(songCount);
				case 5:  /* User control */
					return input;
				default: /* Invalid choice */
					System.out.printf(
						"Invalid choice, %d, please try again.\n\n", choice);
			}
		}
	}

	/**
	* Makes a playlist containing all available songs in order
	* @param songCount The total number of songs available including "0", exit.
	* @return Returns a Scanner that scans over all available songs.
	*/
	private static Scanner makeAllPlaylist(int songCount)
	{
		StringBuilder all = new StringBuilder();

		for(int i = 1; i < songCount; i++)
		{
			all.append(String.format("%d ", i));
		}
		all.append("0");

		return new Scanner(all.toString());
	}

	/**
	* Makes a playlist from the users input string
	* @return Returns a Scanner that scans over all the song in the users input.
	*/
	private static Scanner getStringPlaylist()
	{
		int choice;

		System.out.print("Enter the song numbers to play(on the same line): ");
		String playlist = input.nextLine();
		playlist += " 0";

		/* Prompts to save playlist */
		do{
			System.out.print("Would you like to save this playlist to a file?\n"
				+"1)Yes\n"
				+"0)No\n"
				+"#");

			choice = input.nextInt();
			
			if(choice == 1)
			{
				savePlaylist(playlist);
			}
			else if(choice < 0 || choice > 2)
			{
				System.out.printf(
					"Invalid choice, %d. Please try again.\n\n", choice);
			}
		}
		while(choice < 0 || choice > 2); /* Until valid choice */
		
		return new Scanner(playlist);
	}

	/**
	* Saves the given String playlist to a file.
	* @param playlist String playlist to write out to file.
	*/
	private static void savePlaylist(String playlist)
	{
		String filename = "";
		try
		{
			/* Get filename */
			System.out.print("Please enter the file name: ");
			filename = input.next();
			
			/* Attempt to write out */
			PrintWriter out = new PrintWriter(new File(filename));
			out.print(playlist);
			out.close();
			
			/* Successfully wrote file */
			System.out.printf("Playlist successfully written to file %s.\n", 
				filename);
		}
		catch(IOException e)
		{
			System.out.printf(
				"Unable to save playlist to file, %s. Try again later.", 
				filename);
		}
	}

	/**
	* Makes a playlist from the selected file containing a playlist
	* @return Returns a Scanner that scans over all the songs specified in the 
	*	file.
	*/
	private static Scanner getFilePlaylist()
	{
		/* Get filename */
		System.out.print("Enter the filename: ");
		String filename = input.next();

		/* Try to open playlist */
		try
		{
			Scanner fileIn = new Scanner(new File(filename));
			return fileIn;
		}
		/* Unable to open */
		catch(Exception ioe)
		{
			System.out.printf(
				"Unable to open file, %s. Reverting back to custom control.\n", 
				filename);
				
			/* Return system.in scanner if file failed to open. */
			return input;
		} 
	}

	/**
	* Makes a playlist containing a random number, r, of songs in random order. 
	* Where r is greater than 5 and less than 100.
	* @param songCount The total number of songs available including "0", exit.
	* @return Returns a Scanner that scans over at least 5 songs in random 
	*	order.
	*/
	private static Scanner makeShufflePlaylist(int songCount)
	{
		StringBuilder all = new StringBuilder();
		
		/* Get the number of songs to play */
		int play_count = (int)(Math.random() * 95 + 5);
		
		/* Print the number of songs to be played. */
		System.out.printf("Playing %d shuffled songs.\n\n", play_count);

		/* Builds string playlist of random ints, i. Where 0 < i < songCount */
		for(int i = 1; i < play_count; i++)
		{
			all.append(String.format("%d ", 
				(int)(Math.random()*(songCount-1)+1)));
		}
		all.append("0");

		return new Scanner(all.toString()); 
	}

	/**
	* Gets the users choice of song form the Scanner in input.
	* @param playlist Scanner to get the input for the song to be chosen.
	* @param songinfos ArrayList of Song Details to be used for displaying the 
	*	song options by title and for getting the filename of the choice to 
	*	return. 
	* @return String of file name with out .extension.
	*/
	private static SongDetails getSongChoice(Scanner playlist, 
		ArrayList<SongDetails> songinfos)
	{
		int choice;
		while(true)
		{
			/* Prompt user for the song to play. */
			System.out.printf("Which song?\n");
			for(int i = 1; i < songinfos.size(); i++)
			{
				System.out.printf("%2d) %s\n", i, songinfos.get(i).getTitle());
			}
			System.out.printf("%2d) %s\n# ", 0, songinfos.get(0).getTitle());
			
			/* Get choice from Scanner playlist for the song to play. */
			try /* Possible IOException */
			{
				choice = playlist.nextInt();
			}
			catch(InputMismatchException ine)
			{
				playlist.next();
				System.out.printf("Invalid choice, please try again.\n\n");
				continue;
			}
			catch(Exception e) /* Something went wrong getting the choice. */
			{
				choice = 0; /* Use default choice, 0, to exit. */
			}
			
			if(choice > 0 && choice < songinfos.size())
			{
				System.out.printf("\n\nPlaying %s.\n", 
					songinfos.get(choice).getTitle());
				return songinfos.get(choice);
			}
			else if(choice == 0)
			{
				System.out.printf("\n\n%s.\n", 
					songinfos.get(choice).getTitle());
				return songinfos.get(choice);
			}
			else
			{
				System.out.printf("Invalid choice, %d, please try again.\n\n", 
					choice);
			}
		}
	}

	/**
	* Creates a songlist file with the, with the given name, containing the 
	* contents of the given ArrayList of SongDetails 
	* @param filename A String for the name of the file to save the songlist to.
	* @param songlist An ArrayList of SongDetails that will be printed out to 
	*	the created file.
	* @throws IOException Throws an IOException if the file is unable to open 
	*	for writing.
	*/
	private static void saveSonglist(String filename, ArrayList<SongDetails> 
		songlist) throws IOException
	{
		try
		{
			PrintWriter songsout = new PrintWriter(new File(filename));
			int i = 0;
			for(SongDetails info : songlist)
			{
				if(i != 0)
					songsout.print("\n");
				songsout.print(info.toString());

				i++;
			}
			
			songsout.close();
		}
		catch(IOException io)
		{
			System.out.printf("Unable to open file for writing, %s.\n%s\n", 
				filename, io.toString());
			throw io;
		}
	}

	/**
	* Sorts the given ArrayList of SongDetails in the order specified by the 
	* SongDetails' compareTo(). New list will be return, and the original list 
	* will remain unchanged. Uses a QuickSort algorithm to sort the list.
	* @param songlist The ArrayList of SongDetails to be sorted. Will remain 
	*	unchanged.
	* @return Returns a copy of the given ArrayList that is sorted and 
	*	independent of the original.
	*/
	@SuppressWarnings("unchecked")
	private static ArrayList<SongDetails> sortSonglist(ArrayList<SongDetails> 
		songlist)
	{
		return QuickSort.sort(songlist);
	} 
}
