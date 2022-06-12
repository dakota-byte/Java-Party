import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Font;

public class LearningGraphics extends JComponent implements KeyListener, MouseListener, MouseMotionListener {
    // instance variables
    private int WIDTH;
    private int HEIGHT;
    private int rX, rY, rW, rH;
    private int cX, cY, diam, cVx, cVy;

    // Default Constructor
    public LearningGraphics() {
        // initializing instance variables
        WIDTH = 1000;
        HEIGHT = 750;

        // rectangle
        rX = 300;
        rY = 300;
        rW = 50;
        rH = 100;

        // circle
        cX = 500;
        cY = 300;
        diam = 30;
        cVx = 5;
        cVy = 5;

        // Setting up the GUI
        JFrame gui = new JFrame(); // This makes the gui box
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Makes sure program can close
        gui.setTitle("Learning Graphics"); // This is the title of the game, you can change it
        gui.setPreferredSize(new Dimension(WIDTH + 5, HEIGHT + 30)); // Setting the size for gui
        gui.setResizable(false); // Makes it so the gui cant be resized
        gui.getContentPane().add(this); // Adding this class to the gui

        /*
         * If after you finish everything, you can declare your buttons or other things
         * at this spot. AFTER gui.getContentPane().add(this) and BEFORE gui.pack();
         */

        gui.pack(); // Packs everything together
        gui.setLocationRelativeTo(null); // Makes so the gui opens in the center of screen
        gui.setVisible(true); // Makes the gui visible
        gui.addKeyListener(this);// stating that this object will listen to the keyboard
        gui.addMouseListener(this); // stating that this object will listen to the Mouse
        gui.addMouseMotionListener(this); // stating that this object will acknowledge when the Mouse moves

    }

    // This method will acknowledge user input
    public void keyPressed(KeyEvent e) {
        // getting the key pressed
        int keyPressed = e.getKeyCode();

        // moving the rectangle
        if (keyPressed == 38) {
            rY = rY - 10;
        }
        if (keyPressed == 40) {
            rY = rY + 10;
        }
        if (keyPressed == 37) {
            rX = rX - 10;
        }
        if (keyPressed == 39) {
            rX = rX + 10;
        }
    }

    // All your UI drawing goes in here
    public void paintComponent(Graphics g) {
        // setting color to blue.
        g.setColor(Color.WHITE);

        // Drawing a Blue Rectangle to be the background
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Drawing Hello World!! at the center of the GUI
        Font s = new Font("Times New Roman", Font.PLAIN, 20);
        g.setFont(s);

        g.setColor(Color.BLACK);
        g.drawString("Hello World", 450, 375);

        // Drawing the user-controlled rectangle

        g.setColor(Color.RED);
        g.fillRect(rX, rY, rW, rH);

        // Drawing the autonomous circle
        g.setColor(Color.MAGENTA);
        g.fillOval(cX, cY, diam, diam);

    }

    public void loop() {
        // making the autonomous circle move
        cX = cX + cVx;
        cY = cVy + cY;

        // handling when the circle collides with the edges
        int nextX = cX + cVx;
        int nextY = cY + cVy;

        if (nextY + diam > HEIGHT) { // checking the bottom of the circle with bottom of the screen
            cVy = cVy * -1;
        } else if (nextX + diam > WIDTH) {
            cVx = cVx * -1;
        }

        else if ((nextY) < 0) {
            cVy = cVy * -1;
        } else if ((nextX) < 0) {
            cVx = cVx * -1;
        }

        // handling the collision of the circle with the rectangle
        if (detectCollision()) {
            cVy = cVy * -1;
            cVx = cVx * -1;
        }

        // Do not write below this
        repaint();
    }

    // These methods are required by the compiler.
    // You might write code in these methods depending on your goal.
    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void start(final int ticks) {
        Thread gameThread = new Thread() {
            public void run() {
                while (true) {
                    loop();

                    try {

                        Thread.sleep(1000 / ticks);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        gameThread.start();
    }

    public double distanceCalc(int x1, int x2, int y1, int y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public boolean detectCollision() {
        boolean output = false;

        int radius, centerX, centerY;
        radius = diam / 2;

        int nextX, nextY;
        nextX = cX + cVx;
        nextY = cY + cVy;

        centerX = (2 * nextX + diam) / 2;
        centerY = (2 * nextY + diam) / 2;

        for (int r = rX; r < rX + rW; r++) {
            for (int c = rY; c < rY + rH; c++) {
                if (distanceCalc(r, centerX, c, centerY) < radius) {
                    output = true;
                }
            }
        }

        return output;
    }

    public static void main(String[] args) {
        LearningGraphics g = new LearningGraphics();
        g.start(60);
    }
}
