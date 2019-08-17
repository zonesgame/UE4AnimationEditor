package z.ue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.viewport.FillViewport;

import z.ue.assets.AssetsLoader;
import z.ue.assets.SFTXAssetsLoader;
import z.ue.input.ZonesChangeListener;
import z.ue.input.ZonesClickListener;
import z.ue.input.ZonesInputProcessor;
import z.ue.ui.GUI;
import z.ue.utils.ZResize;

import static z.ue.Cons.LIST_ANIMATION;
import static z.ue.Cons.LIST_FRAME;
import static z.ue.Core.CENTER;
import static z.ue.Core.aniPlayControl;
import static z.ue.Core.animations;
import static z.ue.Core.curAnimation;
import static z.ue.Core.curFrame;
import static z.ue.Core.executionCore;
import static z.ue.Core.frameDuration;
import static z.ue.Core.isFullScreen;
import static z.ue.Core.isLoop;
import static z.ue.Core.isPlay;
import static z.ue.Core.isShowFrameBound;
import static z.ue.Core.isShowPreviousFrame;
import static z.ue.Core.needResizeArray;
import static z.ue.Core.preFrame;
import static z.ue.Core.preWindowsHeight;
import static z.ue.Core.preWindowsWidth;
import static z.ue.Core.resolutionRender;
import static z.ue.Core.resolutionUI;
import static z.ue.Core.stage;
import static z.ue.Core.viewportRender;
import static z.ue.Core.viewportUI;
import static z.ue.Core.zChangeListener;
import static z.ue.Core.zClickListener;
import static z.ue.Core.zInputProcessor;
import static z.ue.utils.Tools.getActor;
import static z.ue.utils.Tools.loadEditorAnimation;

public class MainInput extends ApplicationAdapter {

	Camera camera;
	InputManager inputManager;

	SpriteBatch batch;
	ShapeRenderer shapeRenderer;




	AssetsLoader loader;

	private SettingsPreferences settings;

	@Override
	public void create () {
		settings = new SettingsPreferences().loadSettings();
		aniPlayControl = new AnimationPlayControl(frameDuration * 0.05f);
		if (isLoop)
			aniPlayControl.setPlayMode(AnimationPlayControl.PlayMode.LOOP);
		Assets.init();

		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);

		camera = new OrthographicCamera();
		viewportRender = new FillViewport(0, 0, camera);
		viewportUI = new FillViewport(0, 0);
		stage = new Stage(viewportUI);
		if (isFullScreen)
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		else
			Gdx.graphics.setWindowedMode(preWindowsWidth, preWindowsHeight);

		executionCore = new ExecutionCore();

		{	// inputs
			inputManager = new InputManager(viewportRender, stage);
			inputManager.addInputProcess(zInputProcessor = new ZonesInputProcessor());
			Gdx.input.setInputProcessor(inputManager);

			zClickListener = new ZonesClickListener();
			zChangeListener = new ZonesChangeListener();
		}

		needResizeArray.add(new GUI(stage));

		initLoadAssets();
	}

	private void initLoadAssets() {
		loader = new SFTXAssetsLoader();
		animations = loader.getAnimations();

		loadEditorAnimation(loader.getEditSaveFile());

		List listAnimation = (List) getActor(LIST_ANIMATION);
		listAnimation.clearItems();
		listAnimation.setItems(animations);
		executionCore.nextAnimation(0);		//  set current play animation
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//		camera.update();
		batch.setProjectionMatrix(camera.combined);

		float delta = Gdx.graphics.getDeltaTime();
		updateAnimationPlay(delta);

		batch.begin();

		batch.setColor(1, 0, 0, 0.3f);
		batch.draw(Assets.whiteColorRegion, 0, CENTER.y, CENTER.x, 1);
		batch.draw(Assets.whiteColorRegion, CENTER.x + 1, CENTER.y, CENTER.x, 1);
		batch.setColor(0, 1, 0, 0.3f);
		batch.draw(Assets.whiteColorRegion, CENTER.x, 0, 1, CENTER.y);
		batch.draw(Assets.whiteColorRegion, CENTER.x, CENTER.y + 1, 1, CENTER.y);
		batch.setColor(Color.WHITE);

		batch.end();



		{		// 锚点绘制
			batch.begin();
			batch.setColor(1, 0, 0, 1f);
			batch.draw(Assets.whiteColorRegion, CENTER.x, CENTER.y , 1, 1);
			batch.setColor(Color.WHITE);
			batch.end();
		}

		stage.act();
		stage.draw();

//		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
//			Gdx.app.exit();
//		}
	}

	public void updateAnimationPlay(float delta) {
		if (curAnimation == null) return;

		if (isPlay) {
			aniPlayControl.update(delta);
			int frameIndex = aniPlayControl.getKeyFrame();
			Frame frame = curAnimation.getFrame(frameIndex);
			if ( !frame.equals(curFrame)) {
				executionCore.setCurFrame(frame);
				((List) getActor(LIST_FRAME)).setSelected(frame);
			}
		}

		batch.begin();
		if (isShowPreviousFrame) {
			batch.setColor(Color.WHITE);
			batch.draw(preFrame.texture, CENTER.x + preFrame.offsetX, CENTER.y + preFrame.offsetY);
			batch.setColor(1, 1, 1, 0.6f);
		}
		batch.draw(curFrame.texture, CENTER.x + curFrame.offsetX, CENTER.y + curFrame.offsetY);
		batch.setColor(Color.WHITE);
		batch.end();

		if ( !isShowFrameBound)	return;

		Gdx.gl.glEnable(GL20.GL_BLEND);
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin();
		shapeRenderer.set(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(0, 0, 1, 1f);
		shapeRenderer.rect(CENTER.x + curFrame.offsetX - 1, CENTER.y + curFrame.offsetY - 1, curFrame.getWidth() + 2, curFrame.getHeight() + 2);
		shapeRenderer.end();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

		viewportRender.setWorldSize(resolutionRender, (height / (float) width) * resolutionRender);
		viewportRender.update(width, height, true);

		viewportUI.setWorldSize(resolutionUI, (height / (float) width) * resolutionUI);
		viewportUI.update(width, height, true);

		for (ZResize resizeObj : needResizeArray) {
			resizeObj.resize(width, height);
		}

//		windowAnimation.setSize(viewportUI.getWorldWidth() * 0.09f, viewportUI.getWorldHeight());
//		windowAnimation.setPosition(viewportUI.getWorldWidth() - windowAnimation.getWidth(), viewportUI.getWorldHeight() - windowAnimation.getHeight());

//		windowFrames.setSize(viewportUI.getWorldWidth() * 0.06f, viewportUI.getWorldHeight());
//		windowFrames.setPosition(viewportUI.getWorldWidth() - windowAnimation.getWidth() - windowFrames.getWidth(), viewportUI.getWorldHeight() - windowFrames.getHeight());
	}

	@Override
	public void dispose () {
		settings.saveSettings();

		batch.dispose();
		stage.dispose();
		Assets.dispose();
	}

}
