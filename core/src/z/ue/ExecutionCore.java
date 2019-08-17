package z.ue;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;

import z.ue.event.ZEvent;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.visible;
import static z.ue.Cons.LABEL_ANIMATION;
import static z.ue.Cons.LABEL_DESCRIPTION;
import static z.ue.Cons.LABEL_FRAMES;
import static z.ue.Cons.LABEL_SHOWMESSAGE;
import static z.ue.Cons.LIST_ANIMATION;
import static z.ue.Cons.LIST_FRAME;
import static z.ue.Cons.TYPE_EDITOR_UI;
import static z.ue.Core.aniPlayControl;
import static z.ue.Core.animations;
import static z.ue.Core.curAnimation;
import static z.ue.Core.curFrame;
import static z.ue.Core.isSequenceMove;
import static z.ue.Core.preFrame;
import static z.ue.event.ZEvent.CancelFrameMove;
import static z.ue.event.ZEvent.SetCurAnimation;
import static z.ue.event.ZEvent.SetCurFrame;
import static z.ue.utils.Tools.getActor;

/**
 *
 */
public class ExecutionCore {

    Array<ZEvent> operatingSavePool = new Array<ZEvent>();

    public ExecutionCore() {

    }


    public void updateShowMessage(String msg) {
        Label labelShowMessage = (Label) getActor(LABEL_SHOWMESSAGE);
        labelShowMessage.setVisible(true);
        labelShowMessage.setText(msg);
        {	// reset
            labelShowMessage.clearActions();
        }
        labelShowMessage.addAction(sequence(alpha(1f), delay(1.5f), alpha(0f, 1.5f), visible(false)));
    }

    public void setTextureFilter(Texture.TextureFilter textureFilter) {
        if (animations == null)	return;

        for (Animation ani : animations) {
            for (Frame fra : ani.frames) {
                fra.texture.setFilter(textureFilter, textureFilter);
            }
        }
    }

    public void setCurAnimation(Animation animation, int typeScrollPane, boolean isCancel) {
        if (isCancel)
            operatingSavePool.add(new ZEvent(SetCurAnimation, animation));

        curAnimation = animation;
        ((Label) getActor(LABEL_ANIMATION[typeScrollPane])).setText( animation.toString());
        List listFrames = (List) getActor(LIST_FRAME[typeScrollPane]);
        listFrames.clearItems();
        listFrames.setItems(animation.frames);
        listFrames.setSelectedIndex(0);

        aniPlayControl.setKeyFrames(animation.frames.size);

        setCurFrame((Frame) listFrames.getSelected(), typeScrollPane, false);
    }

    public void nextAnimation(int addvalue, boolean isCancel) {
        int typeScrollPane = TYPE_EDITOR_UI;

        List listAnimation = (List) getActor(LIST_ANIMATION[typeScrollPane]);
        if (curAnimation == null) {
            listAnimation.setSelectedIndex(0);
            setCurAnimation((Animation) listAnimation.getSelected(), typeScrollPane, isCancel);
            return;
        }

        int curaniIndex = listAnimation.getSelectedIndex();
        if (curaniIndex == -1) {
            listAnimation.setSelected(curAnimation);
            curaniIndex = listAnimation.getSelectedIndex();
        }
        int anilength = listAnimation.getItems().size;
        curaniIndex += addvalue;
        if (curaniIndex < 0)
            curaniIndex = anilength - 1;
        else if (curaniIndex >= anilength)
            curaniIndex = 0;

        listAnimation.setSelectedIndex(curaniIndex);
        setCurAnimation((Animation) listAnimation.getSelected(), typeScrollPane, isCancel);
    }

    public void setCurFrame(Frame frame, int typeScrollPane, boolean isCancel) {
        if (frame.equals(curFrame))	return;

        if (isCancel)
            operatingSavePool.add(new ZEvent(SetCurFrame, frame));

        preFrame = curFrame == null ? frame : curFrame;
        curFrame = frame;
        ((Label) getActor(LABEL_FRAMES[typeScrollPane])).setText( frame.toString());
        if (typeScrollPane == TYPE_EDITOR_UI)
            ((Label) getActor(LABEL_DESCRIPTION)).setText("[RED]" + frame.toString()  + "     " +  "[BLUE]" + curAnimation.toString());
    }

    public void nextFrame(int addvalue, boolean isCancel) {
        if ( curAnimation == null)	return;

        int typeScrollPane = TYPE_EDITOR_UI;

        List listFrames = (List) getActor(LIST_FRAME[typeScrollPane]);
        int curframeIndex = listFrames.getSelectedIndex();
        if (curframeIndex == -1) {
            listFrames.setSelected(curFrame);
            curframeIndex = listFrames.getSelectedIndex();
        }
        int framelength = listFrames.getItems().size;
        curframeIndex += addvalue;
        if (curframeIndex < 0)
            curframeIndex = framelength - 1;
        else if (curframeIndex >= framelength)
            curframeIndex = 0;

        listFrames.setSelectedIndex(curframeIndex);
        setCurFrame((Frame) listFrames.getSelected(), typeScrollPane, isCancel);
    }

    public void frameMove(int addx, int addy, boolean isCancel) {
        if (curAnimation == null)	return;

        if (isCancel)
            operatingSavePool.add(new ZEvent(CancelFrameMove, curAnimation, curFrame, isSequenceMove, addx, addy));

        if ( !isSequenceMove) {	// 当前帧位置移动
            curFrame.offsetX += addx;
            curFrame.offsetY += addy;
        }
        else {		// 当前动画位置移动
            for (Frame fra : curAnimation.frames) {
                fra.offsetX += addx;
                fra.offsetY += addy;
            }
        }
    }

    public void cancelFrameMove(Animation tmpAnimation, Frame tmpFrame, boolean bmpIsSequenceMove, int addx, int addy) {
        if (tmpAnimation == null)	return;

        if ( !bmpIsSequenceMove) {	// 当前帧位置移动
            tmpFrame.offsetX += addx;
            tmpFrame.offsetY += addy;
        }
        else {		// 当前动画位置移动
            for (Frame fra : tmpAnimation.frames) {
                fra.offsetX += addx;
                fra.offsetY += addy;
            }
        }
    }
}
