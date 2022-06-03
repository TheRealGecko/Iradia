/**
This is the first draft of the Stage2 class. This class was added as of version 1.2.0. Current features include:
* <ul>
*    <li>Setting up the painting code
     <li>Painting the background (same for all screens in this stage)
     <li>Adding the yes and no buttons
     <li>Adding the dialogue/instrucitons box
* </ul>
* <p>
* Version date: 05/27/2022
* @author Alexandra Mitnik
* @version: 1.2.29
* </p>
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Stage2 extends JPanel implements KeyListener, MouseListener
  {

    Image table;
    Image buttons;
    Image[] dialogue;
    Image dialogueBack;
    int pos;

    ArrayList<Image> cases;
    ArrayList<Boolean> isToxic;
    boolean answer;

    /**
     * Stage2 class's constructor. Initializes the table and button images.
     */
    public Stage2() {
      cases = new ArrayList<Image>();
     for (int i = 1; i <= 7; i++)
       {  
         String dir = "res/stage2/cases/Case_Files_" + i;
         if (i == 2 || i == 3 || i == 5 || i == 6)
         {
           dir += "t";
         }
         else
         {
           dir += "f";
         }
         dir += ".png";
         
         cases.add(ImageReader.reader(dir));
       }

       isToxic = ImageReader.isToxic (cases);
      
        table = ImageReader.reader("res/stage2/clip_bg.png");
        buttons = ImageReader.reader ("res/stage2/yes_no.png");
        pos = 1;
        dialogue = ImageReader.storeDir("res/stage2/text/");
        dialogueBack = ImageReader.reader("res/header_base.png");

        this.setFocusable(true);
        this.addKeyListener(this);
        addMouseListener(this);
    }

    public void tutorial() {
        Game.graphics.drawImage(dialogueBack, 40, 50, null);
        Game.graphics.drawImage(dialogue[pos], 40, 50, null);
    }

   public void getCase() {
        Game.graphics.drawImage(table, -9, 0, null);
        Game.graphics.drawImage (buttons, -9, 0, null);
        int caseNum = (int) (Math.random() * cases.size());
        Game.graphics.drawImage(cases.remove(caseNum), 0, 0, null);
        answer = isToxic.remove(caseNum);
    }

    public void prompt() {
        Game.graphics.drawImage(dialogue[0], 328, 488, null);
    }

     /**
      * Displays the graphics necessary for stage 2.
      * @param g     Used to draw graphics.
      */
    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;
        this.requestFocus();
        if(pos < 6 ) {
            Game.graphics.drawImage(table, -9, 0, null);
            Game.graphics.drawImage (buttons, -9, 0, null);
            tutorial();
        } else {
          if (pos % 2 == 0)
          {
           prompt(); 
          }
          else
          {
           getCase();    
          }
        }
    }

    /**
     * KeyListener being added
     * @param l    KeyListener
     */
    @Override
    public synchronized void addKeyListener(KeyListener l) {
        super.addKeyListener(l);
    }
    
      /**
    * Key being typed
    * @param e     Typing a key
    */
    @Override
    public void keyTyped(KeyEvent e) {

    }

      /**
    * Key being pressed
    * @param e     Pressing a key
    */
    @Override
    public void keyPressed(KeyEvent e) {
        if(pos < 6 || (pos !=6 && pos % 2 == 1))
        {
        pos++;
        repaint();
        }
    }

      /**
    * Key being released
    (method not used but is necessary to implement 
    KeyListener)
    * @param e     Releasing a key
    */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * 
     * @param e     A click while stage 2 is
     *              onscreen
     */
    @Override
    public void mousePressed(MouseEvent e) {
    if (pos >= 6 && pos % 2 == 0)
    {
      if ((e.getX() <= 500 && answer == true) || (e.getX() > 500 && answer == false))
        Game.increaseScore();
      if (cases.size() == 0)
      {
      System.out.println ("Done stage 2! Score is: " + Game.getPlayerScore());
      }
      else
      {
      pos++; 
      repaint(); 
      }
    }
}

    /**
     * Clicking mouse (method not used but is necessary
     to implement MouseListener)
     * @param e     A click while the Iradia menu is
     *              onscreen
     */
    @Override
    public void mouseClicked(MouseEvent e) {}

    /**
     * Releasing mouse (method not used but is necessary
     to implement MouseListener)
     * @param e     A release while the Iradia menu is
     *              onscreen
     */
    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
     * Mouse entering the bounds of a component (method
     not used but is necessary to implement
     MouseListener)
     * @param e     Entering the bounds of a component
     */
    @Override
    public void mouseEntered(MouseEvent e) {}

    /**
     * Mouse exiting the bounds of a component (method
     not used but is necessary to implement
     MouseListener)
     * @param e     Exiting the bounds of a component
     */
    @Override
    public void mouseExited(MouseEvent e) {}
}
