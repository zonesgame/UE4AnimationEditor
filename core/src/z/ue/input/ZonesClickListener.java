package z.ue.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import z.ue.UE4JsonCreater;

import static z.ue.Cons.BUTTON_EXPORT;
import static z.ue.Cons.BUTTON_SAVE;
import static z.ue.Core.animations;
import static z.ue.Core.loader;
import static z.ue.utils.Tools.saveEditorAnimation;

/**
 *
 */
public class ZonesClickListener extends ClickListener {

    public ZonesClickListener() {
        super();
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        super.touchUp(event, x, y, pointer, button);

        String actorName = event.getListenerActor().getName();
        if (actorName == null || actorName.equals(""))  return;

        if (actorName.equals(BUTTON_SAVE)) {
            saveEditorAnimation(animations, loader.getEditSaveFile());
        }

        else if (actorName.equals(BUTTON_EXPORT)) {
            UE4JsonCreater.getInstance().createUE4TextureFile(Gdx.files.absolute(loader.getAtlasTextureFile()), animations);
            UE4JsonCreater.getInstance().createUE4ShadowFile(Gdx.files.absolute(loader.getAtlasShadowFile()), Gdx.files.absolute(loader.getSourceFoledShadow()), animations);
        }

        else {

        }
    }

}
