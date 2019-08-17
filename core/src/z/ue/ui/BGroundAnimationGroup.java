package z.ue.ui;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import z.ue.Assets;
import z.ue.utils.ZResize;

import static z.ue.Cons.LIST_ANIMATION;
import static z.ue.Cons.LIST_FRAME;
import static z.ue.Cons.TABLE_ZROOT;
import static z.ue.Core.zChangeListener;
import static z.ue.utils.Tools.getActor;

/**
 *
 */
public class BGroundAnimationGroup implements ZResize {

//    private Table windowAnimation;
//    private Table windowFrames;
//
//    private Label labelAnimation;
//    private Label labelFrames;

    private List listAnimation;
    private List listFrames;

    public BGroundAnimationGroup() {
        init();
    }

    private void init() {
        Skin skin = Assets.skin;

        final CheckBox checkBox = new CheckBox("is show", skin);
        checkBox.setChecked(true);
        final Slider slider = new Slider(0, 10, 1, false, skin);

        listAnimation = new List(skin);
        listAnimation.setName(LIST_ANIMATION);
        listAnimation.getSelection().setMultiple(true);
        listAnimation.getSelection().setRequired(false);
        // list.getSelection().setToggle(true);

        ScrollPane scrollPaneAnimation = new ScrollPane(listAnimation, skin);
        scrollPaneAnimation.setFlickScroll(false);
        Label labelAnimation = new Label("", skin); // demos SplitPane respecting widget's minWidth
        Table rightSideTable = new Table(skin);
        rightSideTable.add(labelAnimation).growX().row();
        rightSideTable.add(scrollPaneAnimation).grow();

        listFrames = new List(skin);
        listFrames.setName(LIST_FRAME);
        listFrames.getSelection().setMultiple(true);
        listFrames.getSelection().setRequired(false);
        // list.getSelection().setToggle(true);

        ScrollPane scrollPaneFrames = new ScrollPane(listFrames, skin);
        scrollPaneFrames.setFlickScroll(false);
        Label labelFrames = new Label("", skin); // demos SplitPane respecting widget's minWidth
        Table leftSideTable = new Table(skin);
        leftSideTable.add(labelFrames).growX().row();
        leftSideTable.add(scrollPaneFrames).grow();

        SplitPane splitPane = new SplitPane(rightSideTable, leftSideTable, false, skin, "default-horizontal");

        Window window = new Window("BackGroundRender", skin);
        window.getTitleTable().add(new TextButton("X", skin)).height(window.getPadTop());
        window.setPosition(0, 0);
        window.defaults().spaceBottom(10);

        window.row().fill().expandX();
        window.add(checkBox);
        window.add(slider).minWidth(100).fillX().colspan(3);

        window.row();
        window.add(splitPane).fill().expand().colspan(4).maxHeight(300).maxWidth(300);

        window.pack();


        Table uiNode1 = (Table) getActor(TABLE_ZROOT);
        uiNode1.addActor(window);

        scrollPaneAnimation.addCaptureListener(zChangeListener);
        scrollPaneFrames.addCaptureListener(zChangeListener);
    }

    private void initWindow() {
        Skin skin = Assets.skin;






//        fpsLabel = new Label("fps:", skin);

        // configures an example of a TextField in password mode.
//        final Label passwordLabel = new Label("Textfield in password mode: ", skin);
//        final TextField passwordTextField = new TextField("", skin);
//        passwordTextField.setMessageText("password");
//        passwordTextField.setPasswordCharacter('*');
//        passwordTextField.setPasswordMode(true);



        // window.debug();

    }

    @Override
    public void resize(int width, int height) {

    }
}
