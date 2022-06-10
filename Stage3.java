import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;

public class Stage3 extends JPanel implements KeyListener, MouseListener
  {

    Game game;
    Sprite sprite;
    Thread dSprite;
    Image background;
    boolean pause;
    int pos;

    Image[] introDialogue;

    ArrayList<Image> files;
    ArrayList<Image> options;
    ArrayList<Integer> answers;
    Image[] caseDialogue;

    public Stage3 (Game g)
    {
        game = g;
        sprite = new Sprite ();
        background = ImageReader.reader ("res/stage3/background.png");

        introDialogue = ImageReader.storeDir("res/stage3/introText/");

        pause = true;
        pos = 0;

        files = new ArrayList<Image>();
        options = new ArrayList<Image>();
        answers = new ArrayList<Integer>();
        for(int i = 1; i < 7; i++) {
            files.add(ImageReader.reader("res/stage3/files/" + i + ".png"));
            options.add(ImageReader.reader("res/stage3/files/" + i + ".png"));
        }

        answers.add(1);
        answers.add(3);
        answers.add(3);
        answers.add(2);
        answers.add(4);
        answers.add(2);

        this.setFocusable(true); // Allows the class to receive user input
        this.addKeyListener(this);
      addMouseListener (this);
    }

    public void checkPos() {
        if(sprite.getXPos() >= 650 && sprite.getYPos() <= 18) {
            System.out.println("Approaching case #1!");
        } else if(sprite.getXPos() >= 650 && sprite.getYPos() >= 188 && sprite.getYPos() <= 308) {
            System.out.println("Approaching case #2!");
        } else if(sprite.getXPos() >= 422 && sprite.getXPos() <= 598 && sprite.getYPos() >= 358) {
            System.out.println("Approaching case #3!");
        } else if(sprite.getXPos() >= 142 && sprite.getYPos() <= 130) {
            System.out.println("Approaching case #4!");
        } else if(sprite.getXPos() <= 180 && sprite.getYPos() <= 64) {
            System.out.println("Approaching case #5!");
        } else if(sprite.getXPos() <= 60 && sprite.getYPos() >= 370) {
            System.out.println("Approaching case #6!");
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
        //Game.graphics.drawImage (sprite.getSprite(), sprite.getXPos(), sprite.getYPos(), null);
        if(pos >= 7) {
            for(int i = 0; i < files.size(); i++) {
                Game.graphics.drawImage(files.get(i), 0, 0, null);
            }
        }
        if(!pause) {
            dSprite = new Thread(new DrawSprite(Game.graphics));
            dSprite.run();
        } else if(pos < 10) {
            Game.graphics.drawImage(introDialogue[pos], 0, 0, null);
        }

      if (sprite.isWithin (0, 98, 0, 52))
        paperScreen (1);
      else if (sprite.isWithin (0, 48, 420, 650))
        paperScreen (2);
      else if (sprite.isWithin (426, 590, 368, 650))
        paperScreen (3);
      else if (sprite.isWithin (638, 1000, 208, 269))
        paperScreen (4);
      else if (sprite.isWithin (682, 1000, 0, 28))
        paperScreen (5);
      else if (sprite.isWithin (252, 330, 118, 122))
        paperScreen (6);

    }

    private void paperScreen(int paperNum)
    {
      try
        {
                Thread.sleep (150);
        }
catch(Exception e)
        {
        
        }
       //Game.graphics.drawImage (ImageReader.reader("res/Name_Screen_Background_1.png"), 0, 0, null);
      
      System.out.println ("Paper " + paperNum);

        checkPos();
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
    * Moves the sprite when arrow keys are pressed
    * @param e     Pressing a key
    */
    @Override
    public void keyPressed(KeyEvent e) {
        if(!pause) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
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
                } else if (e.getKeyCode() == KeyEvent.VK_UP) // Add code for not going up once floor y ends + when in front of table
                {
                    if (sprite.getYPos() >= 4 && !(sprite.getXPos() > 180 && sprite.getXPos() < 624 && sprite.getYPos() <= 119)) {
                        sprite.setYPos(-2);
                    }
                    sprite.setDir('b');
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    //if(!(sprite.getXPos() >= 218 && sprite.getXPos() <= 654 && sprite.getYPos() <= 119)) {
                    if(sprite.getYPos() <= 454) {
                        sprite.setYPos(2);
                    }
                    //}
                    sprite.setDir('f');
                }
                //sprite.incrementImgIndex();
                repaint();
            }
        } else {
            pos++;
            if(pos == 10) {
                pause = false;
            }
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
    * Sets sprite to the standing sprite
    * @param e     Releasing a key
    */
    @Override
    public void keyReleased(KeyEvent e) {
      sprite.resetImgIndex();
      repaint();
    }

     /**
     * Desc.
     * @param e     A click while stage 2 is
     *              onscreen
     */
    @Override
    public void mousePressed(MouseEvent e) {
System.out.println (sprite.getXPos());
      System.out.println (sprite.getYPos());
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

  private class Sprite
  {
   private int xPos;
   private int yPos;
   private int imgIndex;
   private char dir;

    public Sprite ()
    {
    xPos = 300;
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
    String directory = "res/stage3/sprite/" + dir + imgIndex + ".png"; // Make correct directory
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

    public boolean isWithin (int minX, int maxX, int minY, int maxY)
    {
      if (xPos >= minX && xPos <= maxX && yPos >= minY && yPos < maxY)
        return true;
      return false;
    }

   }
    
    private class DrawSprite implements Runnable
      {
        Graphics2D graphics;
        public DrawSprite(Graphics2D g)
        {
          graphics = g;
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
