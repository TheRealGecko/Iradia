import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;

public class Stage3
{
    Game game;
    Sprite sprite;
    Thread dSprite;
    Image background;
    
    Image[] introDialogue;
    int pos;
    ArrayList<Case> cases;
    static boolean caseOpen;

    public stage3(Game g)
    {
    Game = g;
    sprite = new Sprite ();
    pos = 0;
    background = ImageReader.reader ("res/stage3/background.png");
    introDialogue = ImageReader.storeDir("res/stage3/introText/");
    
    cases = new ArrayList<Case>();
    
    for (int a = 0; a < 5; a++)
    {
    String caseImgAddress = "res/stage3/files" + (a+1);
    String optionImgAddress = "res/stage3/options/c" + (a+1);
    String dialogueDir = "res/stage3/caseDialogue/c" + (a+1);
    String caseName = "case" + (a+1);
    Case caseName = new Case (caseImgAddress, optionImgAddress, dialogueDir);
    cases.add (caseName);
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
           Game.graphics.drawImage(introDialogue[pos], 0, 0, null);
        else
        {
        dSprite = new Thread(new DrawSprite(Game.graphics));
        dSprite.run();
        }
     
        if (!caseOpen)
        {
            for (Case c: cases)
            {
               if (c.isTouched (sprite.getX(), sprite.getY()))
               paperScreen ();
            }
        }
    }
    }
    
    private void paperScreen ()
    {
    
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
    
    
    private Case (String cImg, String optImg, diaDir)
    {
    caseImg = ImageReader.reader (cImg);
    optionsImg = ImageReader.reader (optImg);
    caseDialogue = ImageReader.storeDir(diaDir);
    answer = 0;   
    imgCoords = new int[4];
    
    
    public void setAnswer (int a)
    {
    answer = a;
    }
    
    public void showCase ()
    {
    Game.graphics.drawImage (caseImg);
    }
    
    public void showOptions()
    {
    Game.graphics.drawImage (optionsImg);
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
    if (spriteX >= xMin && spriteX <= xMax)
       if (spriteY >= yMin && spriteY <= yMax)
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
    
    }
}
