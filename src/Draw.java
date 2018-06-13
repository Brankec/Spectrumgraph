import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

public class Draw {
    GraphicsContext gc;
    public void Audio(GraphicsContext gc, MediaPlayer player) {
        this.gc = gc;
        player.setAudioSpectrumNumBands(1024);
        player.setst
        player.setAudioSpectrumListener(new AudioSpectrumListener() {
            int bands = player.getAudioSpectrumNumBands();
            double height = (double)Globals.canvasSizeY / bands;
            double COLUMN_X = 0;
            @Override
            public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases)
            {
                double[] correctedMagnitude = new double[bands];

               if(COLUMN_X >= Globals.canvasSizeX){
                   gc.clearRect(COLUMN_X, 0, 4, Globals.canvasSizeY);
                   COLUMN_X = 0;
                }

                for (int i = 0; i < bands; i++)
                {
                    // Do something with magnitude
                    correctedMagnitude[i] = magnitudes[i] - player.getAudioSpectrumThreshold();

                    //System.out.println(phases[i]);

                    // Draws pixel(rectangle) 1x4.8
                    spectrum(correctedMagnitude[i], COLUMN_X, Globals.canvasSizeY - (i * (height)), 4, height);
                }
                COLUMN_X+=4;
            }
        });
    }

    public void spectrum(double i, double x, double y, double w, double h)
    {
        // Colour of rectangle determined by magnitude of band
        gc.setFill(Color.color(i*0.02, 0, 0.5));

        // Draw rectangle
        gc.fillRect(x, y, w, h);
    }
}
