package z.ue;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

/**
 *
 */
public class MainTest extends Game {

    @Override
    public void create() {
        String filename = "D:\\Games\\目标软件\\三分天下\\素材\\19\\UE4\\texture\\低级枪兵\\BasicGunman.paper2dsprites";
        JsonReader reader = new JsonReader();
        JsonValue fileRoot = reader.parse(Gdx.files.absolute(filename));

//        for (int i = 0; i < fileRoot.size; i++) {
//            JsonValue jsonValueNode = fileRoot.next(i);
//            System.out.println(jsonValueNode.);
//        }

        JsonValue.JsonIterator iterator = fileRoot.iterator();
        while (iterator.hasNext()) {
            JsonValue value = iterator.next();
            System.out.println("-----------------");
            System.out.println(value.name());
        }

    }

}
