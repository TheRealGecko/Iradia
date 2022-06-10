/**
This is the third draft of the Stage2 class. This class was added as of version 1.2.0.
<p>
Changes include:
* <ul>
     <li>Adding the cases
     <li>Allowing user input
     <li>Evaluating the user's answer
     <li>Incrementing the user's score for correct answers
* </ul>
</p>
* <p>
* Version date: 06/03/2022
* @author Alexandra Mitnik
* @version: 1.3.63
* </p>
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Stage2 extends JPanel implements KeyListener, MouseListener
  {

    Image table;
    Image buttons;
    Image tutorialCase;
    Image[] dialogue;
    Image dialogueBack;
    int pos;
    Game game;
    int caseNum;

    ArrayList<Image> cases;
    ArrayList<Boolean> isToxic;
    ArrayList<Image> reasons;
    ArrayList<Image> profiles;
    boolean answer;
    boolean nextStage;

    /**
     * Stage2 class's constructor. Initializes the table, button, case, and dialogue images, correct answers, and the value 
      used to iterate through cases. Also prepares for user input.
     */
    public Stage2(Game g) {
      game = g;
      cases = new ArrayList<Image>(); // Case images
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

        reasons = new ArrayList<Image>();
        for(int i = 1; i <= 7; i++) {
            for(int j = 0; j <= 1; j++) {
                String dir = "res/stage2/reasons/c" + i;

                if(j == 0) {
                    dir += "_right";
                } else {
                    dir += "_wrong";
                }

                dir += ".png";

                reasons.add(ImageReader.reader(dir));
            }
        }

        profiles = new ArrayList<Image>();
        Image[] temp = ImageReader.storeDir("res/stage2/profiles/");
        profiles.addAll(Arrays.asList(temp).subList(0, 7));

        isToxic = ImageReader.isToxic (cases); // Case answers
      
        table = ImageReader.reader("res/stage2/clip_bg.png"); // Table image
        buttons = ImageReader.reader ("res/stage2/yes_no.png"); // Button image
        dialogue = ImageReader.storeDir("res/stage2/text/"); // Dialogue images
        dialogueBack = ImageReader.reader("res/header_base.png"); // Dialogue background image
        tutorialCase = ImageReader.reader("res/stage2/cases/Tutorial_Case.png");

        pos = 1; // Dialogue position

        nextStage = false;

        this.setFocusable(true); // Allows the class to receive user input
        this.addKeyListener(this);
        addMouseListener(this);
    }

           /**
      * Displays the graphics for the tutorial dialogue.
      */
    private void tutorial() {
        Game.graphics.drawImage(table, -9, 0, null);
            if (pos >= 5)
        Game.graphics.drawImage (tutorialCase, 0, 0, null);
      if (pos != 5)
      {
        Game.graphics.drawImage(dialogueBack, 40, 50, null);
        Game.graphics.drawImage(dialogue[pos], 40, 50, null);
      }
    }

       /**
      * Displays the graphics for a random case the player has not yet solved.
      */
   private void getCase() {
       if(pos % 2 == 0) {
           Game.graphics.drawImage(table, -9, 0, null);
           caseNum = (int) (Math.random() * cases.size());
           Game.graphics.drawImage(cases.remove(caseNum), 0, 0, null);
           Game.graphics.drawImage(profiles.remove(caseNum), 350, 197, null);
           answer = isToxic.remove(caseNum);
       }
    }

    
     /**
      * Displays the graphics for the prompt asking the player if the case is toxic.
      */
    private void prompt() {
        if(pos % 2 == 0) {
            Game.graphics.drawImage(buttons, -9, 0, null);
            Game.graphics.drawImage(dialogue[0], 0, 0, null);
        } else {
            if(answer) {
                Game.graphics.drawImage(reasons.remove(caseNum * 2), 0, 0, null);
                reasons.remove(caseNum * 2);
            } else {
                Game.graphics.drawImage(reasons.remove((caseNum * 2) + 1), 0, 0, null);
                reasons.remove(caseNum * 2);
            }
        }
    }

     /**
      * Displays the graphics necessary for stage 2. Calls tutorial (), prompt (), or getCase() depending on which screen the 
        player is on.
      * @param g     Used to draw graphics.
      */
    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;
        this.requestFocus();
        if(pos < 8) {
            tutorial();
        } else if(cases.size() != 0){
           getCase();
           prompt();
        } else {
            Game.graphics.drawImage(ImageReader.reader("res/transition/end2.png"), 0, 0, null);
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
    * When any key is pressed, switches through dialogue in the tutorial + opens prompt for user input on cases
    * @param e     Pressing a key
    */
    @Override
    public void keyPressed(KeyEvent e) {
        if (cases.size() == 0) {
            nextStage = true;
        } else if(pos < 8 || (pos != 8 && pos % 2 == 1))
        {
        pos++;
        repaint();
        }

        if(nextStage) {
            Game.frame.remove(this);
            Game.frame.add(new Stage3(game));
            Game.frame.pack();
        }
    }
    
      /**
    * Key being typed (method not used but is necessary to implement 
    KeyListener)
    * @param e     Typing a key
    */
    @Override
    public void keyTyped(KeyEvent e) {

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
     * Increases the user's score if they clicked the correct answer + switches to next case file.
     * @param e     A click while stage 2 is
     *              onscreen
     */
    @Override
    public void mousePressed(MouseEvent e) {
    if (pos >= 8 && pos % 2 == 0)
    {
      if ((e.getX() <= 500 && answer) || (e.getX() > 500 && !answer)) {
          answer = true;
          game.increasePlayerScore();
      } else {
          answer = false;
      }
      pos++; 
      repaint();
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
