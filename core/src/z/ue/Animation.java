package z.ue;

import com.badlogic.gdx.utils.Array;

/**
 *
 */
public class Animation {

    public Array<Frame> frames;
    public String name;

    public Animation() {
        frames = new Array<Frame>();
    }

    public void addFrame(Frame frame) {
        frames.add(frame);
    }

    public Animation setName(String name) {
        this.name = name;
        return this;
    }

    public Frame getFrame(int index) {
        return frames.get(index);
    }

    @Override
    public String toString() {
        return name;
    }
}
