//package z.ue;
//
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.Touchable;
//import com.badlogic.gdx.scenes.scene2d.ui.Button;
//import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
//import com.badlogic.gdx.scenes.scene2d.ui.Label;
//import com.badlogic.gdx.scenes.scene2d.ui.List;
//import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.badlogic.gdx.scenes.scene2d.ui.Slider;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
//import com.badlogic.gdx.utils.Align;
//
///**
// *
// */
//public class UI {
//
//    Table uiNode1;
//    Table windowAnimation;
//    Table windowFrames;
//
//    List listAnimation;
//    Label labelAnimation;
//
//    List listFrames;
//    Label labelFrames;
//
//    Label labelDescription;
//
//    Button buttonPlayer;
//    CheckBox checkBoxLoop;
//    Slider sliderAniSpeed;
//
//    public UI(Stage stage) {
//
//    }
//
//    public void init(Stage stage) {
//        uiNode1 = new Table();
//        uiNode1.setFillParent(true);
//        stage.addActor(uiNode1);
//
//        Skin skin = Assets.skin;
////		skin.getAtlas().getTextures().iterator().next().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
//        skin.getFont("default-font").getData().markupEnabled = true;
//
//        labelDescription = new Label("", skin);
//        labelDescription.setFontScale(2f);
//        labelDescription.setSize(300, 50);
//        labelDescription.setTouchable(Touchable.disabled);
//        uiNode1.addActor(labelDescription);
//
//        listAnimation = new List(skin);
//        listAnimation.getSelection().setMultiple(true);
//        listAnimation.getSelection().setRequired(false);
//        ScrollPane scrollPaneAnimation = new ScrollPane(listAnimation, skin);
//        scrollPaneAnimation.setFlickScroll(false);
//        labelAnimation = new Label("", skin); // demos SplitPane respecting widget's minWidth
//        labelAnimation.setColor(Color.BLUE);
//        windowAnimation = new Table(skin);
//        windowAnimation.add(labelAnimation).row();
//        windowAnimation.add(scrollPaneAnimation).prefWidth(200).growY();
//        windowAnimation.align(Align.topRight);
//        windowAnimation.setFillParent(true);
//
//        listFrames = new List(skin);
//        listFrames.getSelection().setMultiple(true);
//        listFrames.getSelection().setRequired(false);
//        ScrollPane scrollPaneFrames = new ScrollPane(listFrames, skin);
//        scrollPaneFrames.setFlickScroll(false);
//        labelFrames = new Label("", skin); // demos SplitPane respecting widget's minWidth
//        labelFrames.setColor(Color.RED);
//        windowFrames = new Table(skin);
//        windowFrames.add(labelFrames).row();
//        windowFrames.add(scrollPaneFrames).width(100).growY();
//        windowFrames.right().padRight(200);
//        windowFrames.setFillParent(true);
//
//        uiNode1.addActor(windowAnimation);
//        uiNode1.addActor(windowFrames);
//
//        scrollPaneAnimation.addCaptureListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                List listActor = (List) actor;
//                Object obj = listActor.getSelected();
//                if (obj == null)	return;
//
//                setCurAnimation((Animation) obj);
//            }
//        });
//
//        scrollPaneFrames.addCaptureListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                List listActor = (List) actor;
//                Object obj = listActor.getSelected();
//                if (obj == null)	return;
//
//                setCurFrame((Frame) obj);
//            }
//        });
//
//        buttonPlayer = new TextButton(isPlay ? "Player" : "Pause", skin, "toggle");
//        buttonPlayer.setSize(100, 60);
//        uiNode1.addActor(buttonPlayer);
//        buttonPlayer.addCaptureListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                isPlay = !isPlay;
//                if (actor instanceof  TextButton) {
//                    ((TextButton) actor).setText(isPlay ? "Player" : "Pause");
//                }
//            }
//        });
//
//        checkBoxLoop = new CheckBox("[BLACK]is loop play", skin);
//        checkBoxLoop.setSize(100, 50);
//        checkBoxLoop.setChecked(isLoop);
//        uiNode1.addActor(checkBoxLoop);
//        checkBoxLoop.addCaptureListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                isLoop = !isLoop;
//                aniPlayControl.setPlayMode( isLoop ? AnimationPlayControl.PlayMode.LOOP : AnimationPlayControl.PlayMode.NORMAL);
//            }
//        });
//
//        sliderAniSpeed = new Slider(1, 6, 1, false, skin);
//        sliderAniSpeed.setSize(120, 40);
//        uiNode1.addActor(sliderAniSpeed);
//        sliderAniSpeed.addCaptureListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                if (actor instanceof Slider) {
//                    aniPlayControl.setFrameDuration(((Slider) actor).getValue() * 0.05f);
//                }
//            }
//        });
//    }
//
//
//    public void resize (int width, int height) {
//    }
//
//}
