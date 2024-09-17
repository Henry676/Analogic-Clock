    package p1.ventanas.clock;

    import java.awt.*;
    import javax.swing.*;
    import javax.sound.sampled.*;
    import java.util.Calendar;
    import java.io.File;

    public class Clock extends JFrame implements Runnable {
        private Image background,buffer;
        private int hour = 0, min = 0, sec = 0;
        private Clip ticTac,midNight,newHour,noon;
        private int xCenter = 0,yCenter = 0;
        private Thread t; 
        private Font font; 

        public Clock() {
            super("Ticking Clock");
            setSize(800, 800);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            setLocationRelativeTo(null);
            setBackground(Color.GRAY);
            setIconImage(new ImageIcon(getClass().getResource("icon.png")).getImage()); //ICON
            font = new Font("Times Roman",Font.BOLD,20);
            //Coordenadas del centro del reloj o ventana
            xCenter = getWidth() / 2;
            yCenter = getHeight() / 2;
            setupNoises("/home/hertz676/Documentos/graficas/p1/ventanas/clock/tictac.wav",
                        "/home/hertz676/Documentos/graficas/p1/ventanas/clock/Zelda Majora's mask - Clock tower bell sound.wav",
                        "/home/hertz676/Documentos/graficas/p1/ventanas/clock/newHour.wav",
                        "/home/hertz676/Documentos/graficas/p1/ventanas/clock/noon.wav");

            t = new Thread(this);
            t.start();
            
            setVisible(true);
        }

        public void setupNoises(String _tictac, String _midNight, String _newHour,String _noon) {
            try {
                File file1 = new File(_tictac);
                AudioInputStream noise = AudioSystem.getAudioInputStream(file1);
                ticTac = AudioSystem.getClip();
                ticTac.open(noise);

                File file2 = new File(_midNight);
                AudioInputStream noise2 = AudioSystem.getAudioInputStream(file2);
                midNight = AudioSystem.getClip();
                midNight.open(noise2);

                File file3 = new File(_newHour);
                AudioInputStream noise3 = AudioSystem.getAudioInputStream(file3);
                newHour = AudioSystem.getClip();
                newHour.open(noise3);

                File file4 = new File(_noon);
                AudioInputStream noise4 = AudioSystem.getAudioInputStream(file4);
                noon = AudioSystem.getClip();
                noon.open(noise4);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void makeNoise() {
            Calendar cal = Calendar.getInstance();
            if (ticTac != null) {
                ticTac.setFramePosition(0); //Rewind file
                ticTac.start();
            }
            if (midNight != null && cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0) {
                midNight.start();
            } else {
                midNight.stop();
                midNight.setFramePosition(0);
            }
            if(noon != null && cal.get(Calendar.HOUR_OF_DAY) == 12 && cal.get(Calendar.MINUTE) == 0){
                noon.start();
            }else{
                noon.stop();
                noon.setFramePosition(0);
            }

            if (newHour != null && cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.HOUR) != 0) {
                newHour.start();
            } else {
                newHour.stop();
                newHour.setFramePosition(0);
            }
        }

        public void drawClockMarks(Graphics g, int _xCenter, int _yCenter, int radious) {
            //Draw hour marks (12 marks)
            for (byte i = 0; i < 12; i++) {
                double angulo = Math.toRadians(i * 30); //Every 30 degrees
                int x1 = _xCenter + (int) ((radious - 20) * Math.cos(angulo)); //Beginning point
                int y1 = _yCenter + (int) ((radious - 20) * Math.sin(angulo));
                int x2 = _xCenter + (int) (radious * Math.cos(angulo)); //Final point
                int y2 = _yCenter + (int) (radious * Math.sin(angulo));
                g.setFont(font);
                g.setColor(Color.CYAN);
                g.drawLine(x1, y1, x2, y2);

                if(i == 0) g.drawString("XII", _xCenter - 5, _yCenter - 205);
                if(i == 1) g.drawString("I", _xCenter + 105, _yCenter - 175);
                if(i == 2) g.drawString("II", _xCenter + 175, _yCenter - 100);
                if(i == 3) g.drawString("III", _xCenter + 205, _yCenter);
                if(i == 4) g.drawString("IV", _xCenter + 175, _yCenter + 100);
                if(i == 5) g.drawString("V", _xCenter + 105, _yCenter + 175);
                if(i == 6) g.drawString("VI", _xCenter-10, _yCenter + 205);
                if(i == 7) g.drawString("VII", _xCenter - 115, _yCenter + 175);
                if(i == 8) g.drawString("VIII", _xCenter - 185, _yCenter + 100);
                if(i == 9) g.drawString("IX", _xCenter - 215, _yCenter);
                if(i == 10) g.drawString("X", _xCenter - 185, _yCenter - 100);
                if(i == 11) g.drawString("XI", _xCenter - 115, _yCenter - 175);
            }

            //Draw minutes marks (60 marks)
            for (byte i = 0; i < 60; i++) {
                double angulo = Math.toRadians(i * 6); //Cada 6 grados
                int x1 = _xCenter + (int) ((radious - 10) * Math.cos(angulo)); //Punto de inicio
                int y1 = _xCenter + (int) ((radious - 10) * Math.sin(angulo));
                int x2 = _xCenter + (int) (radious * Math.cos(angulo)); //Punto final
                int y2 = _xCenter + (int) (radious * Math.sin(angulo));
                g.drawLine(x1, y1, x2, y2); 
            }
        }

        public void paint(Graphics g) {
            if (background == null) {
                background = createImage(getWidth(), getHeight());
                Graphics gback = background.getGraphics();
                gback.setClip(0, 0, getWidth(), getHeight());
                gback.setColor(Color.WHITE);
                gback.fillOval((getWidth() - 525) / 2, (getHeight() - 525) / 2, 525, 525);
                gback.setColor(Color.BLACK);
                gback.fillOval((getWidth() - 500) / 2, (getHeight() - 500) / 2, 500, 500);
                gback.setColor(Color.CYAN);
                gback.fillOval((getWidth() - 20) / 2, (getHeight() - 20) / 2, 20, 20);
                drawClockMarks(gback, xCenter, yCenter, 250);
            }
            update(g);
        }

        public void update(Graphics g) {
            g.setClip(0,0,getWidth(),getHeight());
            Calendar cal = Calendar.getInstance();
  
            if(cal.get(Calendar.MINUTE) != min){
                
                //Recreate background image
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
                g.drawImage(buffer, 0, 0, this);
                sec = cal.get(Calendar.SECOND);
                //Paint moving entity
                g.setColor(Color.WHITE);
                drawClockHand(g, xCenter, yCenter, sec *6 - 90, 180);
            }
            //Paint buffer
            g.drawImage(buffer, 0, 0, this);
            sec = cal.get(Calendar.SECOND);
            //Paint moving entity
            g.setColor(Color.WHITE);
            drawClockHand(g, xCenter, yCenter, sec *6 - 90, 180);
        }

    
        private void drawClockHand(Graphics g, int _xCenter, int _yCenter, double angleDegrees, int length) {
            double angleRadians = Math.toRadians(angleDegrees);
            int x2 = _xCenter + (int) (length * Math.cos(angleRadians));
            int y2 = _yCenter + (int) (length * Math.sin(angleRadians));
            g.drawLine(_xCenter, _yCenter, x2, y2);
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
                makeNoise(); 
            }
        }

        public static void main(String[] args) {
            new Clock();
        }
    }
