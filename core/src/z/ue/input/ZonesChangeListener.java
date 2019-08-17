package z.ue.input;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import z.ue.Animation;
import z.ue.AnimationPlayControl;
import z.ue.Frame;

import static z.ue.Cons.CHECKBOX_LOOP;
import static z.ue.Cons.CHECKBOX_SHOWBOUND;
import static z.ue.Cons.SCROLLPANE_ANIMATION;
import static z.ue.Cons.SCROLLPANE_FRAME;
import static z.ue.Cons.SELECTBOX_TEXTUREFILTER;
import static z.ue.Cons.SLIDER_ANISPEED;
import static z.ue.Core.aniPlayControl;
import static z.ue.Core.executionCore;
import static z.ue.Core.frameDuration;
import static z.ue.Core.isLoop;
import static z.ue.Core.isShowFrameBound;
import static z.ue.Core.gdxTextureFilter;
import static z.ue.utils.Tools.getActor;

/**
 *
 */
public class ZonesChangeListener extends ChangeListener {

    public ZonesChangeListener() {
        super();
    }


    @Override
    public void changed(ChangeEvent event, Actor actor) {
        String actorName = event.getListenerActor().getName();
        if (actorName == null || actorName.equals(""))  return;

        if (actorName.equals(SCROLLPANE_ANIMATION)) {   // scroll animations
            List listActor = (List) actor;
            Object obj = listActor.getSelected();
            if (obj == null)	return;

            executionCore.setCurAnimation((Animation) obj);
        }

        else if (actorName.equals(SCROLLPANE_FRAME)) {  // scroll frames
            List listActor = (List) actor;
            Object obj = listActor.getSelected();
            if (obj == null)	return;

            executionCore.setCurFrame((Frame) obj);
        }

        else if (actorName.equals(CHECKBOX_LOOP)) {     // checkbox  loop
            isLoop = !isLoop;
            aniPlayControl.setPlayMode( isLoop ? AnimationPlayControl.PlayMode.LOOP : AnimationPlayControl.PlayMode.NORMAL);
        }

        else if (actorName.equals(CHECKBOX_SHOWBOUND)) {    // checkbox showbound
            isShowFrameBound = !isShowFrameBound;
        }

        else if (actorName.equals(SLIDER_ANISPEED)) {    // slider  ani speed
            if (actor instanceof Slider) {
                frameDuration = (int) ((Slider) actor).getValue();
                aniPlayControl.setFrameDuration(frameDuration * 0.05f);
            }
        }

        else if (actorName.equals(SELECTBOX_TEXTUREFILTER)) {           //   selectbox   textureFliter
            gdxTextureFilter = ((Texture.TextureFilter) ((SelectBox) getActor(SELECTBOX_TEXTUREFILTER)).getSelected()).name();
            executionCore.setTextureFilter(Texture.TextureFilter.valueOf(gdxTextureFilter));
        }

        else {

        }
    }


}
