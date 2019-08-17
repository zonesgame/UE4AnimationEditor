package z.ue.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import z.ue.Assets;
import z.ue.utils.ZResize;

import static z.ue.Cons.BUTTON_EXPORT;
import static z.ue.Cons.BUTTON_PLAYER;
import static z.ue.Cons.BUTTON_SAVE;
import static z.ue.Cons.CHECKBOX_LOOP;
import static z.ue.Cons.CHECKBOX_SHOWBOUND;
import static z.ue.Cons.LABEL_DESCRIPTION;
import static z.ue.Cons.LABEL_SHOWMESSAGE;
import static z.ue.Cons.SELECTBOX_TEXTUREFILTER;
import static z.ue.Cons.SLIDER_ANISPEED;
import static z.ue.Cons.TABLE_ZROOT;
import static z.ue.Core.CENTER;
import static z.ue.Core.frameDuration;
import static z.ue.Core.gdxTextureFilter;
import static z.ue.Core.isLoop;
import static z.ue.Core.isPlay;
import static z.ue.Core.isShowFrameBound;
import static z.ue.Core.needResizeArray;
import static z.ue.Core.viewportRender;
import static z.ue.Core.viewportUI;
import static z.ue.Core.zChangeListener;
import static z.ue.Core.zClickListener;

/**
 *
 */
public class GUI implements ZResize {

    private Button buttonPlayer;
    private Button buttonSave;
    private Button buttonExport;



    private CheckBox checkBoxLoop;
    private CheckBox checkBoxShowBound;

    private Slider sliderAniSpeed;
    private SelectBox selectBoxTextureFilter;

    private Label labelDescription;
    private Label labelShowMessage;

    public GUI(Stage stage) {
        init(stage);
    }


    private void init(Stage stage) {
        Table uiNode1 = new Table();
        uiNode1.setName(TABLE_ZROOT);
        uiNode1.setFillParent(true);
        stage.addActor(uiNode1);

        needResizeArray.add( new EditAnimationGroup());
        needResizeArray.add( new BGroundAnimationGroup());

        Skin skin = Assets.skin;
//		skin.getAtlas().getTextures().iterator().next().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        skin.getFont("default-font").getData().markupEnabled = true;

        buttonPlayer = new TextButton(isPlay ? "Player" : "Pause", skin, "toggle");
        buttonPlayer.setName(BUTTON_PLAYER);
        buttonPlayer.setSize(100, 60);
        uiNode1.addActor(buttonPlayer);
        buttonPlayer.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isPlay = !isPlay;
                if (actor instanceof  TextButton) {
                    ((TextButton) actor).setText(isPlay ? "Player" : "Pause");
                }
            }
        });

        buttonSave = new TextButton("Save", skin);
        buttonSave.setName(BUTTON_SAVE);
        buttonSave.setSize(100, 60);
        buttonSave.setProgrammaticChangeEvents(false);
        uiNode1.addActor(buttonSave);
        buttonSave.addListener(zClickListener);

        buttonExport = new TextButton("Export", skin);
        buttonExport.setName(BUTTON_EXPORT);
        buttonExport.setSize(100, 60);
        buttonExport.setProgrammaticChangeEvents(false);
        uiNode1.addActor(buttonExport);
        buttonExport.addListener(zClickListener);

        {

        }

        {
            checkBoxLoop = new CheckBox("[BLACK]is loop play", skin);
            checkBoxLoop.setName(CHECKBOX_LOOP);
            checkBoxLoop.setSize(100, 50);
            checkBoxLoop.setChecked(isLoop);
            uiNode1.addActor(checkBoxLoop);
            checkBoxLoop.addCaptureListener(zChangeListener);
        }

        {
            checkBoxShowBound = new CheckBox("[BLACK]is show frame bound", skin);
            checkBoxShowBound.setName(CHECKBOX_SHOWBOUND);
            checkBoxShowBound.setSize(180, 50);
            checkBoxShowBound.align(Align.left);
            checkBoxShowBound.setChecked(isShowFrameBound);
            uiNode1.addActor(checkBoxShowBound);
            checkBoxShowBound.addCaptureListener(zChangeListener);
        }

        {
            sliderAniSpeed = new Slider(1, 6, 1, false, skin);
            sliderAniSpeed.setName(SLIDER_ANISPEED);
            sliderAniSpeed.setValue(frameDuration);
            sliderAniSpeed.setSize(120, 40);
            uiNode1.addActor(sliderAniSpeed);
            sliderAniSpeed.addCaptureListener(zChangeListener);
        }

        {
            selectBoxTextureFilter = new SelectBox(skin);
            selectBoxTextureFilter.setName(SELECTBOX_TEXTUREFILTER);
            selectBoxTextureFilter.setSize(180, 40);
            selectBoxTextureFilter.getList().setAlignment(Align.left);
            uiNode1.addActor(selectBoxTextureFilter);
            Texture.TextureFilter filter = Texture.TextureFilter.valueOf(gdxTextureFilter);
            selectBoxTextureFilter.addListener(zChangeListener);
            selectBoxTextureFilter.setItems(Texture.TextureFilter.values());		// Texture.TextureFilter.values()
            selectBoxTextureFilter.setSelected(filter);
        }

        {
            labelDescription = new Label("", skin);
            labelDescription.setName(LABEL_DESCRIPTION);
            labelDescription.setFontScale(2f);
//		labelDescription.setAlignment(Align.top);
//		labelDescription.setFillParent(true);
            labelDescription.setSize(300, 50);
            labelDescription.setTouchable(Touchable.disabled);
//		labelDescription.setDebug(true);
            uiNode1.addActor(labelDescription);
        }

        {
            labelShowMessage = new Label("", skin); // demos SplitPane respecting widget's minWidth
            labelShowMessage.setName(LABEL_SHOWMESSAGE);
            labelShowMessage.setSize(1000, 200);
            labelShowMessage.setFontScale(3f);
            labelShowMessage.setWrap(true);
            labelShowMessage.setVisible(false);
            labelShowMessage.setAlignment(Align.center);
            uiNode1.addActor(labelShowMessage);
        }
    }


    private int uipad = 300;
    @Override
    public void resize(int width, int height) {
        float uiRightPad = (viewportRender.getWorldWidth() / viewportUI.getWorldWidth()) * (uipad / 2f);
        CENTER.set(viewportRender.getWorldWidth() / 2 - uiRightPad, viewportRender.getWorldHeight() / 2);

        labelDescription.setPosition(viewportUI.getWorldWidth() / 2 - labelDescription.getWidth() / 2, viewportUI.getWorldHeight() - labelDescription.getHeight() - 300);
        buttonPlayer.setPosition(viewportUI.getWorldWidth() - buttonPlayer.getWidth() - uipad, viewportUI.getWorldHeight() - buttonPlayer.getHeight());
        sliderAniSpeed.setPosition(0, checkBoxLoop.getHeight());
        checkBoxShowBound.setPosition(0, sliderAniSpeed.getY() + sliderAniSpeed.getHeight());

        buttonSave.setPosition(0, viewportUI.getWorldHeight() - buttonSave.getHeight());
        buttonExport.setPosition(0, buttonSave.getY() - buttonExport.getHeight() - 10);
        selectBoxTextureFilter.setPosition(buttonSave.getX() + buttonSave.getWidth() + 50, viewportUI.getWorldHeight() - selectBoxTextureFilter.getHeight() - 0);
        labelShowMessage.setPosition(viewportUI.getWorldWidth() / 2 - labelShowMessage.getWidth() / 2f - uipad / 2f, 0 );
    }

}
