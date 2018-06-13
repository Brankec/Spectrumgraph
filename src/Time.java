import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Time {
    private void Time() {
    }

    public void timeCalc(MediaPlayer player) {//This is where the controls for the playback slider are and where it shows the duration
        //add listeners
        Globals.PlaybackSlider.valueProperty().addListener(new InvalidationListener() { //listener for video jump using slider
            public void invalidated(Observable arg0) {
                if (Globals.PlaybackSlider.isPressed())
                    player.seek(player.getMedia().getDuration().multiply(Globals.PlaybackSlider.getValue() / 100));
            }
        });

        player.currentTimeProperty().addListener(new InvalidationListener() { //updating video slider according to time
            public void invalidated(Observable observable) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        Globals.PlaybackSlider.setValue(player.getCurrentTime().toMillis() / player.getTotalDuration().toMillis() * 100);
                        Duration currentTime = player.getCurrentTime();
                        Duration duration = player.getMedia().getDuration();

                        Globals.PlayedText.setText(formatTime(currentTime));
                        Globals.DurationText.setText(formatTime(duration));
                    }
                });
            }
        });
    }

    private static String formatTime(javafx.util.Duration time) {
        int intElapsed = (int)Math.floor(time.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                - elapsedMinutes * 60;


        return String.format("%02d:%02d",
                elapsedMinutes, elapsedSeconds);
    }
}
