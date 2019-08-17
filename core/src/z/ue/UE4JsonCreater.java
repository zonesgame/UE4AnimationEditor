package z.ue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ObjectMap;

import java.io.IOException;

/**
 *
 */
public class UE4JsonCreater {

    private static UE4JsonCreater instance;

    public static UE4JsonCreater getInstance() {
        if (instance == null)
            instance = new UE4JsonCreater();
        return instance;
    }


    private String curAtlasFile = null;
    private String curShadowFile = null;

    private UE4JsonCreater() {
        initTexturePacker();
    }

    private TexturePacker.Settings settings;
    private TexturePacker packerTexture;
    private TexturePacker packerShadow;

    private void initTexturePacker() {
        settings = new TexturePacker.Settings();
        settings.flattenPaths = false;
        settings.paddingX = 0;
        settings.paddingY = 0;
        settings.maxWidth = 1024;
        settings.maxHeight = 1024;
        settings.format = Pixmap.Format.RGBA8888;     // jpg
//        settings.filterMin = Texture.TextureFilter.Linear;
//        settings.filterMag = Texture.TextureFilter.Linear;

        packerTexture = new TexturePacker(settings);
        packerShadow = new TexturePacker(settings);
    }

    private void createTextureAtlas(FileHandle atlasHandle, Array<Animation> aniArray) {
        FileHandle atlasPngHandle = atlasHandle.sibling(atlasHandle.name() + ".png");
        if (atlasPngHandle.exists())    atlasPngHandle.delete();

        for (Animation ani : aniArray) {
            for (Frame fra : ani.frames) {
                packerTexture.addImage(fra.fileHandle.file());
            }
        }

        this.curAtlasFile = atlasHandle.path() + ".atlas";
        packerTexture.pack(atlasHandle.parent().file(), atlasHandle.name());
    }

    private void createShadowAtlas(FileHandle atlasHandle, FileHandle imageHandle) {
        FileHandle atlasPngHandle = atlasHandle.sibling(atlasHandle.name() + ".png");
        if (atlasPngHandle.exists())    atlasPngHandle.delete();

        for (FileHandle handle : list(imageHandle, new Array<FileHandle>())) {
            packerShadow.addImage(handle.file());
        }

        this.curShadowFile = atlasHandle.path() + ".atlas";
        packerShadow.pack(atlasHandle.parent().file(), atlasHandle.name());
    }

