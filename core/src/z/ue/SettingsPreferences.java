package z.ue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;

import static z.ue.Core.frameDuration;
import static z.ue.Core.isFullScreen;
import static z.ue.Core.*;
import static z.ue.Core.isShowFrameBound;
import static z.ue.Core.isShowPreviousFrame;

/**
 *
 */
public class SettingsPreferences {

    private final String SCREEN_WIDTH = "screenWidth";
    private final String SCREEN_HEIGHT = "screenHeight";

    private final String ISLOOP = "isLoop";
    private final String ISSHOWFRAMEBOUND = "isShowFrameBound";
    private final String ISSHOWPREVIOUSFRAME = "isShowPreviousFrame";
    private final String ISFULLSCREEN = "isFullScreen";
    private final String FRAMEDURATION = "frameDuration";
    private final String TEXTUREFILTER = "gdxTextureFilter";

    private Preferences prefs;


    public SettingsPreferences() {
        this("z.ue.animationEditor");
    }

    public SettingsPreferences(String saveID) {
        prefs = Gdx.app.getPreferences(saveID);
    }

    public SettingsPreferences loadSettings() {
        isLoop = prefs.getBoolean(ISLOOP, true);
        isShowFrameBound = prefs.getBoolean(ISSHOWFRAMEBOUND, false);
        isShowPreviousFrame = prefs.getBoolean(ISSHOWPREVIOUSFRAME, false);
        isFullScreen = prefs.getBoolean(ISFULLSCREEN, false);

        frameDuration = prefs.getInteger(FRAMEDURATION, 4);
        preWindowsWidth = prefs.getInteger(SCREEN_WIDTH, 1280);
        preWindowsHeight = prefs.getInteger(SCREEN_HEIGHT, 720);

        gdxTextureFilter = prefs.getString(TEXTUREFILTER, Texture.TextureFilter.Nearest.name());

        return this;
    }

    public void saveSettings() {
        if ( !Gdx.graphics.isFullscreen()) {
            preWindowsWidth = Gdx.graphics.getWidth();
            preWindowsHeight = Gdx.graphics.getHeight();
        }

        prefs.putBoolean(ISLOOP, isLoop);
        prefs.putBoolean(ISSHOWFRAMEBOUND, isShowFrameBound);
        prefs.putBoolean(ISSHOWPREVIOUSFRAME, isShowPreviousFrame);
        prefs.putBoolean(ISFULLSCREEN, isFullScreen);

        prefs.putInteger(FRAMEDURATION, frameDuration);
        prefs.putInteger(SCREEN_WIDTH, preWindowsWidth);
        prefs.putInteger(SCREEN_HEIGHT, preWindowsHeight);

        prefs.putString(TEXTUREFILTER, gdxTextureFilter);

        prefs.flush();
    }

}
