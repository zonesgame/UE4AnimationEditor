package z.ue.input;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import z.ue.AnimationPlayControl;

import static z.ue.Cons.CHECKBOX_LOOP;
import static z.ue.Cons.CHECKBOX_SHOWBOUND;
import static z.ue.Cons.CHECKBOX_SHOW_BG;
import static z.ue.Cons.SCROLLPANE_ANIMATION;
import static z.ue.Cons.SCROLLPANE_FRAME;
import static z.ue.Cons.SELECTBOX_TEXTUREFILTER;
import static z.ue.Cons.SLIDER_ALPHA__BG;
import static z.ue.Cons.SLIDER_ANISPEED;
import static z.ue.Cons.TYPE_BG_UI;
import static z.ue.Cons.TYPE_EDITOR_UI;
import static z.ue.Core.alphaBG;
import static z.ue.Core.aniPlayControl;
import static z.ue.Core.frameDuration;
import static z.ue.Core.gdxTextureFilter;
import static z.ue.Core.isLoop;
import static z.ue.Core.isShowBG;
import static z.ue.Core.isShowFrameBound;
import static z.ue.event.ZEvent.SetCurAnimation;
import static z.ue.event.ZEvent.SetCurFrame;
import static z.ue.event.ZEvent.SetTextureFilter;
import static z.ue.utils.Tools.addZEvent;
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

        if (actorName.equals(SCROLLPANE_ANIMATION[TYPE_EDITOR_UI])) {   // scroll animations
            List listActor = (List) actor;
            Object obj = listActor.getSelected();
            if (obj == null)	return;

//            executionCore.setCurAnimation((Animation) obj);
            addZEvent(SetCurAnimation, obj, TYPE_EDITOR_UI);
        }

        else if (actorName.equals(SCROLLPANE_FRAME[TYPE_EDITOR_UI])) {  // scroll frames
            List listActor = (List) actor;
            Object obj = listActor.getSelected();
            if (obj == null)	return;

//            executionCore.setCurFrame((Frame) obj);
            addZEvent(SetCurFrame, obj, TYPE_EDITOR_UI);
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
//            executionCore.setTextureFilter(Texture.TextureFilter.valueOf(gdxTextureFilter));
            addZEvent(SetTextureFilter, Texture.TextureFilter.valueOf(gdxTextureFilter));
        }


        else if (actorName.equals(CHECKBOX_SHOW_BG)) {        //  window   checkbox showbackground
            isShowBG = !isShowBG;
        }

        else if (actorName.equals(SLIDER_ALPHA__BG)) {
            if (actor instanceof Slider) {
                alphaBG = ((Slider) actor).getValue() / 10f;
            }
        }

        else if (actorName.equals(SCROLLPANE_ANIMATION[TYPE_BG_UI])) {   // scroll  background animations
            List listActor = (List) actor;
            Object obj = listActor.getSelected();
            if (obj == null)	return;

            addZEvent(SetCurAnimation, obj, TYPE_BG_UI);
        }

        else if (actorName.equals(SCROLLPANE_FRAME[TYPE_BG_UI])) {  // scroll background frames
            List listActor = (List) actor;
            Object obj = listActor.getSelected();
            if (obj == null)	return;

            addZEvent(SetCurFrame, obj, TYPE_BG_UI);
        }

        else {

        }
    }


}
