/**
  * Class for storing different information about songs. Includes song title,
  * music file name, artwork file name, and album name.
  * @author Zachary Ferguson
  * @version 0.1.0
  */
public class SongDetails implements Comparable
{
	/** String for the title of the song */
	private final String title;

	/** String for the name of the music file */
	private final String musicFilename;

	/** String for the name of the song artwork file */
	private final String artFilename;

	/** String for the name of the album */
	private final String album;

	/**
	  * Constructor that only need the title of the song and the general file
	 * name for both the music and artwork file
	  * @param title A String for the title of the song.
	  * @param filename A String for the name of the music and artwork file.
	  */
	public SongDetails(String title, String filename)
	{
		this(title, filename, filename, "");
	}

	/**
	  * Constructor that takes a String for every field (song title, music file
	 * name, artwork file name, and album name).
	  * @param title A String for the title of the song.
	  * @param musicFilename A String for the name of the music file.
	  * @param artFilename A String for the name of the artwork file.
	  * @param album A String for the name of the album containing the song.
	  */
	public SongDetails(String title, String musicFilename, String artFilename,
	  String album)
	{
		this.title = title;
		this.musicFilename = musicFilename;
		this.artFilename = artFilename;
		this.album = album;
	}


	/**
	  * Gets the title of the song.
	  * @return Returns a String for the song title.
	  */
	public String getTitle()
	{
		return this.title;
	}

	/**
	  * Gets the standard file name of the file containg the music and artwork.
	  * @return Returns a String for the standard filename.
	  */
	public String getFilename()
	{
		return this.getMusicFilename();
	}

	/**
	  * Gets the name of the file containing the music.
	  * @return Returns a String for the file containing the music.
	  */
	public String getMusicFilename()
	{
		return this.musicFilename;
	}

	/**
	  * Gets the name of the file containing the artwork.
	  * @return Returns a String for the file containing the artwork.
	  */
	public String getArtFilename()
	{
		return this.artFilename;
	}

	/**
	  * Gets the name of the album containg the song.
	  * @return Returns a String for the album name.
	  */
	public String getAlbum()
	{
		return this.album;
	}

	/**
	  * Creates a String containing all four fields separated by new lines. The
	 *last line does not have a new line. Could be used to print to a file while
	 * creating a songlist.
	  * @return Returns a String containing all of the fields, title, music file
	 * name, art file name, and the album name. All of the fields are separated
	 * by new lines.
	  */
	public String toString()
	{
		return String.format("%s\n%s\n%s\n%s", this.title, this.musicFilename,
		this.artFilename, this.album);
	}

	/**
	  * Compares this SongDetails with the given Object. Non-SongDetails always
	 * go after SongDetails, and "exit" always goes before any other song. Sorts
	 * first by album name then by song title. Art and music file names do not
	 * play a role in comparing.
	  * @param o An Object to compare this SongDetails with.
	  * @return Returns an int less than 0 if this sorts before o, 0 is this
	 * equals o, or an int greater than 0 if this sorts after o.
	  */
	public int compareTo(Object o)
	{
		/* SongDetails sort first before other Objects */
		if(!(o instanceof SongDetails))
			return 1;

		SongDetails songO = (SongDetails) o;

		/* Exit always sorts first */
		if(this.getFilename().equals("exit"))
			return -1;
		if(songO.getFilename().equals("exit"))
			return 1;

		/* If same album then compare titles */
		if(this.album.equals(songO.album))
			return this.title.compareTo(songO.title);
		return this.album.compareTo(songO.album);
	}
}
