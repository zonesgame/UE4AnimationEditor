package z.ue.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static z.ue.Cons.BUTTON_EXPORT;
import static z.ue.Cons.BUTTON_SAVE;

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

        String actorName = event.getRelatedActor().getName();
        if (actorName == null || actorName.equals(""))  return;

        if (actorName.equals(BUTTON_SAVE)) {
//            Core.saveEditor();
        }

        else if (actorName.equals(BUTTON_EXPORT)) {
//            UE4JsonCreater.getInstance().createUE4TextureFile(Gdx.files.absolute(atlasTextureFile), animations);
//            UE4JsonCreater.getInstance().createUE4ShadowFile(Gdx.files.absolute(atlasShadowFile), Gdx.files.absolute(sourceFoledShadow), animations);
        }

        else {

        }
    }

}
