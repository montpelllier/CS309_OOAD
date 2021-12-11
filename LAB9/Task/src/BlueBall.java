import java.awt.*;

public class BlueBall extends Ball {


    public BlueBall(int xSpeed, int ySpeed, int ballSize) {
        super(Color.BLUE, xSpeed, ySpeed, ballSize);
    }

    @Override
    public void update(char keyChar) {
        this.setXSpeed(-1 * this.getXSpeed());
        this.setYSpeed(-1 * this.getYSpeed());
    }

    @Override
    protected void update(Ball whiteBall) {
        setXSpeed(-this.getXSpeed());
        setYSpeed(-this.getYSpeed());
    }


}
