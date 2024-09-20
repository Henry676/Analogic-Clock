package p1.ventanas.tickingclock;

import java.awt.*;
import javax.swing.*;
import java.util.Calendar;
import java.awt.geom.Ellipse2D;


public class Clock extends JFrame implements Runnable {
    private Image background,buffer,backImage,backClock;
    private int hour = 0, min = 0, sec = 0;
    private int xCenter = 0,yCenter = 0;
    private Thread t; 
    private Noises tic = new Noises(); 
    private Marks mark = new Marks();
    private byte begin = 0;

    public Clock() {
        super("Ticking Clock");
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        backImage = new ImageIcon(getClass().getResource("windowBackgr.jpg")).getImage();
        backClock = new ImageIcon(getClass().getResource("clockBackgr.jpg")).getImage();
        this.setIconImage(new ImageIcon(getClass().getResource("appIcon.png")).getImage());//ICONO

        xCenter = getWidth() / 2;
        yCenter = getHeight() / 2;
        t = new Thread(this);
        t.start();

        this.setVisible(true);
    }

    public void paint(Graphics g) {
        if (background == null) {
            background = createImage(getWidth(), getHeight());
            Graphics2D gfondo = (Graphics2D) background.getGraphics();
            gfondo.drawImage(backImage, 0, 0, getWidth(), getHeight(), this);

            int relojDiametroExterior = 516;//Front of the clock

            gfondo.setColor(Color.WHITE);
            gfondo.fillOval((getWidth() - 525) / 2, (getHeight() - 525) / 2, 525, 525);

            //Setup clip in order to prepare background of the clock
            Ellipse2D.Double clipArea = new Ellipse2D.Double(xCenter - relojDiametroExterior / 2, yCenter - relojDiametroExterior / 2, 
            relojDiametroExterior, relojDiametroExterior);
            gfondo.setClip(clipArea);
            gfondo.drawImage(backClock, (getWidth() - 525) / 2, (getHeight() - 525) / 2, 525, 525, this);

            //Restore clip area
            gfondo.setClip(null);

            gfondo.setColor(Color.CYAN);
            gfondo.fillOval((getWidth() - 20) / 2, (getHeight() - 20) / 2, 20, 20);

            mark.drawMarks(gfondo, xCenter, yCenter, 255);
        }
        update(g);
    }

    public void update(Graphics g) {
        g.setClip(0,0,getWidth(),getHeight());
        Calendar cal = Calendar.getInstance();

        if(cal.get(Calendar.MINUTE) != min){
            //Regenerate background image
            hour = cal.get(Calendar.HOUR);
            min = cal.get(Calendar.MINUTE);
            //Create static image
            buffer = createImage(getWidth(),getHeight());

            Graphics gbuffer = buffer.getGraphics();
            gbuffer.setClip(0,0,getWidth(),getHeight());
            gbuffer.drawImage(background, 0, 0, this);

            gbuffer.setColor(Color.CYAN);
            drawClockHand(gbuffer, xCenter, yCenter, (hour % 12) * 30 + min * 0.5 - 90, 130);

            gbuffer.setColor(Color.CYAN);
            drawClockHand(gbuffer, xCenter, yCenter, min * 6 - 90, 170);
            if(begin > 0){
                g.drawImage(buffer, 0, 0, this);//Paint main buffer
                //Paint moving entity
                g.setColor(Color.WHITE);
                drawClockHand(g, xCenter, yCenter, sec *6 - 90, 180);
            }
            begin++;
        }
        //Paint main buffer
        g.drawImage(buffer, 0, 0, this);
        sec = cal.get(Calendar.SECOND);
        //Paint moving entity
        g.setColor(Color.WHITE);
        drawClockHand(g, xCenter, yCenter, sec *6 - 90, 180);

    }
    private void drawClockHand(Graphics g, int _xCenter, int _centerY, double degreesAngle, int length) {
        double angleRadians = Math.toRadians(degreesAngle);
        int x2 = _xCenter + (int)(length * Math.cos(angleRadians));
        int y2 = _centerY + (int)(length * Math.sin(angleRadians));
        g.drawLine(_xCenter, _centerY, x2, y2);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
            tic.makeNoise(); 
        }
    }

    public static void main(String[] args) {
        new Clock();
    }
}