package p1.ventanas.tickingclock;

import java.io.File;
import java.util.Calendar;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Noises {
    private Clip ticTac,midNight,newHour,noon;

    public Noises(){
        setupNoises("/home/hertz676/Documentos/graficas/p1/ventanas/tickingclock/tictac.wav",
                        "/home/hertz676/Documentos/graficas/p1/ventanas/tickingclock/Zelda Majora's mask - Clock tower bell sound.wav",
                        "/home/hertz676/Documentos/graficas/p1/ventanas/tickingclock/newHour.wav",
                        "/home/hertz676/Documentos/graficas/p1/ventanas/tickingclock/noon.wav");
    }

    public void setupNoises(String _ticTac, String _midNight, String _newHour,String _noon) {
        try {
            File file1 = new File(_ticTac);
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
}