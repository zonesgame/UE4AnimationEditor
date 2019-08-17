package z.ue.event;

import com.badlogic.gdx.utils.Array;

/**
 *
 */
public class ZEvent {

//    public static final byte TEXTUREFILTER  = 1;
    public static final byte SetTextureFilter = 1;
//    public static final byte UPDATE_SHOWMESSAGE = 2;
public static final byte UpdateShowMessage = 2;

//    public static final byte SETCURANIMATION  = 3;
    public static final byte SetCurAnimation = 3;
//    public static final byte NEXTANIMATION = 4;
    public static final byte NextAnimation = 4;
//    public static final byte SETCURFRAME = 5;
    public static final byte SetCurFrame = 5;
//    public static final byte NEXTFRAME = 6;
    public static final byte NextFrame = 6;
//    public static final byte FRAMEMOVE = 7;
    public static final byte FrameMove = 7;
    public static final byte CancelFrameMove = 8;



    public boolean isCancel = true;
    public byte type;
    public Array<Object> parameterArray;

    public ZEvent(byte b, Object... objs) {
        this(b, true, objs);
    }

    public ZEvent(byte b, boolean back, Object... objs) {
        this.type = b;
        parameterArray = new Array<Object>(objs);
        this.isCancel = back;
    }

}
