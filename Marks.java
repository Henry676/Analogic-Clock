package p1.ventanas.tickingclock;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Marks {
    
    private Font font;

    public Marks(){
        font = new Font("Times Roman",Font.BOLD,20);
    }

    public void drawMarks(Graphics g, int _xCenter, int _yCenter, int radious) {
        //Draw hour marks (12 marks)
        for (byte i = 0; i < 12; i++) {
            double angulo = Math.toRadians(i * 30); //Every 30 degrees
            int x1 = _xCenter + (int) ((radious - 20) * Math.cos(angulo)); //Beginning point
            int y1 = _yCenter + (int) ((radious - 20) * Math.sin(angulo));
            int x2 = _xCenter + (int) (radious * Math.cos(angulo)); //Ending point
            int y2 = _yCenter + (int) (radious * Math.sin(angulo));
            g.setFont(font);
            g.setColor(Color.CYAN);
            g.drawLine(x1, y1, x2, y2);

            if(i == 0) g.drawString("XII", _xCenter - 5, _yCenter - 205);
            if(i == 1) g.drawString("I", _xCenter + 105, _yCenter - 175);
            if(i == 2) g.drawString("II", _xCenter + 175, _yCenter - 100);
            if(i == 3) g.drawString("III", _xCenter + 205, _yCenter);
            if(i == 4) g.drawString("IIII", _xCenter + 175, _yCenter + 100);
            if(i == 5) g.drawString("V", _xCenter + 105, _yCenter + 185);
            if(i == 6) g.drawString("VI", _xCenter-15, _yCenter + 215);
            if(i == 7) g.drawString("VII", _xCenter - 120, _yCenter + 185);
            if(i == 8) g.drawString("VIII", _xCenter - 205, _yCenter + 100);
            if(i == 9) g.drawString("IX", _xCenter - 225, _yCenter);
            if(i == 10) g.drawString("X", _xCenter - 195, _yCenter - 100);
            if(i == 11) g.drawString("XI", _xCenter - 125, _yCenter - 175);
        }

        //Draw minutes marks (60 marks)
        for (byte i = 0; i < 60; i++) {
            double angleRadians = Math.toRadians(i * 6); //Every 6 degrees
            int x1 = _xCenter + (int) ((radious - 10) * Math.cos(angleRadians)); //Beginning point
            int y1 = _yCenter + (int) ((radious - 10) * Math.sin(angleRadians));
            int x2 = _xCenter + (int) (radious * Math.cos(angleRadians)); //Ending point
            int y2 = _yCenter + (int) (radious * Math.sin(angleRadians));
            g.drawLine(x1, y1, x2, y2); 
        }
    }
}