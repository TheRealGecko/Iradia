import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.io.*;

public class Stage3 extends JPanel implements KeyListener, MouseListener
{
    private Game game;
    private Sprite sprite;
    private Thread dSprite;
    private Image background;
    private Image dialogueBack;
    
    private Image[] introDialogue;
    private Image[] endDialogue;
    private int pos;
    private int paperPos;
    private int caseNum;
    private int s3Score;
    private ArrayList<Case> cases;
    private static boolean caseOpen;
    private boolean isTouched;
    private boolean isPressed;

Font consolas;
  
  boolean nextScene;
  
    public Stage3(Game g)
    {
    isPressed = false;
    game = g;
    sprite = new Sprite ();
    pos = 0;
    paperPos = 0;
    s3Score = 0;
    background = ImageReader.reader ("res/stage3/background.png");
    introDialogue = ImageReader.storeDir("res/stage3/introText/");
    endDialogue = ImageReader.storeDir("res/stage3/endDialogue/");
    dialogueBack = ImageReader.reader("res/header_base.png"); // Dialogue background image

nextScene = false;
      
    cases = new ArrayList<Case>();
    int[] tempAnswers = {3, 2, 3, 3, 3, 2}; 
      
    for (int a = 1; a <= 6; a++)
    {
    String caseImgAddress = "res/stage3/files/" + a + ".png";
    String optionImgAddress = "res/stage3/options/c" + a + ".png";
    String answerImgAddress = "res/stage3/answers/c" + a + "_answers.png";
    String dialogueDir = "res/stage3/caseDialogue/c" + a + "/";
    cases.add (new Case (caseImgAddress, optionImgAddress, answerImgAddress, dialogueDir, tempAnswers[a - 1]));
    }

      cases.get(0).setImgCoords(682, 1000, 0, 28);
      cases.get(1).setImgCoords(638, 1000, 208, 269);
      cases.get(2).setImgCoords(426, 590, 368, 650);
      cases.get(3).setImgCoords(252, 330, 118, 122);
      cases.get(4).setImgCoords(0, 98, 0, 52);
      cases.get(5).setImgCoords(0, 48, 420, 650);
     

consolas = new Font ("res/Consolas.ttf", Font.PLAIN, 86);
      
      this.setFocusable(true); // Allows the class to receive user input
      this.addKeyListener(this);
      addMouseListener(this);
    }
    
    /**
      * Displays the graphics necessary for stage 3.
      * @param g     Used to draw graphics.
      */
    @Override
    public void paintComponent(Graphics g) {
  
        Game.graphics = (Graphics2D) g;
        this.requestFocus();
      
       if (!caseOpen) {
        Game.graphics.drawImage (background, 0, 0, null); 
        if(pos >= 7) {
            for (int i = 0; i < cases.size(); i++) {
                Game.graphics.drawImage(cases.get(i).getCaseImg(), 0, 0, null);
            }
            if (cases.size() > 0 && !caseOpen) {
                for(int a = 0; a < cases.size(); a++) {
                if (cases.get(a).isTouched(sprite.getXPos(), sprite.getYPos())) {
                  caseNum = a;
                    caseOpen = true;
                    paperScreen();
                    break;
                }
                }
            }
        }
         if (pos < 10 && cases.size() > 0)
          Game.graphics.drawImage(introDialogue[pos], 0, 0, null);
        else if(cases.size() > 0)
        {
        dSprite = new Thread(new DrawSprite(Game.graphics, sprite));
        dSprite.run();
        } else if(pos < 2 && !nextScene) {
          Game.graphics.drawImage(dialogueBack, 40, 50, null);
          Game.graphics.drawImage(endDialogue[pos], 0, 0, null);
        } else if(pos >= 2 && !nextScene) { Game.graphics.drawImage(ImageReader.reader("res/transition/end3.png"), 0, 0, null);
            Game.graphics.setFont (consolas);
            Game.graphics.setColor (new Color(92, 23, 40));
            Game.graphics.drawString("" + s3Score, 800, 310);
                nextScene = true;
                pos = 0;
                endDialogue = ImageReader.storeDir("res/transition/endDialogue/");
        } else if(pos < 7 && nextScene) {
          Game.graphics.drawImage(endDialogue[pos], 0, 0, null);
          pos++;
        }
       }
        else if(cases.size() > 0 && cases.get(caseNum).getDialogueLength() > 0)
       {
         paperScreen();
       }
       }

