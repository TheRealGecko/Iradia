import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Stage3 extends JPanel implements KeyListener, MouseListener
  {

    Game game;
    Sprite sprite;
    Thread dSprite;
    Image background;
    
    public Stage3 (Game g)
    {
        game = g;
        sprite = new Sprite ();
        background = ImageReader.reader ("res/Name_Screen_Background_1.png");
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
        Game.graphics.drawImage (background, 0, 0, null); 
        //Game.graphics.drawImage (sprite.getSprite(), sprite.getXPos(), sprite.getYPos(), null);
        dSprite = new Thread (new DrawSprite (Game.graphics));
        dSprite.run ();
      
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
      if (e.getKeyCode()== KeyEvent.VK_LEFT  || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
      {
             if (e.getKeyCode()== KeyEvent.VK_LEFT )
      {
         sprite.setXPos (-1);
         sprite.setDir ('l');
      }
      else if (e.getKeyCode () == KeyEvent.VK_RIGHT)
      {
         sprite.setXPos (1);
         sprite.setDir ('r');
      }
      else if (e.getKeyCode () == KeyEvent.VK_UP) // Add code for not going up once floor y ends + when in front of table
      {
         sprite.setYPos (-1);
         sprite.setDir ('b');
      }
      else if (e.getKeyCode () == KeyEvent.VK_DOWN)
      {
         sprite.setYPos (1);
         sprite.setDir ('f');
      }
      //sprite.incrementImgIndex();
      repaint ();
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
              Thread.sleep (110);
            }
            catch(Exception e)
            {
              
            }
        }
      }
  }