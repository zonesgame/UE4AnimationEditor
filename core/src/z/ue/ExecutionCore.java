package z.ue;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.visible;
import static z.ue.Cons.LABEL_SHOWMESSAGE;
import static z.ue.Core.curAnimation;
import static z.ue.Core.*;
import static z.ue.Cons.*;
import static z.ue.utils.Tools.*;

/**
 *
 */
public class ExecutionCore {

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

    public void setCurAnimation(Animation animation) {
        curAnimation = animation;
        ((Label) getActor(LABEL_ANIMATION)).setText( animation.toString());
        List listFrames = (List) getActor(LIST_FRAME);
        listFrames.clearItems();
        listFrames.setItems(animation.frames);
        listFrames.setSelectedIndex(0);

        aniPlayControl.setKeyFrames(animation.frames.size);

        setCurFrame((Frame) listFrames.getSelected());
    }

    public void nextAnimation(int addvalue) {
        List listAnimation = (List) getActor(LIST_ANIMATION);
        if (curAnimation == null) {
            listAnimation.setSelectedIndex(0);
            setCurAnimation((Animation) listAnimation.getSelected());
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
        setCurAnimation((Animation) listAnimation.getSelected());
    }

    public void setCurFrame(Frame frame) {
        if (frame.equals(curFrame))	return;

        preFrame = curFrame == null ? frame : curFrame;
        curFrame = frame;
        ((Label) getActor(LABEL_FRAMES)).setText( frame.toString());
        ((Label) getActor(LABEL_DESCRIPTION)).setText("[RED]" + frame.toString()  + "     " +  "[BLUE]" + curAnimation.toString());
    }

    public void nextFrame(int addvalue) {
        if ( curAnimation == null)	return;

        List listFrames = (List) getActor(LIST_FRAME);
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
        setCurFrame((Frame) listFrames.getSelected());
    }

    public void frameMove(int addx, int addy) {
        if (curAnimation == null)	return;

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
}