  public static boolean getCaseOpen()
  {
    return caseOpen;
  }

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
      if(nextScene) {
        repaint();
      } else if((pos < 10 && cases.size() > 0) || (pos < 2 && cases.size() == 0) || (pos < 6 && nextScene)) {
          pos++;
          repaint ();
        }
      else
        {
          if (!caseOpen && cases.size() > 0)
          {
            
            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
            {
                  if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (sprite.getXPos() >= 50 && !(sprite.getXPos() == 624 && sprite.getYPos() < 118)) {
                        sprite.setXPos(-2);
                    }
                    sprite.setDir('l');
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (sprite.getXPos() <= 756 && !(sprite.getXPos() == 180 && sprite.getYPos() < 118)) {
                        sprite.setXPos(2);
                    }
                    sprite.setDir('r');
                } else if (e.getKeyCode() == KeyEvent.VK_UP)
                {
                    if (sprite.getYPos() >= 4 && !(sprite.getXPos() > 180 && sprite.getXPos() < 624 && sprite.getYPos() <= 119)) {
                        sprite.setYPos(-2);
                    }
                    sprite.setDir('b');
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if(sprite.getYPos() <= 454) {
                        sprite.setYPos(2);
                    }

                    sprite.setDir('f');
                }
              repaint();
            }
          }
        }
    }
    
      /**
    * Toggles stage 3 case dialogue when any key is typed.
    * This is used instead of keyPressed() to avoid accidental key presses from attempts to move the sprite
    * @param e     Typing a key
    */
    @Override
    public void keyTyped(KeyEvent e) {
        if(caseNum < cases.size() && cases.get(caseNum).getDialogueLength() == 0)
        {
          caseOpen = false;
          cases.remove(caseNum);
          if(cases.size() == 0) {
            pos = 0;
          }
          sprite.resetImgIndex();
          repaint();
        } else if(caseNum < cases.size() && cases.get(caseNum).getDialogueLength() > 1) {
          repaint();
        }   
      }

      /**
    * Key being released
    (method not used but is necessary to implement 
    KeyListener)
    * @param e     Releasing a key
    */
    public void keyReleased(KeyEvent e) {
    // sprite.resetImgIndex();
     //repaint();
    }
  
    @Override
    public void mousePressed(MouseEvent e) {
      if((e.getX() >= 35 && e.getX() <= 473 && e.getY() >= 372 && e.getY() <= 473) || (e.getX() >= 35 && e.getX() <= 473 && e.getY() >= 506 && e.getY() <= 607) || (e.getX() >= 527 && e.getX() <= 965 && e.getY() >= 372 && e.getY() <= 473) || (e.getX() >= 527 && e.getX() <= 965 && e.getY() >= 506 && e.getY() <= 607))
        {
        if(caseOpen == true && cases.get(caseNum).getDialogueLength() == 1) {
        if(cases.get(caseNum).isCorrect(e)) {
          s3Score++;
          game.increasePlayerScore (1);
        }
        cases.get(caseNum).finished();
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
  
    private void paperScreen ()
    {
          Game.graphics.drawImage(ImageReader.reader("res/Name_Screen_Background_1.png"), 0, 0, null);
          if (cases.get(caseNum).getDialogueLength() > 2) 
             Game.graphics.drawImage(dialogueBack, 40, 50, null);
          Game.graphics.drawImage(cases.get(caseNum).getCaseDialogue(), 0, 0, null);
    }
    
    public void setCaseOpen(boolean open)
    {
    caseOpen = open;
    }
    
    private class Case
    {
    Image caseImg;
    Image optionsImg;
    Image answerImg;
    int answer;
    boolean open;
    boolean finished;
    
    int[] imgCoords;   
    ArrayList<Image> caseDialogue;
    
    private Case (String cImg, String optImg, String ansImg, String diaDir, int ans)
    {
    caseImg = ImageReader.reader (cImg);
    optionsImg = ImageReader.reader (optImg);
    answerImg = ImageReader.reader (ansImg);
    caseDialogue = new ArrayList<Image>();
    finished = false;
    answer = ans;

    for (int a = 1; a < 6; a++)
      {
        String path = diaDir + diaDir.substring (24, 26);
        path += "_" + a + ".png";
        File tempFile = new File(path);
        if(tempFile.exists())
        {
          caseDialogue.add(ImageReader.reader(path));
        }
        else
          break;
      }
      caseDialogue.add(optionsImg);
      caseDialogue.add(answerImg);
      
    imgCoords = new int[4];
    }
    
    public void setAnswer (int a)
    {
    answer = a;
    }
    
    public void showCase ()
    {
    Game.graphics.drawImage (caseImg, 0, 0, null);
    }

    public int getDialogueLength()
      {
        return caseDialogue.size();
      }

    public Image getCaseDialogue ()
    {
        return (caseDialogue.remove(0));
    }

    public void finished() {
      finished = true;
    }
      
    public boolean getFinished() {
      return finished;
    }
      
    public void showOptions()
    {
    Game.graphics.drawImage (optionsImg, 0, 0, null);
    }
      
    public boolean isCorrect(MouseEvent e) {
      if(e.getX() >= 35 && e.getX() <= 473 && e.getY() >= 372 && e.getY() <= 473) {
        if(answer == 1) {
           return true;
        } else {
          return false;
        }
      } else if(e.getX() >= 35 && e.getX() <= 473 && e.getY() >= 506 && e.getY() <= 607) {
        if(answer == 2) {
           return true;
        } else {
          return false;
        }
      } else if(e.getX() >= 527 && e.getX() <= 965 && e.getY() >= 372 && e.getY() <= 473) {
        if(answer == 3) {
           return true;
        } else {
          return false;
        }
      } else {
        if(answer == 4) {
           return true;
        } else {
          return false;
        }
      }
    }
      
    public void setImgCoords(int xMin, int xMax, int yMin, int yMax)
    {
    imgCoords [0] = xMin;
    imgCoords [1] = xMax;
    imgCoords [2] = yMin;
    imgCoords [3] = yMax;
    }
    
    public boolean isTouched(int spriteX, int spriteY)
    {
    if (spriteX >= imgCoords[0] && spriteX <= imgCoords[1])
    {
             if (spriteY >= imgCoords [2] && spriteY <= imgCoords [3])
            return true;
    }     
    return false;
    }
    
    public Image getCaseImg()
    {
    return caseImg;
    }
    
    }
    
    private class Sprite
    {
   private int xPos;
   private int yPos;
   private int imgIndex;
   private char dir;

    private Sprite ()
    {
    xPos = 450;
    yPos = 150;
    imgIndex = 1;
    dir = 'f';
    }
    
    public int getXPos()
    {
    return xPos;
    }
    
    public int getYPos()
    {
    return yPos;
    }
    
    public void setXPos(int num)
    {
    xPos += num;
    }
    
    public void setYPos (int num)
    {
    yPos += num;
    }

    public void resetPos() {
        xPos = 450;
        yPos = 150;
    }
    
    public Image getSprite()
    {
    String directory = "res/stage3/sprite/" + dir + imgIndex + ".png";
    Image i = ImageReader.reader(directory);
    return i;
    }
    
    public void incrementImgIndex()
    {
    imgIndex++;
      if (imgIndex > 3)
        imgIndex = 1;
    }
    
    public void resetImgIndex()
    {
    imgIndex = 1;
    }
    
    public void setDir (char s)
    {
    dir = s;
    }
    }

      private class DrawSprite implements Runnable
      {
        Graphics2D graphics;
        Sprite sprite;
        
        public DrawSprite(Graphics2D g, Sprite s)
        {
          graphics = g;
          sprite = s;
        }

        public void run()
        {
          if (!Stage3.getCaseOpen()) {
            try
            {
              graphics.drawImage (sprite.getSprite(), sprite.getXPos(), sprite.getYPos(), null);
              sprite.incrementImgIndex();
              Thread.sleep (110);
            }
            catch(Exception e)
            {

            }
            }
          }
        }
}