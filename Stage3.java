import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.io.*;

public class Stage3 extends JPanel implements KeyListener
{
    Game game;
    Sprite sprite;
    Thread dSprite;
    Image background;
    Image dialogueBack;
    
    Image[] introDialogue;
    int pos;
    int paperPos;
    int caseNum;
    ArrayList<Case> cases;
    static boolean caseOpen;
    boolean isTouched;
    boolean isPressed;
  
    public Stage3(Game g)
    {
    isPressed = false;
    game = g;
    sprite = new Sprite ();
    pos = 0;
    paperPos = 0;
    background = ImageReader.reader ("res/stage3/background.png");
    introDialogue = ImageReader.storeDir("res/stage3/introText/");
    dialogueBack = ImageReader.reader("res/header_base.png"); // Dialogue background image
    
    cases = new ArrayList<Case>();
    
    for (int a = 1; a <= 6; a++)
    {
    String caseImgAddress = "res/stage3/files/" + a + ".png";
    String optionImgAddress = "res/stage3/options/c" + a + ".png";
    String answerImgAddress = "res/stage3/answers/c" + a + "_answers.png";
    String dialogueDir = "res/stage3/caseDialogue/c" + a + "/";
    cases.add (new Case (caseImgAddress, optionImgAddress, answerImgAddress, dialogueDir));
    }

      cases.get(0).setImgCoords(682, 1000, 0, 28);
      cases.get(1).setImgCoords(638, 1000, 208, 269);
      cases.get(2).setImgCoords(426, 590, 368, 650);
      cases.get(3).setImgCoords(252, 330, 118, 122);
      cases.get(4).setImgCoords(0, 98, 0, 52);
      cases.get(5).setImgCoords(0, 48, 420, 650);
     

      this.setFocusable(true); // Allows the class to receive user input
      this.addKeyListener(this);
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
                    System.out.println ("the case is" + caseNum);
                    caseOpen = true;
                    paperScreen();
                    break;
                }
                }
            }
        }
         if (pos < 10)
          Game.graphics.drawImage(introDialogue[pos], 0, 0, null);
        else
        {
        dSprite = new Thread(new DrawSprite(Game.graphics, sprite));
        dSprite.run();
        }
        }
        else
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
        if(pos < 10) {
          pos++;
          repaint ();
        }
      else
        {
          if (!caseOpen)
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

      if (caseOpen)
      {
        if(cases.get(caseNum).getDialogueLength() == 0)
        {
          caseOpen = false;
          cases.remove(caseNum);
        }
      }
      repaint();
    }

      /**
    * Key being released
    (method not used but is necessary to implement 
    KeyListener)
    * @param e     Releasing a key
    */
    public void keyReleased(KeyEvent e) {
     sprite.resetImgIndex();
     repaint();
    }

    private void paperScreen ()
    {
          Game.graphics.drawImage(ImageReader.reader("res/Name_Screen_Background_1.png"), 0, 0, null);
          if (cases.get(caseNum).getDialogueLength() > 1)
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
    
    int[] imgCoords;   
    ArrayList<Image> caseDialogue;
    
    private Case (String cImg, String optImg, String ansImg, String diaDir)
    {
    caseImg = ImageReader.reader (cImg);
    optionsImg = ImageReader.reader (optImg);
    answerImg = ImageReader.reader (ansImg);
    caseDialogue = new ArrayList<Image>();

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
      
    answer = 0;
      
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
      
    public void showOptions()
    {
    Game.graphics.drawImage (optionsImg, 0, 0, null);
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