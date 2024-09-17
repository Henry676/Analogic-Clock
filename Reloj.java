    package p1.ventanas.reloj;

    import java.awt.*;
    import javax.swing.*;
    import javax.sound.sampled.*;
    import java.util.Calendar;
    import java.io.File;

    public class Reloj extends JFrame implements Runnable {
        private Image fondo,buffer;
        private int hora = 0, min = 0, sec = 0;
        private Clip ticTac,doceNoche,horaNueva,doceDia;
        private int centroX = 0,centroY = 0;
        private Thread hilo; 
        private Font fuente; 

        public Reloj() {
            super("Reloj Analógico");
            setSize(800, 800);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            setLocationRelativeTo(null);
            setBackground(Color.GRAY);
            setIconImage(new ImageIcon(getClass().getResource("relojIcon.png")).getImage()); //ICONO
            fuente = new Font("Times Roman",Font.BOLD,20);
            //Coordenadas del centro del reloj o ventana
            centroX = getWidth() / 2;
            centroY = getHeight() / 2;
            cargarSonidos("/home/hertz676/Documentos/graficas/p1/ventanas/reloj/tictac.wav",
                        "/home/hertz676/Documentos/graficas/p1/ventanas/reloj/Zelda Majora's mask - Clock tower bell sound.wav",
                        "/home/hertz676/Documentos/graficas/p1/ventanas/reloj/nuevaHora.wav",
                        "/home/hertz676/Documentos/graficas/p1/ventanas/reloj/midNight.wav");

            hilo = new Thread(this);
            hilo.start();
            
            setVisible(true);
        }

        public void cargarSonidos(String tictac, String midNight, String newHour,String noon) {
            try {
                File archivo1 = new File(tictac);
                AudioInputStream sonido = AudioSystem.getAudioInputStream(archivo1);
                ticTac = AudioSystem.getClip();
                ticTac.open(sonido);

                File archivo2 = new File(midNight);
                AudioInputStream sonido2 = AudioSystem.getAudioInputStream(archivo2);
                doceNoche = AudioSystem.getClip();
                doceNoche.open(sonido2);

                File archivo3 = new File(newHour);
                AudioInputStream sonido3 = AudioSystem.getAudioInputStream(archivo3);
                horaNueva = AudioSystem.getClip();
                horaNueva.open(sonido3);

                File archivo4 = new File(noon);
                AudioInputStream sonido4 = AudioSystem.getAudioInputStream(archivo4);
                doceDia = AudioSystem.getClip();
                doceDia.open(sonido4);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void hacerTicTac() {
            Calendar cal = Calendar.getInstance();
            if (ticTac != null) {
                ticTac.setFramePosition(0); //Reinicia el archivo
                ticTac.start();
            }
            if (doceNoche != null && cal.get(Calendar.HOUR_OF_DAY) == 0 && cal.get(Calendar.MINUTE) == 0) {
                doceNoche.start();
            } else {
                doceNoche.stop();
                doceNoche.setFramePosition(0);
            }
            if(doceDia != null && cal.get(Calendar.HOUR_OF_DAY) == 12 && cal.get(Calendar.MINUTE) == 0){
                doceDia.start();
            }else{
                doceDia.stop();
                doceDia.setFramePosition(0);
            }

            if (horaNueva != null && cal.get(Calendar.MINUTE) == 0 && cal.get(Calendar.HOUR) != 0) {
                horaNueva.start();
            } else {
                horaNueva.stop();
                horaNueva.setFramePosition(0);
            }
        }

        public void dibujarIndicadores(Graphics g, int centroX, int centroY, int radio) {
            //Dibujar indicadores de horas (12 marcas)
            for (byte i = 0; i < 12; i++) {
                double angulo = Math.toRadians(i * 30); // Cada 30 grados
                int x1 = centroX + (int) ((radio - 20) * Math.cos(angulo)); // Punto de inicio
                int y1 = centroY + (int) ((radio - 20) * Math.sin(angulo));
                int x2 = centroX + (int) (radio * Math.cos(angulo)); // Punto final
                int y2 = centroY + (int) (radio * Math.sin(angulo));
                g.setFont(fuente);
                g.setColor(Color.CYAN);
                g.drawLine(x1, y1, x2, y2);

                if(i == 0) g.drawString("XII", centroX - 5, centroY - 205);
                if(i == 1) g.drawString("I", centroX + 105, centroY - 175);
                if(i == 2) g.drawString("II", centroX + 175, centroY - 100);
                if(i == 3) g.drawString("III", centroX + 205, centroY);
                if(i == 4) g.drawString("IV", centroX + 175, centroY + 100);
                if(i == 5) g.drawString("V", centroX + 105, centroY + 175);
                if(i == 6) g.drawString("VI", centroX-10, centroY + 205);
                if(i == 7) g.drawString("VII", centroX - 115, centroY + 175);
                if(i == 8) g.drawString("VIII", centroX - 185, centroY + 100);
                if(i == 9) g.drawString("IX", centroX - 215, centroY);
                if(i == 10) g.drawString("X", centroX - 185, centroY - 100);
                if(i == 11) g.drawString("XI", centroX - 115, centroY - 175);
            }

            //Dibujar indicadores de minutos (60 marcas)
            for (byte i = 0; i < 60; i++) {
                double angulo = Math.toRadians(i * 6); //Cada 6 grados
                int x1 = centroX + (int) ((radio - 10) * Math.cos(angulo)); //Punto de inicio
                int y1 = centroY + (int) ((radio - 10) * Math.sin(angulo));
                int x2 = centroX + (int) (radio * Math.cos(angulo)); //Punto final
                int y2 = centroY + (int) (radio * Math.sin(angulo));
                g.drawLine(x1, y1, x2, y2); 
            }
        }

        public void paint(Graphics g) {
            if (fondo == null) {
                fondo = createImage(getWidth(), getHeight());
                Graphics gfondo = fondo.getGraphics();
                gfondo.setClip(0, 0, getWidth(), getHeight());
                gfondo.setColor(Color.WHITE);
                gfondo.fillOval((getWidth() - 525) / 2, (getHeight() - 525) / 2, 525, 525);
                gfondo.setColor(Color.BLACK);
                gfondo.fillOval((getWidth() - 500) / 2, (getHeight() - 500) / 2, 500, 500);
                gfondo.setColor(Color.CYAN);
                gfondo.fillOval((getWidth() - 20) / 2, (getHeight() - 20) / 2, 20, 20);
                dibujarIndicadores(gfondo, centroX, centroY, 250);
            }
            update(g);
        }

        public void update(Graphics g) {
            g.setClip(0,0,getWidth(),getHeight());
            Calendar cal = Calendar.getInstance();
  
            if(cal.get(Calendar.MINUTE) != min){
                
                //Regenerar la imagen de fondo
                hora = cal.get(Calendar.HOUR);
                min = cal.get(Calendar.MINUTE);
                //Crear la imagen estática
                buffer = createImage(getWidth(),getHeight());

                Graphics gbuffer = buffer.getGraphics();
                gbuffer.setClip(0,0,getWidth(),getHeight());
                gbuffer.drawImage(fondo, 0, 0, this);

                gbuffer.setColor(Color.CYAN);
                dibujarManecilla(gbuffer, centroX, centroY, (hora % 12) * 30 + min * 0.5 - 90, 130);

                gbuffer.setColor(Color.CYAN);
                dibujarManecilla(gbuffer, centroX, centroY, min * 6 - 90, 170);
                g.drawImage(buffer, 0, 0, this);
                sec = cal.get(Calendar.SECOND);
                //Pintar ente movil
                g.setColor(Color.WHITE);
                dibujarManecilla(g, centroX, centroY, sec *6 - 90, 180);
            }
            //Pintar buffer
            
            g.drawImage(buffer, 0, 0, this);
            sec = cal.get(Calendar.SECOND);
            //Pintar ente movil
            g.setColor(Color.WHITE);
            dibujarManecilla(g, centroX, centroY, sec *6 - 90, 180);

        }

    
        private void dibujarManecilla(Graphics g, int centroX, int centroY, double anguloGrados, int longitud) {
            double anguloRadianes = Math.toRadians(anguloGrados);
            int x2 = centroX + (int) (longitud * Math.cos(anguloRadianes));
            int y2 = centroY + (int) (longitud * Math.sin(anguloRadianes));
            g.drawLine(centroX, centroY, x2, y2);
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
                hacerTicTac(); 
            }
        }

        public static void main(String[] args) {
            new Reloj();
        }
    }
