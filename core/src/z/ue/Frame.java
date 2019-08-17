package z.ue;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 */
public class Frame {

    public int offsetX, offsetY;
    public String name;
    public Texture texture;
    public FileHandle fileHandle;

    public Frame() {
        this(null);
    }

    public Frame (FileHandle file) {
        this.fileHandle = file;
        this.texture = new Texture(file);
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Frame setName(String name) {
        this.name = name;
        return this;
    }

    public Frame initOffset(Vec offset) {
        offsetX = offset.x;
        offsetY = offset.y;
        return this;
    }

    public int getWidth() {
        return texture.getWidth();
    }

    public int getHeight() {
        return texture.getHeight();
    }

    public float getPivotX() {
        return Math.abs(offsetX) / (float) texture.getWidth();
    }

    public float getPivotY() {
        return (texture.getHeight() - Math.abs(offsetY)) / (float) texture.getHeight();
    }

    @Override
    public String toString() {
        return name;
    }
}
