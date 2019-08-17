package z.ue.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;

import z.ue.Vec;

import static z.ue.Cons.BUTTON_PLAYER;
import static z.ue.Cons.CHECKBOX_SHOWBOUND;
import static z.ue.Cons.TYPE_EDITOR_UI;
import static z.ue.Core.CENTER;
import static z.ue.Core.curFrame;
import static z.ue.Core.frameRect;
import static z.ue.Core.isFullScreen;
import static z.ue.Core.isPlay;
import static z.ue.Core.isSequenceMove;
import static z.ue.Core.isShowFrameBound;
import static z.ue.Core.isShowPreviousFrame;
import static z.ue.Core.maxResolutionRender;
import static z.ue.Core.minResolutionRender;
import static z.ue.Core.preWindowsHeight;
import static z.ue.Core.preWindowsWidth;
import static z.ue.Core.resolutionRender;
import static z.ue.event.ZEvent.FrameMove;
import static z.ue.event.ZEvent.NextAnimation;
import static z.ue.event.ZEvent.NextFrame;
import static z.ue.utils.Tools.addZEvent;
import static z.ue.utils.Tools.getActor;

/**
 *
 */
public class ZonesInputProcessor implements InputProcessor {

    private boolean isCtrl = false;
    private boolean isAlt = false;
    private boolean isTouchdown = false;

    private int moveAdd = 1;

    private Vec preTouchPoint = new Vec();

    public ZonesInputProcessor() {

    }

    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Input.Keys.W) {
//            executionCore.nextFrame(1);
            addZEvent(NextFrame, 1);
        }
        else if (keycode == Input.Keys.S) {
            if (isCtrl) {
//                saveEditor();
                return true;
            }
//            executionCore.nextFrame(-1);
            addZEvent(NextFrame, -1);
        }
        else if (keycode == Input.Keys.A) {
//            executionCore.nextAnimation(1);
            addZEvent(NextAnimation, 1);
        }
        else if (keycode == Input.Keys.D) {
//            executionCore.nextAnimation(-1);
            addZEvent(NextAnimation, -1);
        }

        else if (keycode == Input.Keys.CONTROL_LEFT) {
            isCtrl = true;
        }
        else if (keycode == Input.Keys.ALT_LEFT) {
            isAlt = true;
            moveAdd = 5;
        }
        else if (keycode == Input.Keys.C) {
            isSequenceMove = true;
        }
        else if (keycode == Input.Keys.Z) {        //  恢复上一走
            if (isCtrl)
                ;
        }
        else if (keycode == Input.Keys.Y) {         // 恢复下一步
            if (isCtrl)
                ;
        }
        else if (keycode == Input.Keys.UP) {
//            executionCore.frameMove( 0, moveAdd);
            addZEvent(FrameMove, 0, moveAdd);
        }
        else if (keycode == Input.Keys.DOWN) {
//            executionCore.frameMove( 0, -moveAdd);
            addZEvent(FrameMove, 0, -moveAdd);
        }
        else if (keycode == Input.Keys.LEFT) {
//            executionCore.frameMove( -moveAdd, 0);
            addZEvent(FrameMove, -moveAdd, 0);
        }
        else if (keycode == Input.Keys.RIGHT) {
//            executionCore.frameMove( moveAdd, 0);
            addZEvent(FrameMove, moveAdd, 0);
        }

        else if (keycode == Input.Keys.Q) {
            isShowPreviousFrame = !isShowPreviousFrame;
        }
        else if (keycode == Input.Keys.ENTER) {
            ((Button) getActor(BUTTON_PLAYER)).setChecked(!isPlay);
        }
        else if (keycode == Input.Keys.TAB) {
            ((CheckBox) getActor(CHECKBOX_SHOWBOUND)).setChecked( !isShowFrameBound);
        }
        else if (keycode == Input.Keys.ESCAPE) {
            isFullScreen = !isFullScreen;
            if (isFullScreen)
            {
                preWindowsWidth = Gdx.graphics.getWidth();
                preWindowsHeight = Gdx.graphics.getHeight();
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
            else
                Gdx.graphics.setWindowedMode(preWindowsWidth, preWindowsHeight);
        }
        else if (keycode == Input.Keys.BACKSPACE) {
            Gdx.app.exit();
        }
        else {
            return false;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.CONTROL_LEFT) {
            isCtrl = false;
        }
        else if (keycode == Input.Keys.ALT_LEFT) {
            isAlt = false;
            moveAdd = 1;
        }
        else if (keycode == Input.Keys.C) {
            isSequenceMove = false;
        }
        else {
            return false;
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        frameRect.set(CENTER.x + curFrame[TYPE_EDITOR_UI].offsetX, CENTER.y + curFrame[TYPE_EDITOR_UI].offsetY, curFrame[TYPE_EDITOR_UI].getWidth(), curFrame[TYPE_EDITOR_UI].getHeight());
        if (frameRect.contains(screenX, screenY)) {
            preTouchPoint.set(screenX, screenY);
            isTouchdown = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        isTouchdown = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if ( isTouchdown) {
            int addx = screenX - preTouchPoint.x;
            int addy = screenY - preTouchPoint.y;
            preTouchPoint.set(screenX, screenY);
//            curFrame.offsetX += addx;
//            curFrame.offsetY += addy;
            addZEvent(FrameMove, addx, addy);
        }
        return false;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if ( !isCtrl)		return false;

        int value = 100 * amount;
        resolutionRender += value;
        if (resolutionRender < minResolutionRender)
            resolutionRender = minResolutionRender;
        else if (resolutionRender > maxResolutionRender)
            resolutionRender = maxResolutionRender;

        Gdx.app.getApplicationListener().resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());     // call  MainInput.resize(int , int);
        return true;
    }
}
