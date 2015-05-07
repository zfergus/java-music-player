/**
  * @author Zachary Ferguson
  * @version 0.0.1a
  * JFrame child class for displaying song artwork.
  */

import java.io.*;
import java.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

public class ArtFrame extends JFrame
{   
   /** The Art to be displayed in the Frame. */
   Art artwork;
   
   /**
     * Constructor for ArtWork using the given imageFile. 
     * @param title Title of the artwork to be displayed.
     * @param width Width of the ArtFrame in pixels.
     * @param height Height of the ArtFrame in pixels.
     * @param imageFile File contaning the image to be displayed in the frame.
     * @throws IOException Throws an IOException if something goes wrong while reading the imageFile.
     */
   public ArtFrame(String title, int width, int height, File imageFile) throws IOException
   {
      this.setTitle(title);
      this.setSize(width, height);
      this.artwork = new Art(imageFile);
      this.add(artwork);
   }
   
   /**
     * Replaces the current artwork in the frame with the new one from the given File.
     * @param title String for the title of the Art to be displayed.
     * @param imageFile File contaning the new image to be displayed in the frame.
     * @throws IOException Throws an IOException if unable to read the image from the given File.
     */
   public void repaint(String title, File imageFile) throws IOException
   {
      this.remove(artwork);
      this.setTitle(title);
      this.artwork = new Art(imageFile);
      this.add(artwork);
   }
   
   public class Art extends JComponent
   {
      /** The image that is displayed when the Art is painted */
      private Image image;
      
      /**
        * Constructor that creates an Art from with the Image in the given File.
        * @param imagefile File that contains the image that should be displayed when the Art is painted.
        * @throws IOException Throws an IOException if unable to read the image from the given File.
        */
      public Art(File imagefile) throws IOException
      {
         this.image = ImageIO.read(imagefile);
      }
   
      /**
        * Paints the image using the given graphic
        * @param g Graphic used to draw the image
        */
      public void paintComponent(Graphics g)
      {
         if(image == null) 
            return; 
         g.drawImage(this.image, 0, 0, this);
	      super.setBackground(Color.BLACK);
      }
   }
}
