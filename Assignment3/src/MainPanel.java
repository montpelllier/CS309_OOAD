import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainPanel extends JPanel implements KeyListener, Subject<Ball> {
    private List<Ball> paintingBallList = new ArrayList<>();
    private List<Ball> observers = new ArrayList<>();

    public void registerObserver(Ball ball){
        observers.add(ball);
        //this.whiteBall.registerObserver;
    }

    @Override
    public void removeObserver(Ball ball) {
        observers.remove(ball);
    }

    @Override
    public void notifyObservers(char keyChar) {

        for (Ball ball:observers) {
            //System.out.println("ddd");
            if (ball != whiteBall) {
                ball.update(keyChar);
            }else if (gameStatus == GameStatus.START) {
                ball.update(keyChar);
                //System.out.println(keyChar);
            }

        }
    }

    @Override
    public void notifyObservers() {

    }

    enum GameStatus {PREPARING, START, STOP}

    private GameStatus gameStatus;
    private int score;
    private WhiteBall whiteBall;
    Random random = new Random();
    Timer t;

    public MainPanel() {
        super();
        setLayout(null);
        setSize(590, 590);
        setFocusable(true);
        this.addKeyListener(this);
        t = new Timer(50, e -> moveBalls());
        restartGame();
    }


    public void startGame() {
        this.gameStatus = GameStatus.START;
        this.whiteBall.setVisible(true);
        this.paintingBallList.forEach(b -> b.setVisible(true));
    }

    public void stopGame() {
        this.observers.clear();
        this.observers.add(this.whiteBall);
        this.whiteBall.clearObserver();
        this.gameStatus = GameStatus.STOP;
        this.t.stop();
        paintingBallList.forEach(b -> {
            if (b.isVisible()) {
                if (b.getColor() == Color.RED) {
                    scoreIncrement(80);
                } else if (b.getColor() == Color.BLUE) {
                    scoreIncrement(-80);
                }
            }
        });
        repaint();
    }

    public void restartGame() {
        //setWhiteBall(new WhiteBall(0,0,200));
        this.gameStatus = GameStatus.PREPARING;
        if (paintingBallList.size() > 0) {
            paintingBallList.forEach(this::remove);
        }

        this.paintingBallList = new ArrayList<>();
        Ball.setCount(0);
        this.score = 100;
        if (this.whiteBall != null)
            this.whiteBall.setVisible(false);

        this.t.start();
        repaint();
    }

    public void setWhiteBall(WhiteBall whiteBall) {
        this.whiteBall = whiteBall;
        this.whiteBall.setVisible(false);
        add(whiteBall);
        observers.add(whiteBall);
    }

    public void moveBalls() {

        if (this.gameStatus == GameStatus.START) {
            score--;
            whiteBall.move();
            //System.out.println('a');

            paintingBallList.forEach(b -> {
                b.setVisible(b.isIntersect(whiteBall));
            });
        }
        paintingBallList.forEach(Ball::move);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.setColor(Color.BLACK);
        g.drawString("Score: " + score, 20, 40);

        if (gameStatus == GameStatus.START) {
            this.setBackground(Color.WHITE);
        }

        if (gameStatus == GameStatus.STOP) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 45));
            g.drawString("Game Over!", 200, 200);
            g.setFont(new Font("", Font.BOLD, 40));
            g.drawString("Your score is " + score, 190, 280);
        }
    }

    public void scoreIncrement(int increment) {
        this.score += increment;
    }

    public void addBallToPanel(Ball ball) {
        paintingBallList.add(ball);
        observers.add(ball);
        this.whiteBall.registerObserver(ball);
        this.add(ball);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        char keyChar = e.getKeyChar();
        System.out.println("Press: " + keyChar);
        notifyObservers(keyChar);
        //if (gameStatus == )
        //System.out.println(gameStatus);
        //print();

        //notifyObservers();
//        paintingBallList.stream().filter(b -> b.getColor() == Color.RED).forEach(
//                redBall -> {
//                    switch (keyChar) {
//                        case 'a':
//                            redBall.setXSpeed(-random.nextInt(3) - 1);
//                            break;
//                        case 'd':
//                            redBall.setXSpeed(random.nextInt(3) + 1);
//                            break;
//                        case 'w':
//                            redBall.setYSpeed(-random.nextInt(3) - 1);
//                            break;
//                        case 's':
//                            redBall.setYSpeed(random.nextInt(3) + 1);
//                    }
//                }
//        );
//        paintingBallList.stream().
//                filter(b -> b.getColor() == Color.BLUE).
//                forEach(
//                        blueBall ->
//                        {
//                            blueBall.setXSpeed(-1 * blueBall.getXSpeed());
//                            blueBall.setYSpeed(-1 * blueBall.getYSpeed());
//                        }
//                );
//
//        if (gameStatus == GameStatus.START) {
//            switch (keyChar) {
//                case 'a':
//                    whiteBall.setXSpeed(-8);
//                    break;
//                case 'd':
//                    whiteBall.setXSpeed(8);
//                    break;
//                case 'w':
//                    whiteBall.setYSpeed(-8);
//                    break;
//                case 's':
//                    whiteBall.setYSpeed(8);
//                    break;
//            }
//        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}
