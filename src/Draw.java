import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

import java.text.DecimalFormat;

public class Draw {
    GraphicsContext gc;
    int bands;
    public void Audio(GraphicsContext gc, MediaPlayer player) {
        this.gc = gc;
        player.setAudioSpectrumNumBands(128);
        bands = player.getAudioSpectrumNumBands();
        player.setAudioSpectrumListener(new AudioSpectrumListener() {
            int xPos = 0;
            double height = (double)Globals.canvasSizeY / bands;
            double COLUMN_X = 0;
            @Override
            public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases)
            {
                float[] correctedMagnitude = new float[bands];

                if(COLUMN_X >= Globals.canvasSizeX){
                    COLUMN_X = 0;
                }

                for (int i = 0; i < bands; i++)
                {
                    // Do something with magnitude
                    correctedMagnitude[i] = magnitudes[i] - player.getAudioSpectrumThreshold();

                    // Draws pixel(rectangle) 1x4.8
                    spectrum(correctedMagnitude[i], COLUMN_X, i * (height-1), 4, height);
                }
                COLUMN_X+=4;
            }
        });
    }

    public void spectrum(float i, double x, double y, double w, double h)
    {
        // Colour of rectangle determined by magnitude of band
        gc.setFill(Color.color(i*0.02, i*0.02, i*0.02));

        // Draw rectangle
        gc.fillRect(x, y, w, h);
    }

    public void inHeight(int i, int x, int y, int w, double h) {
        double amplitude;
        amplitude = y - h;
        DecimalFormat df = new DecimalFormat("#.00");

        gc.setFill(Color.color(1, Double.parseDouble(df.format(i * 0.00785)), 0));

        gc.fillRect(amplitude, x, w, h);
    }
}