    public void createUE4TextureFile (FileHandle atlasHandle, Array<Animation> aniArray) {
        createTextureAtlas( atlasHandle,  aniArray);
        TextureAtlas atlas = new TextureAtlas(curAtlasFile);
        ObjectMap<String, TextureAtlas.AtlasRegion> atlasregionArray = new ObjectMap<String, TextureAtlas.AtlasRegion>(512);
        for (TextureAtlas.AtlasRegion atlasRegion : atlas.getRegions()) {
            String[] foledList = atlasRegion.name.split("/");
            atlasregionArray.put(foledList[foledList.length - 2] + "/" + foledList[foledList.length - 1], atlasRegion);
        }

        Gdx.files.absolute(curAtlasFile).delete();
        FileHandle paperFile = atlasHandle.parent().child(atlasHandle.nameWithoutExtension() + ".paper2dsprites");
        JsonWriter jsonWriter = new JsonWriter(paperFile.writer(false));

        try {
            jsonWriter.object();
            jsonWriter.object("frames");


            for (Animation ani : aniArray) {
                for (Frame fra : ani.frames) {
                    String keyname = ani.name.substring(0, ani.name.indexOf("_")) + "/" + fra.name;
                    writeJsonFrameNode(jsonWriter, keyname, fra, atlasregionArray.get(keyname));
                }
            }

            jsonWriter.pop();       // frames 结束

            // 文件头数据写入
            jsonWriter.object("meta");
            jsonWriter.set("app", "https://zonesgame.github.io/");
            jsonWriter.set("version", 1.0f);
            jsonWriter.set("target", "paper2d");
            jsonWriter.set("image", atlasHandle.nameWithoutExtension() + ".png");
            jsonWriter.set("format", settings.format);

            jsonWriter.object("size");
            jsonWriter.set("w", atlas.getTextures().first().getWidth());
            jsonWriter.set("h", atlas.getTextures().first().getHeight());
            jsonWriter.pop();

            jsonWriter.set("scale", 1.0f);


            jsonWriter.pop();
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createUE4ShadowFile (FileHandle atlasHandle, FileHandle imageHandle, Array<Animation> aniArray) {
        createShadowAtlas( atlasHandle,  imageHandle);
        TextureAtlas atlas = new TextureAtlas(curShadowFile);
        ObjectMap<String, TextureAtlas.AtlasRegion> atlasregionArray = new ObjectMap<String, TextureAtlas.AtlasRegion>(512);
        for (TextureAtlas.AtlasRegion atlasRegion : atlas.getRegions()) {
            String[] foledList = atlasRegion.name.split("/");
            atlasregionArray.put(foledList[foledList.length - 2] + "/" + foledList[foledList.length - 1], atlasRegion);
        }

        Gdx.files.absolute(curShadowFile).delete();
        FileHandle paperFile = atlasHandle.parent().child(atlasHandle.nameWithoutExtension() + ".paper2dsprites");
        JsonWriter jsonWriter = new JsonWriter(paperFile.writer(false));

        try {
            jsonWriter.object();
            jsonWriter.object("frames");


            for (Animation ani : aniArray) {
                for (Frame fra : ani.frames) {
                    String keyname = ani.name.substring(0, ani.name.indexOf("_")) + "/" + fra.name;
                    writeJsonFrameNode(jsonWriter, keyname, fra, atlasregionArray.get(keyname));
                }
            }

            jsonWriter.pop();       // frames 结束

            // 文件头数据写入
            jsonWriter.object("meta");
            jsonWriter.set("app", "https://zonesgame.github.io/");
            jsonWriter.set("version", 1.0f);
            jsonWriter.set("target", "paper2d");
            jsonWriter.set("image", atlasHandle.nameWithoutExtension() + ".png");
            jsonWriter.set("format", settings.format);

            jsonWriter.object("size");
            jsonWriter.set("w", atlas.getTextures().first().getWidth());
            jsonWriter.set("h", atlas.getTextures().first().getHeight());
            jsonWriter.pop();

            jsonWriter.set("scale", 1.0f);


            jsonWriter.pop();
            jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeJsonFrameNode(JsonWriter jsonWriter, String name, Frame frame, TextureAtlas.AtlasRegion atlasRegion) throws IOException {
        // test
        if (frame.offsetX == 0 && frame.offsetY == 0)   return;

        jsonWriter.object(name);

        jsonWriter.object("frame");
        jsonWriter.set("x", atlasRegion.getRegionX());
        jsonWriter.set("y", atlasRegion.getRegionY());
        jsonWriter.set("w", atlasRegion.getRegionWidth());
        jsonWriter.set("h", atlasRegion.getRegionHeight());
        jsonWriter.pop();

        jsonWriter.set("rotated", false);
        jsonWriter.set("trimmed", false);

        jsonWriter.object("spriteSourceSize");
        jsonWriter.set("x", 0);
        jsonWriter.set("y", 0);
        jsonWriter.set("w", atlasRegion.getRegionWidth());
        jsonWriter.set("h", atlasRegion.getRegionHeight());
        jsonWriter.pop();

        jsonWriter.object("sourceSize");
        jsonWriter.set("w", atlasRegion.getRegionWidth());
        jsonWriter.set("h", atlasRegion.getRegionHeight());
        jsonWriter.pop();

        jsonWriter.object("pivot");
        jsonWriter.set("x", frame.getPivotX());
        jsonWriter.set("y", frame.getPivotY());
        jsonWriter.pop();

        jsonWriter.pop();
    }


    public static Array<FileHandle> list(FileHandle parentPath, Array<FileHandle> fileArray) {
        if (parentPath.isDirectory()) {
            for (FileHandle handle : parentPath.list()) {
                if (handle.isDirectory()) {
                    list(handle, fileArray);
                }
                else
                    fileArray.add(handle);
            }
        }
        else
            fileArray.add(parentPath);

        return fileArray;
    }

}
