package com.badlogic.gdx.zones;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.tests.utils.GdxTest;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import z.ue.Assets;

/**
 *
 */
public class ZUITest extends GdxTest {
    Object[] listEntries = {"This is a list entry1", "And another one1", "The meaning of life1", "Is hard to come by1",
            "This is a list entry2", "And another one2", "The meaning of life2", "Is hard to come by2", "This is a list entry3",
            "And another one3", "The meaning of life3", "Is hard to come by3", "This is a list entry4", "And another one4",
            "The meaning of life4", "Is hard to come by4", "This is a list entry5", "And another one5", "The meaning of life5",
            "Is hard to come by5"};

    Skin skin;
    Stage stage;
    Texture texture1;
    Texture texture2;

    public Table table;

    public ZUITest() {

    }

    public ZUITest(Table table) {
        this.table = table;

        create();
    }

//    Label fpsLabel;

    @Override
    public void create () {
        if (table != null)
            skin = Assets.skin;
        else
            skin = new Skin(Gdx.files.internal("data/uiskin.json"));
//        texture1 = new Texture(Gdx.files.internal("data/badlogicsmall.jpg"));
//        texture2 = new Texture(Gdx.files.internal("data/badlogic.jpg"));
//        TextureRegion image = new TextureRegion(texture1);
//        TextureRegion imageFlipped = new TextureRegion(image);
//        imageFlipped.flip(true, true);
//        TextureRegion image2 = new TextureRegion(texture2);
        // stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, new PolygonSpriteBatch());
        if (table == null) {
            stage = new Stage(new ScreenViewport());
            Gdx.input.setInputProcessor(stage);
        }


        // Group.debug = true;


//        Label myLabel = new Label("this is some text.", skin);
//        myLabel.setWrap(true);
//
//        Table t = new Table();
//        t.row();
//        t.add(myLabel);
//
//        t.layout();

        final CheckBox checkBox = new CheckBox("is show", skin);
        checkBox.setChecked(true);
        final Slider slider = new Slider(0, 10, 1, false, skin);
//        slider.setAnimateDuration(0.3f);
//        TextField textfield = new TextField("", skin);
//        textfield.setMessageText("Click here!");
//        textfield.setAlignment(Align.center);
//        final SelectBox selectBox = new SelectBox(skin);
//        selectBox.setAlignment(Align.right);
//        selectBox.getList().setAlignment(Align.right);
//        selectBox.getStyle().listStyle.selection.setRightWidth(10);
//        selectBox.getStyle().listStyle.selection.setLeftWidth(20);
//        selectBox.addListener(new ChangeListener() {
//            public void changed (ChangeEvent event, Actor actor) {
//                System.out.println(selectBox.getSelected());
//            }
//        });
//        selectBox.setItems("Android1", "Windows1 long text in item", "Linux1", "OSX1", "Android2", "Windows2", "Linux2", "OSX2",
//                "Android3", "Windows3", "Linux3", "OSX3", "Android4", "Windows4", "Linux4", "OSX4", "Android5", "Windows5", "Linux5",
//                "OSX5", "Android6", "Windows6", "Linux6", "OSX6", "Android7", "Windows7", "Linux7", "OSX7");
//        selectBox.setSelected("Linux6");
//        Image imageActor = new Image(image2);
//        ScrollPane scrollPane = new ScrollPane(imageActor);
        List list = new List(skin);
        list.setItems(listEntries);
        list.getSelection().setMultiple(true);
        list.getSelection().setRequired(false);

        List list2 = new List(skin);
        list2.setItems(listEntries);
        list2.getSelection().setMultiple(true);
        list2.getSelection().setRequired(false);
        // list.getSelection().setToggle(true);

        ScrollPane scrollPane3 = new ScrollPane(list, skin);
        scrollPane3.setFlickScroll(false);
        Label minSizeLabel3 = new Label("Animation Name", skin); // demos SplitPane respecting widget's minWidth
        Table rightSideTable3 = new Table(skin);
        rightSideTable3.add(minSizeLabel3).growX().row();
        rightSideTable3.add(scrollPane3).grow();

        ScrollPane scrollPane2 = new ScrollPane(list2, skin);
        scrollPane2.setFlickScroll(false);
        Label minSizeLabel = new Label("Frame Name", skin); // demos SplitPane respecting widget's minWidth
        Table rightSideTable = new Table(skin);
        rightSideTable.add(minSizeLabel).growX().row();
        rightSideTable.add(scrollPane2).grow();
        SplitPane splitPane = new SplitPane(rightSideTable, rightSideTable3, false, skin, "default-horizontal");
//        fpsLabel = new Label("fps:", skin);

        // configures an example of a TextField in password mode.
//        final Label passwordLabel = new Label("Textfield in password mode: ", skin);
//        final TextField passwordTextField = new TextField("", skin);
//        passwordTextField.setMessageText("password");
//        passwordTextField.setPasswordCharacter('*');
//        passwordTextField.setPasswordMode(true);



        // window.debug();
        Window window = new Window("Dialog", skin);
        window.getTitleTable().add(new TextButton("X", skin)).height(window.getPadTop());
        window.setPosition(0, 0);
        window.defaults().spaceBottom(10);
//        window.row().fill().expandX();
//        window.add(iconButton);
//        window.add(buttonMulti);
//        window.add(imgButton);
//        window.add(imgToggleButton);
        window.row();
        window.add(checkBox);
        window.add(slider).minWidth(100).fillX().colspan(3);
        window.row();
//        window.add(selectBox).maxWidth(100);
//        window.add(textfield).minWidth(100).expandX().fillX().colspan(3);
        window.row();
//        window.add(splitPane);
        window.add(splitPane).fill().expand().colspan(4).maxHeight(300).maxWidth(300);
        window.row();
//        window.add(passwordLabel).colspan(2);
//        window.add(passwordTextField).minWidth(100).expandX().fillX().colspan(2);
//        window.row();
//        window.add(fpsLabel).colspan(4);
        window.pack();

        // stage.addActor(new Button("Behind Window", skin));
        if (table != null)
            table.addActor(window);
        else
            stage.addActor(window);

//        textfield.setTextFieldListener(new TextField.TextFieldListener() {
//            public void keyTyped (TextField textField, char key) {
//                if (key == '\n') textField.getOnscreenKeyboard().show(false);
//            }
//        });

        slider.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Gdx.app.log("UITest", "slider: " + slider.getValue());
            }
        });



        checkBox.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                Gdx.graphics.setContinuousRendering(checkBox.isChecked());
            }
        });
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        fpsLabel.setText("fps: " + Gdx.graphics.getFramesPerSecond());

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose () {
        stage.dispose();
        skin.dispose();
        texture1.dispose();
        texture2.dispose();
    }
}
