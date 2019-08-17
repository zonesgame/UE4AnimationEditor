package z.ue.event;

import com.badlogic.gdx.utils.Array;

/**
 *
 */
public class ZEvent {

    public static final byte TEXTUREFILTER  = 1;
    public static final byte UPDATE_SHOWMESSAGE = 2;

    public static final byte SETCURANIMATION  = 3;
    public static final byte NEXTANIMATION = 4;
    public static final byte SETCURFRAME = 5;



    public boolean isWithdrawal = true;
    public byte type;
    public Array<Object> parameterArray = new Array<Object>(8);

    public ZEvent(byte b) {
        this.type = b;
    }

    public ZEvent(byte b, boolean back) {
        this.type = b;
        this.isWithdrawal = back;
    }

}
