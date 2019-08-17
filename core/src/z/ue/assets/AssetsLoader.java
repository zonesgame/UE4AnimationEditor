package z.ue.assets;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 *
 */
public interface AssetsLoader<T> extends Disposable {

    public Array<T> getAnimations();

    public String getEditSaveFile();

}
