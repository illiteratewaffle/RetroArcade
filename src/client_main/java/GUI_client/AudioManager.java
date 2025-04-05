package GUI_client;
import javafx.scene.media.MediaPlayer;

public class AudioManager {

    public static MediaPlayer mediaPlayer;
    private static boolean muted = false;

    public static boolean isMuted() {
        return muted;
    }

    public static void toggleMute() {
        mediaPlayer.setMute(!muted);
        muted = !muted;
    }

    public static void setMuted(boolean value) {
        muted = value;
    }

    public static void applyMute() {
        mediaPlayer.setMute(muted);
    }
}
