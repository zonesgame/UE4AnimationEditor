package z.ue.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.zones.ZUITest;

/**
 *
 */
public class Main {

    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;

        new LwjglApplication(new ZUITest(), config);
//        new LwjglApplication(new UITest(), config);
//        new LwjglApplication(new UBJsonTest(), config);
//        new LwjglApplication(new MainTest(), config);
    }

}
