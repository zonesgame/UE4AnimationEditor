package z.ue.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import z.ue.Assets;
import z.ue.utils.ZResize;

import static z.ue.Cons.LABEL_ANIMATION;
import static z.ue.Cons.LABEL_FRAMES;
import static z.ue.Cons.LIST_ANIMATION;
import static z.ue.Cons.LIST_FRAME;
import static z.ue.Cons.SCROLLPANE_ANIMATION;
import static z.ue.Cons.SCROLLPANE_FRAME;
import static z.ue.Cons.TABLE_ZROOT;
import static z.ue.Cons.TYPE_EDITOR_UI;
import static z.ue.Core.zChangeListener;
import static z.ue.utils.Tools.getActor;

/**
 *
 */
public class EditAnimationGroup implements ZResize {

    private Table windowAnimation;
    private Table windowFrames;

    private Label labelAnimation;
    private Label labelFrames;

    private List listAnimation;
    private List listFrames;

    public EditAnimationGroup() {
        init();
    }

    private void init() {
        Skin skin = Assets.skin;
        listAnimation = new List(skin);
        listAnimation.setName(LIST_ANIMATION[TYPE_EDITOR_UI]);
        listAnimation.getSelection().setMultiple(true);
        listAnimation.getSelection().setRequired(false);
        // list.getSelection().setToggle(true);
        ScrollPane scrollPaneAnimation = new ScrollPane(listAnimation, skin);
        scrollPaneAnimation.setName(SCROLLPANE_ANIMATION[TYPE_EDITOR_UI]);
        scrollPaneAnimation.setFlickScroll(false);
//		scrollPaneAnimation.setScrollingDisabled(true, true);
        labelAnimation = new Label("", skin); // demos SplitPane respecting widget's minWidth
        labelAnimation.setName(LABEL_ANIMATION[TYPE_EDITOR_UI]);
        labelAnimation.setColor(Color.BLUE);
        windowAnimation = new Table(skin);
        windowAnimation.add(labelAnimation).row();
        windowAnimation.add(scrollPaneAnimation).prefWidth(200).growY();
        windowAnimation.align(Align.topRight);
//		windowAnimation.right();
        windowAnimation.setFillParent(true);

        listFrames = new List(skin);
        listFrames.setName(LIST_FRAME[TYPE_EDITOR_UI]);
//		listFrames.setItems("  dsfds        ", "bbbbbbbb0", "ccccccccccc", "dddddddddddd");
        listFrames.getSelection().setMultiple(true);
        listFrames.getSelection().setRequired(false);
        // list.getSelection().setToggle(true);
        ScrollPane scrollPaneFrames = new ScrollPane(listFrames, skin);
        scrollPaneFrames.setName(SCROLLPANE_FRAME[TYPE_EDITOR_UI]);
        scrollPaneFrames.setFlickScroll(false);
        labelFrames = new Label("", skin); // demos SplitPane respecting widget's minWidth
        labelFrames.setName(LABEL_FRAMES[TYPE_EDITOR_UI]);
        labelFrames.setColor(Color.RED);
        windowFrames = new Table(skin);
        windowFrames.add(labelFrames).row();
        windowFrames.add(scrollPaneFrames).width(100).growY();
        windowFrames.right().padRight(200);
        windowFrames.setFillParent(true);

        Table uiNode1 = (Table) getActor(TABLE_ZROOT);
        uiNode1.addActor(windowAnimation);
        uiNode1.addActor(windowFrames);

        scrollPaneAnimation.addCaptureListener(zChangeListener);

        scrollPaneFrames.addCaptureListener(zChangeListener);
    }

    @Override
    public void resize(int width, int height) {

    }
}
