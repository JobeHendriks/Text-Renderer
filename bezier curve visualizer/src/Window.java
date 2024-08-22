import javax.swing.*;
import java.awt.*;

public class Window extends JFrame implements Runnable{

    private Graphics2D graphics2D;
    private Curve curve3,curve4;
    private static final int windowSize = 1500;
    double[] point1 = new double[]{250,1250};
    double[] point2 = new double[]{750,250};
    double[] point3 = new double[]{1250,1250};
    double[] point4 = new double[]{250,750};
    double[] point5 = new double[]{1250,750};

    public Window() throws Exception {
        //initialises window
        this.setSize(windowSize,windowSize);
        this.setTitle("Window");
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //initialises graphics
        graphics2D = (Graphics2D)this.getGraphics();

        //initialises curve points
        curve3 = new Curve(point1,point2,point3);
        curve4 = new Curve(point1,point4,point5,point3);
    }

    public void update(){
        Image doubleBufferImage = createImage(getWidth(),getHeight());
        Graphics doubleBufferGraphics = doubleBufferImage.getGraphics();
        this.draw(doubleBufferGraphics);
        graphics2D.drawImage(doubleBufferImage,0,0, this);
    }

    public void draw(Graphics graphics){
        //window
        Graphics2D graphics2D = (Graphics2D)graphics;
        graphics2D.setColor(Color.black);
        graphics2D.fillRect(0,0,windowSize,windowSize);
        curve3.draw(graphics2D, Color.white);
        curve4.draw(graphics2D,Color.cyan);
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        while(true){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                update();
                delta--;
            }
        }
    }
}
