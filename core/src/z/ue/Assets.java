package z.ue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 *
 */
public class Assets {

    public static TextureRegion whiteColorRegion;

    public static Skin skin;


    public static void init() {
        Pixmap colorPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        colorPixmap.setColor(Color.WHITE);
        colorPixmap.fillRectangle(0, 0, colorPixmap.getWidth(), colorPixmap.getHeight());
        whiteColorRegion = new TextureRegion(new Texture(colorPixmap));

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
    }

    public static void dispose() {
        whiteColorRegion.getTexture().dispose();
        skin.dispose();
    }

}
