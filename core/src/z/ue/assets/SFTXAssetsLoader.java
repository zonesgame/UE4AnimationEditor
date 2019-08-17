package z.ue.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

import z.ue.Animation;
import z.ue.Frame;

import static z.ue.utils.Tools.loadEditorAnimation;

/**
 *
 */
public class SFTXAssetsLoader implements AssetsLoader {

    String editorName = "马匹1", fileName = "Horse1";
    String atlasTextureFile = "D:\\Games\\目标软件\\三分天下\\素材\\19\\UE4\\texture\\" + editorName + "\\" + fileName;
    String atlasShadowFile = "D:\\Games\\目标软件\\三分天下\\素材\\19\\UE4\\texture\\" + editorName + "\\" + fileName + "Shadow";

    String sourceFoledShadow = "D:\\Games\\目标软件\\三分天下\\素材\\19\\UE4\\texture\\" + editorName + "\\阴影";

    String editSaveFile = "D:\\Games\\目标软件\\三分天下\\素材\\19\\UE4\\texture\\" + editorName + "\\UE4Editor" + editorName + ".zsave";
    String textureSourceFoled = "D:\\Games\\目标软件\\三分天下\\素材\\19\\UE4\\texture\\" + editorName + "\\纹理";


//    private Array<Animation> animationsTmp = null;

    public SFTXAssetsLoader() {
    }

    @Override
    public Array<Animation> getAnimations() {
//        if (animations == null) {
//            animations = new Array<Animation>(64);
//            initSource();
//        }

        Array<Animation> animationsTmp = initSource();
        loadEditorAnimation(animationsTmp, getEditSaveFile());		// load save data

        return animationsTmp;
    }

    @Override
    public Array getBackgroundAnimations() {
        return getAnimations();
    }

    @Override
    public String getEditSaveFile() {
        return editSaveFile;
    }

    @Override
    public String getAtlasTextureFile() {
        return atlasTextureFile;
    }

    @Override
    public String getAtlasShadowFile() {
        return atlasShadowFile;
    }

    @Override
    public String getSourceFoledShadow() {
        return sourceFoledShadow;
    }

    private Array<Animation> initSource() {
        Array<Animation> animations = new Array<Animation>(64);
        FileHandle sourceHandle = Gdx.files.absolute(textureSourceFoled);
        for (FileHandle handle : sourceHandle.list()) {
            FileHandle[] foledList = handle.list();
            int frameCount = foledList.length / 8;

            for (int d = 1; d <= 8; d++) {
                Animation aniData = new Animation().setName(handle.name() + "_d" + d);
                animations.add(aniData);

                for (int f= 0; f < frameCount; f++) {
                    String fileName = "d" + d + "f";
                    if (f <= 9)
                        fileName += "0";
                    fileName += f;
                    FileHandle regionHandle = handle.child(fileName + ".png");

//					Texture texture = new Texture(regionHandle);
//					texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                    Frame frame = new Frame(regionHandle).setName(fileName);
                    aniData.addFrame(frame);
                }
            }
        }

        return animations;
    }

    @Override
    public void dispose() {

    }
}
