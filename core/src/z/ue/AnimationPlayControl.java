package z.ue;

import com.badlogic.gdx.math.MathUtils;

/**
 *
 */
public class AnimationPlayControl {

    /** Defines possible playback modes for an {@link com.badlogic.gdx.graphics.g2d.Animation}. */
    static public enum PlayMode {
        NORMAL,
        REVERSED,
        LOOP,
        LOOP_REVERSED,
        LOOP_PINGPONG,
        LOOP_RANDOM,
    }

    /** Length must not be modified without updating {@link #animationDuration}. See {link #setKeyFrames(T[])}. */
    public int keyFrames;
    private float frameDuration;
    private float animationDuration;
    private int lastFrameNumber;
    private float lastStateTime;

    private float stateDelta;

    private PlayMode playMode = PlayMode.NORMAL;

    /** Constructor, storing the frame duration and key frames.
     *
     * @param frameDuration the time between frames in seconds.
     * @param keyFrames the objects representing the frames. If this Array is type-aware, {@link #getKeyFrames()} can return the
     *           correct type of array. Otherwise, it returns an Object[]. */
    public AnimationPlayControl (float frameDuration, int keyFrames) {
        this.frameDuration = frameDuration;
        setKeyFrames(keyFrames);
    }

    /** Constructor, storing the frame duration and key frames.
     *
     * @param frameDuration the time between frames in seconds.
     * @param keyFrames the objects representing the frames. If this Array is type-aware, {@link #getKeyFrames()} can
     * return the correct type of array. Otherwise, it returns an Object[].*/
    public AnimationPlayControl (float frameDuration, int keyFrames, PlayMode playMode) {
        this(frameDuration, keyFrames);
        setPlayMode(playMode);
    }

    /** Constructor, storing the frame duration and key frames.
     *
     * @param frameDuration the time between frames in seconds.
     * param keyFrames the objects representing the frames. */
    public AnimationPlayControl (float frameDuration) {
        this.frameDuration = frameDuration;
        setKeyFrames(0);
    }

    /** Returns a frame based on the so called state time. This is the amount of seconds an object has spent in the
     * state this Animation instance represents, e.g. running, jumping and so on. The mode specifies whether the animation is
     * looping or not.
     *
     * @param stateTime the time spent in the state represented by this animation.
     * @param looping whether the animation is looping or not.
     * @return the frame of animation for the given state time. */
    public int getKeyFrame (float stateTime, boolean looping) {
        // we set the play mode by overriding the previous mode based on looping
        // parameter value
        PlayMode oldPlayMode = playMode;
        if (looping && (playMode == PlayMode.NORMAL || playMode == PlayMode.REVERSED)) {
            if (playMode == PlayMode.NORMAL)
                playMode = PlayMode.LOOP;
            else
                playMode = PlayMode.LOOP_REVERSED;
        } else if (!looping && !(playMode == PlayMode.NORMAL || playMode == PlayMode.REVERSED)) {
            if (playMode == PlayMode.LOOP_REVERSED)
                playMode = PlayMode.REVERSED;
            else
                playMode = PlayMode.LOOP;
        }

        int frame = getKeyFrame(stateTime);
        playMode = oldPlayMode;
        return frame;
    }

    /** Returns a frame based on the so called state time. This is the amount of seconds an object has spent in the
     * state this Animation instance represents, e.g. running, jumping and so on using the mode specified by
     * {link #setPlayMode(com.badlogic.gdx.graphics.g2d.Animation.PlayMode)} method.
     *
     * @param stateTime
     * @return the frame of animation for the given state time. */
    public int getKeyFrame (float stateTime) {
        int frameNumber = getKeyFrameIndex(stateTime);
        return frameNumber;
    }

    /** Returns the current frame number.
     * @param stateTime
     * @return current frame number */
    public int getKeyFrameIndex (float stateTime) {
        if (keyFrames == 1) return 0;

        int frameNumber = (int)(stateTime / frameDuration);
        switch (playMode) {
            case NORMAL:
                frameNumber = Math.min(keyFrames - 1, frameNumber);
                break;
            case LOOP:
                frameNumber = frameNumber % keyFrames;
                break;
            case LOOP_PINGPONG:
                frameNumber = frameNumber % ((keyFrames * 2) - 2);
                if (frameNumber >= keyFrames) frameNumber = keyFrames - 2 - (frameNumber - keyFrames);
                break;
            case LOOP_RANDOM:
                int lastFrameNumber = (int) ((lastStateTime) / frameDuration);
                if (lastFrameNumber != frameNumber) {
                    frameNumber = MathUtils.random(keyFrames - 1);
                } else {
                    frameNumber = this.lastFrameNumber;
                }
                break;
            case REVERSED:
                frameNumber = Math.max(keyFrames - frameNumber - 1, 0);
                break;
            case LOOP_REVERSED:
                frameNumber = frameNumber % keyFrames;
                frameNumber = keyFrames - frameNumber - 1;
                break;
        }

        lastFrameNumber = frameNumber;
        lastStateTime = stateTime;

        return frameNumber;
    }

    /** Returns the keyframes[] array where all the frames of the animation are stored.
     * @return The keyframes[] field. This array is an Object[] if the animation was instantiated with an Array that was not
     *         type-aware. */
    public int getKeyFrames () {
        return keyFrames;
    }

    /**
     *  设置帧数量
     * */
    public void setKeyFrames (int keyFrames) {
        this.keyFrames = keyFrames;
        this.animationDuration = keyFrames * frameDuration;

        this.stateDelta = 0;
    }

    /**
     *  获取当前帧
     * */
    public int getKeyFrame () {
        return getKeyFrame(stateDelta);
    }

    public void update(float delta) {
        stateDelta += delta;
    }

    // end

    /** Returns the animation play mode. */
    public PlayMode getPlayMode () {
        return playMode;
    }

    /** Sets the animation play mode.
     *
     * @param playMode The animation {@link com.badlogic.gdx.graphics.g2d.Animation.PlayMode} to use. */
    public void setPlayMode (PlayMode playMode) {
        this.playMode = playMode;
    }

    /** Whether the animation would be finished if played without looping (PlayMode#NORMAL), given the state time.
     * @param stateTime
     * @return whether the animation is finished. */
    public boolean isAnimationFinished (float stateTime) {
        int frameNumber = (int)(stateTime / frameDuration);
        return keyFrames - 1 < frameNumber;
    }

    /** Sets duration a frame will be displayed.
     * @param frameDuration in seconds */
    public void setFrameDuration (float frameDuration) {
        this.frameDuration = frameDuration;
        this.animationDuration = keyFrames * frameDuration;
    }

    /** @return the duration of a frame in seconds */
    public float getFrameDuration () {
        return frameDuration;
    }

    /** @return the duration of the entire animation, number of frames times frame duration, in seconds */
    public float getAnimationDuration () {
        return animationDuration;
    }
}
