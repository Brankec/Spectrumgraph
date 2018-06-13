import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {
    public Button PlayButton, RepeatButton, OpenButton;
    public Text PlayedText, DurationText;
    public Label PlayingLabel;
    public Slider PlaybackSlider;
    public Canvas canvas;

    private MediaPlayer player;
    private Media audio;
    private File fileList;
    private final FileChooser fileChooser = new FileChooser();

    private Draw draw;
    private Time time;

    public GraphicsContext gc;
    private int newBands;
    private boolean addNewBands = false;

    public void initialize() {
        draw = new Draw();
        time = new Time();
        gc = canvas.getGraphicsContext2D();
        Globals.canvasSizeX = (int)canvas.getWidth();
        Globals.canvasSizeY = (int)canvas.getHeight();

        Globals.PlaybackSlider = PlaybackSlider;
        Globals.PlayedText = PlayedText;
        Globals.DurationText = DurationText;
    }

    public void OpenButtonAction() {
        File fileListOld = fileList;
        configureFileChooser(fileChooser);
        fileList = fileChooser.showOpenDialog(Globals.primaryStage); //Opening file explorer and saving selected file here

        if (fileList != null) {
            if(player != null) {//checking if the media player is null
                if (player.getStatus().equals(MediaPlayer.Status.PLAYING)) { //this stops a song that is already playing if opening a new set of songs
                    player.stop();
                    PlayButton.setText("Play");
                }
            }

            if (fileList.toURI() != null) {
                audio = new Media(fileList.toURI().toASCIIString());

                player = new MediaPlayer(audio);
                PlayButtonAction();
                time.timeCalc(player);
                PlayingLabel.setText(fileList.getName());

                player.setVolume(1);//it's set to 1.0 by default which is the max
            }
        } else {
            fileList = fileListOld;
        }
    }
    public void RepeatButtonAction() {
        if(player != null) {
            player.stop();
            player.play();
            PlayButton.setText("Stop");
        }
    }
    public void PlayButtonAction() {
        if(player != null) {//a toggle play button
            if (!player.getStatus().equals(MediaPlayer.Status.PLAYING)) {
                player.play();
                PlayButton.setText("Stop");
            } else {
                player.pause();
                PlayButton.setText("Play");
            }

            playVisual();
        }
    }

    private void playVisual() {//currently the slider controls are in Draw class
        if(player != null) {
            draw.Audio(gc, player);
            PlayingLabel.setText(fileList.getName());

            player.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    PlayButton.setText("Play");
                }
            });
            time.timeCalc(player);
        }
    }

    public void ApplyButtonAction() {
        if (!addNewBands && newBands != player.getAudioSpectrumNumBands()) {
            player.stop();
            PlayButton.setText("Play");
            player.setAudioSpectrumNumBands(newBands);
        }/* else {
            player.pause();
            PlayButton.setText("Play");
        }*/
    }
    public void BandButtonAction2048() {
        newBands = 2048;
    }
    public void BandButtonAction1024() {
        newBands = 1024;
    }
    public void BandButtonAction512() {
        newBands = 512;
    }
    public void BandButtonAction256() {
        newBands = 256;
    }
    public void BandButtonAction128() {
        newBands = 128;
    }
    public void BandButtonAction64() {
        newBands = 64;
    }
    public void BandButtonAction32() {
        newBands = 32;
    }

    private static void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("View songs");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP3", "*.mp3"),
                new FileChooser.ExtensionFilter("WAV", "*.wav") //add formats you'd like to play
        );
    }
}
