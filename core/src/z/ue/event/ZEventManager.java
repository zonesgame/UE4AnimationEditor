package z.ue.event;

import com.badlogic.gdx.graphics.Texture;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import z.ue.Animation;
import z.ue.Frame;

import static z.ue.Core.executionCore;
import static z.ue.event.ZEvent.CancelFrameMove;
import static z.ue.event.ZEvent.FrameMove;
import static z.ue.event.ZEvent.NextAnimation;
import static z.ue.event.ZEvent.NextFrame;
import static z.ue.event.ZEvent.SetCurAnimation;
import static z.ue.event.ZEvent.SetCurFrame;
import static z.ue.event.ZEvent.SetTextureFilter;
import static z.ue.event.ZEvent.UpdateShowMessage;

/**
 *
 */
public class ZEventManager {

    private Queue<ZEvent> zInputsEvent = new ConcurrentLinkedQueue<ZEvent>();
//    private Array<>

    public ZEventManager() {

    }

    public void addEvent(ZEvent event) {
        zInputsEvent.add(event);
    }

    public void update() {
        while ( !zInputsEvent.isEmpty()) {
            executeEvent(this.zInputsEvent.poll());
        }
    }

    private void executeEvent(ZEvent event) {
        boolean isBeCancle = false;

        switch (event.type) {
            case SetTextureFilter:
                executionCore.setTextureFilter((Texture.TextureFilter) event.parameterArray.get(0));
                break;

            case UpdateShowMessage:
                executionCore.updateShowMessage((String) event.parameterArray.get(0));
                break;

            case SetCurAnimation:
                executionCore.setCurAnimation((Animation) event.parameterArray.get(0), (Integer) event.parameterArray.get(1), isBeCancle);
                break;

            case NextAnimation:
                executionCore.nextAnimation((Integer) event.parameterArray.get(0), isBeCancle);
                break;

            case SetCurFrame:
                executionCore.setCurFrame((Frame) event.parameterArray.get(0), (Integer) event.parameterArray.get(1), isBeCancle);
                break;

            case NextFrame:
                executionCore.nextFrame((Integer) event.parameterArray.get(0), isBeCancle);
                break;

            case FrameMove:
                executionCore.frameMove((Integer) event.parameterArray.get(0), (Integer) event.parameterArray.get(1), isBeCancle);
                break;

            case CancelFrameMove:
//                executionCore.cancelFrameMove();
                break;

            default:
                ;
        }
    }

}
