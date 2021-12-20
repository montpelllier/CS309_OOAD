import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WhiteBall extends Ball implements Subject<Ball>{
    private List<Ball> obervers = new ArrayList<>();

    public WhiteBall(int xSpeed, int ySpeed, int ballSize) {
        super(Color.WHITE, xSpeed, ySpeed, ballSize);
    }

    @Override
    public void update(char keyChar) {
//        if (gameStatus == GameStatus.START) {
        switch (keyChar) {
            case 'a' -> this.setXSpeed(-8);
            case 'd' -> this.setXSpeed(8);
            case 'w' -> this.setYSpeed(-8);
            case 's' -> this.setYSpeed(8);
        }
        //System.out.println("changed");
    //print("changed");
    }

    @Override
    protected void update(Ball whiteBall) {
        //obervers.clear();
    }


    @Override
    public void registerObserver(Ball ball) {
        obervers.add(ball);
    }

    @Override
    public void removeObserver(Ball ball) {
        obervers.remove(ball);
    }

    @Override
    public void notifyObservers(char keyChar) {

    }

    @Override
    public void notifyObservers() {
        obervers.forEach(b->{
            b.update(this);
            //System.out.println(b.getColor());
        });
    }

    @Override
    public void move(){
        this.notifyObservers();
        super.move();

        //System.out.println("aaa");
//        obervers.forEach(b->{
//            b.update(this);
//        });
    }

    public void clearObserver(){
        obervers.clear();
    }
}
