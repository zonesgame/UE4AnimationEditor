package z.ue;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 *
 */
public class InputManager implements InputProcessor {

    private final Vector2 tempCoords = new Vector2();

    private final boolean[] pointerTouched = new boolean[20];
    private final int[] pointerScreenX = new int[20];
    private final int[] pointerScreenY = new int[20];

    private int mouseScreenX, mouseScreenY;


    private InputMultiplexer screenInput;
    private InputAdapter uiInput;
    private Viewport screenViewport;

    public InputManager(Viewport camera, Stage stage) {
        this.screenViewport = camera;
        this.uiInput = stage;
        screenInput = new InputMultiplexer();
    }

    public void addInputProcess(InputProcessor process) {
        screenInput.addProcessor(process);
    }

    public void removeInputProcess(InputProcessor process) {
        screenInput.removeProcessor(process);
    }


    @Override
    public boolean keyDown(int keycode) {
        if (uiInput.keyDown(keycode))   return true;
        if (screenInput.keyDown(keycode))   return true;

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (uiInput.keyUp(keycode))   return true;
        if (screenInput.keyUp(keycode))   return true;

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        if (uiInput.keyTyped(character))   return true;
        if (screenInput.keyTyped(character))   return true;

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (uiInput.touchDown(screenX,  screenY,  pointer,  button))    return true;

        pointerTouched[pointer] = true;
        pointerScreenX[pointer] = screenX;
        pointerScreenY[pointer] = screenY;
        screenViewport.unproject(tempCoords.set(screenX, screenY));

        if (screenInput.touchDown((int) tempCoords.x,  (int)tempCoords.y,  pointer,  button)) return true;

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (uiInput.touchUp(screenX,  screenY,  pointer,  button))    return true;

        pointerTouched[pointer] = false;
        pointerScreenX[pointer] = screenX;
        pointerScreenY[pointer] = screenY;
        screenViewport.unproject(tempCoords.set(screenX, screenY));

        if (screenInput.touchUp((int) tempCoords.x,  (int)tempCoords.y,  pointer,  button)) return true;

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (uiInput.touchDragged(screenX,  screenY,  pointer))    return true;

        pointerScreenX[pointer] = screenX;
        pointerScreenY[pointer] = screenY;
        mouseScreenX = screenX;
        mouseScreenY = screenY;
        screenViewport.unproject(tempCoords.set(screenX, screenY));

        if (screenInput.touchDragged((int) tempCoords.x,  (int)tempCoords.y,  pointer)) return true;

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (uiInput.mouseMoved(screenX,  screenY))    return true;

        mouseScreenX = screenX;
        mouseScreenY = screenY;
        screenViewport.unproject(tempCoords.set(screenX, screenY));

        if (screenInput.mouseMoved((int) tempCoords.x,  (int)tempCoords.y)) return true;

        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        if (uiInput.scrolled(amount))
            ;
//            return true;
        if (screenInput.scrolled(amount)) return true;

        return false;
    }
}
