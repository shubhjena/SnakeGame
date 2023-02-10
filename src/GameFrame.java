import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
        this.add(new GamePanel());
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);

        ImageIcon image = new ImageIcon("D:\\Programming Bootcamp\\IdeaProjects\\SnakeGame\\src\\snake.png");
        this.setIconImage(image.getImage());

    }


}
