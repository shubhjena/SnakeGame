import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    //Game Parameters
    static final int SCREEN_WIDTH = 600, SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int[] x = new int[GAME_UNITS], y = new int[GAME_UNITS];
    int bodyParts = 6, applesEaten, appleX, appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    ImageIcon background = new ImageIcon("D:\\Programming Bootcamp\\IdeaProjects\\SnakeGame\\src\\background.png");
    ImageIcon apple = new ImageIcon("D:\\Programming Bootcamp\\IdeaProjects\\SnakeGame\\src\\apple.png");
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw (Graphics g){
        g.drawImage(background.getImage(),0,0,null);
        if (running) {
//            g.setColor(Color.darkGray);
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
            }
            for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
                g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
            }
            g.setColor(Color.RED);
//            g.fillRect(appleX,appleY,UNIT_SIZE,UNIT_SIZE);
            g.drawImage(apple.getImage(),appleX,appleY,null);
            for (int i = 0; i < bodyParts; i++) {
                if (i==0){
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
                }
                else {
                    g.setColor(new Color(45, 180, 0));
                    //colourful snake
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Calibri",Font.PLAIN,25));
            FontMetrics fontMetrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten,(SCREEN_WIDTH-fontMetrics.stringWidth("Score:"+applesEaten))/2, g.getFont().getSize());
        }
        else {
            gameOver(g);
        }
    }
    public void newApple(){
    appleX = random.nextInt((SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
    appleY = random.nextInt((SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }
    public void move(){
        for (int i = bodyParts ; i >0 ; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction){
            case 'U': y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D': y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L': x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R': x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    public void checkApple(){
        if ((x[0]==appleX) && (y[0]==appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollision(){
        // checks for head collision with body
        for (int i = bodyParts; i > 0 ; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                break;
            }
        }
        //checks if the head collides with edge
        if(x[0]< 0 || x[0]>SCREEN_WIDTH ||y[0] < 0 ||y[0] > SCREEN_HEIGHT)
            running = false;

        if (!running) timer.stop();
    }

    public void gameOver(Graphics g){
        //Score
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Ariel",Font.PLAIN,25));
        FontMetrics fontMetrics = getFontMetrics(g.getFont());
        g.drawString("SCORE: "+applesEaten,(SCREEN_WIDTH-fontMetrics.stringWidth("Score:"+applesEaten))/2, g.getFont().getSize()+UNIT_SIZE);
        //game over
        g.setColor(Color.RED);
        g.setFont(new Font("jokerman",Font.BOLD,75));
        fontMetrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER",(SCREEN_WIDTH-fontMetrics.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if ( direction!= 'R') direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if ( direction!= 'L') direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if ( direction!= 'D') direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if ( direction!= 'U') direction = 'D';
                    break;
            }
        }
    }
}
