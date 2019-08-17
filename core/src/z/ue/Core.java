package z.ue;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import z.ue.input.ZonesChangeListener;
import z.ue.input.ZonesClickListener;
import z.ue.input.ZonesInputProcessor;
import z.ue.utils.ZResize;

/**
 *
 */
public class Core {

    public static boolean isSequenceMove = false;           //  error position
    public static boolean isPlay;

    // settings data
    public static boolean 	isLoop;
    public static boolean isShowFrameBound;
    public static boolean isShowPreviousFrame;
    public static boolean isFullScreen;
    public static int frameDuration;
    public static int preWindowsWidth, preWindowsHeight;
    public static String gdxTextureFilter;
    // settings data end

    public static Vec CENTER = new Vec();

    public static int resolutionRender = 500, minResolutionRender = 300, maxResolutionRender = 1000;
    public static int resolutionUI = 2000;

    public static Rectangle frameRect = new Rectangle();

    public static Stage stage;
    public static Viewport viewportRender;
    public static Viewport viewportUI;

    public static Array<Animation> animations, bgAnimations;

    public static Animation curAnimation;
    public static Frame curFrame, preFrame, curBGFrame;
    public static AnimationPlayControl aniPlayControl;

    public static Array<ZResize> needResizeArray = new Array(8);

    public static ExecutionCore executionCore;

    public static ZonesInputProcessor zInputProcessor;
    public static ZonesClickListener zClickListener;
    public static ZonesChangeListener zChangeListener;

}
