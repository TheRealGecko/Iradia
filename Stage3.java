import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;

public class Stage3 extends JPanel
{
    Game game;
    Sprite sprite;
    Thread dSprite;
    Image background;
    
    Image[] introDialogue;
    int pos;
    int paperPos;
    ArrayList<Case> cases;
    static boolean caseOpen;

    public Stage3(Game g)
    {
    game = g;
    sprite = new Sprite ();
    pos = 0;
    paperPos = 0;
    background = ImageReader.reader ("res/stage3/background.png");
    introDialogue = ImageReader.storeDir("res/stage3/introText/");
    
    cases = new ArrayList<Case>();
    
    for (int a = 1; a < 6; a++)
    {
    String caseImgAddress = "res/stage3/files/" + a + ".png";
    String optionImgAddress = "res/stage3/options/c" + a + ".png";
    String dialogueDir = "res/stage3/caseDialogue/c" + a + "/";
    cases.add (new Case (caseImgAddress, optionImgAddress, dialogueDir, a));
    }
    }
    
    /**
      * Displays the graphics necessary for stage 3.
      * @param g     Used to draw graphics.
      */
    @Override
    public void paintComponent(Graphics g) {
        Game.graphics = (Graphics2D) g;
        this.requestFocus();
        Game.graphics.drawImage (background, 0, 0, null); 
        if(pos >= 7) {
            for(int i = 0; i < cases.size(); i++) {
                Game.graphics.drawImage(cases.get(i).getCaseImg(), 0, 0, null);
        }
        
        if (pos <= 10)
         // Game.graphics.drawImage(introDialogue[pos+1], 0, 0, null);
          System.out.println ("this is fine");
        else
        {
        dSprite = new Thread(new DrawSprite(Game.graphics, sprite));
        dSprite.run();
        }
     
        if (!caseOpen)
        {
            for (int a = 0; a < cases.size(); a++)
            {
               if (cases.get(a).isTouched (sprite.getXPos(), sprite.getYPos()))
               paperScreen (a+1);
            }
        }
    }
    }
    
    private void paperScreen (int caseNum)
    {
      Game.graphics.drawImage (ImageReader.reader("res/Name_Screen_Background_1.png"), 0, 0, null);
      Game.graphics.drawImage (cases.get(caseNum-1).getCaseDialogue(paperPos), 0, 0, null);
    }
    
    public void setCaseOpen(boolean open)
    {
    caseOpen = open;
    }
    
    private class Case
    {
    Image caseImg;
    Image optionsImg;
    int answer;
    boolean open;
    
    int[] imgCoords;   
    Image[] caseDialogue;
    
    
    private Case (String cImg, String optImg, String diaDir, int num)
    {
    caseImg = ImageReader.reader (cImg);
    optionsImg = ImageReader.reader (optImg);
    //caseDialogue = ImageReader.storeDir(diaDir);
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
    
    public Image getCaseDialogue (int i)
    {
    return caseDialogue [i];
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
       if (spriteY >= imgCoords [2] && spriteY <= imgCoords [3])
          return true;
          
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
          try
            {
              graphics.drawImage (sprite.getSprite(), sprite.getXPos(), sprite.getYPos(), null);
              sprite.incrementImgIndex();
              Thread.sleep (200);
            }
            catch(Exception e)
            {
              
            }
        }
      }
}