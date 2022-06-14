/**
This is the first draft of the EndScreen class. 
<p>
Current features include:
* <ul>
     <li> stuff
* </ul>
</p>
* <p>
* Version date: 06/13/2022
* @author Alexandra Mitnik
* @version: idk
* </p>
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class EndScreen extends JPanel
  {
    Game game;
    private Font consolas;
    
    public EndScreen (Game g)
    {
      game = g;
      consolas = new Font ("res/Consolas.ttf", Font.PLAIN, 86);
    }

    public void paintComponent (Graphics2D g)
    {
      System.out.println ("this runs");
      Game.graphics = (Graphics2D) g;
      this.requestFocus();
      Game.graphics.drawImage(ImageReader.reader("res/transition/finalScore"), 0, 0, null);
      Game.graphics.setFont (consolas);
      Game.graphics.setColor (new Color(92, 23, 40));
     // Game.graphics.drawString("" + game.getPlayerScore(), 800, 
    }
  }