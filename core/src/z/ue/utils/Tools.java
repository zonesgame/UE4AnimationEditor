package z.ue.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.StreamUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import z.ue.Animation;
import z.ue.Frame;
import z.ue.Vec;

import static z.ue.Core.*;

/**
 *
 */
public class Tools {

    public static Actor getActor(String name) {
        return stage.getRoot().findActor(name);
    }

    public static void loadEditorAnimation(String saveFile) {
        FileHandle handle = Gdx.files.absolute(saveFile);
        if ( !handle.exists())	return;

        ObjectMap<String, Vec> saveMap = new ObjectMap<String, Vec>(512);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(handle.read()));

            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                if (line.trim().length() == 0) continue;

                int colon = line.indexOf(':');
                String name = line.substring(0, colon);
                colon += 1;
                int comma = line.indexOf(',', colon);
                int offsetx = Integer.parseInt( line.substring(colon, comma).trim());
                int offsety = Integer.parseInt( line.substring(comma + 1));

                saveMap.put(name, new Vec(offsetx, offsety));
            }

        } catch (IOException e) {

        } finally {
            StreamUtils.closeQuietly(reader);
        }

        try {
            for (Animation ani : animations) {
                for (Frame fra : ani.frames) {
                    fra.initOffset(saveMap.get(ani.name + "#" + fra.name));
                }
            }
        } catch (Exception e) {

        }
    }

    public static void saveEditorAnimation(Array<Animation> animations, String saveFile) {
        BufferedWriter outWriter = null;
        try {
            outWriter = new BufferedWriter(new OutputStreamWriter(Gdx.files.absolute(saveFile).write(false)));
            for (Animation ani : animations) {
                for (Frame fra : ani.frames) {
                    outWriter.write(ani.name + "#" + fra.name + ": " + fra.offsetX + "," + fra.offsetY + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtils.closeQuietly(outWriter);
        }

        executionCore.updateShowMessage("[BLACK]execute save success.........[]");
    }

}
