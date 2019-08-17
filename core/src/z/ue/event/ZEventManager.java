package z.ue.event;

import com.badlogic.gdx.graphics.Texture;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import z.ue.Animation;

import static z.ue.Core.executionCore;
import static z.ue.event.ZEvent.NEXTANIMATION;
import static z.ue.event.ZEvent.SETCURANIMATION;
import static z.ue.event.ZEvent.SETCURFRAME;
import static z.ue.event.ZEvent.TEXTUREFILTER;
import static z.ue.event.ZEvent.UPDATE_SHOWMESSAGE;

/**
 *
 */
public class ZEventManager {

    private Queue<ZEvent> zInputsEvent = new ConcurrentLinkedQueue<ZEvent>();

    public ZEventManager() {

    }

    public void addEvent(ZEvent event) {
        zInputsEvent.add(event);
    }

    public void update() {
        while ( !zInputsEvent.isEmpty()) {
            ZEvent event = this.zInputsEvent.poll();
        }
    }

    private void executeEvent(ZEvent event) {
        switch(event.type) {
            case TEXTUREFILTER:
                executionCore.setTextureFilter((Texture.TextureFilter) event.parameterArray.get(0));
                break;

            case UPDATE_SHOWMESSAGE:
                executionCore.updateShowMessage((String) event.parameterArray.get(0));
                break;

            case SETCURANIMATION:
                executionCore.setCurAnimation((Animation) event.parameterArray.get(0));
                break;

            case NEXTANIMATION:
                executionCore.nextAnimation((Integer) event.parameterArray.get(0));
                break;

            case SETCURFRAME:
                ;
                break;

            case SETCURANIMATION:
                ;
                break;

            case SETCURANIMATION:
                ;
                break;

            default:
                ;

    }

}
