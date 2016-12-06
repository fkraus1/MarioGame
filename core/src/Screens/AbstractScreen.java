package Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.gdx.game.MarioGame;
//import com.packtpub.libgdx.canyonbunny.game.Assets;

/**
 * Created by Y.Zhang on 2016/11/30.
 */
public abstract class AbstractScreen implements Screen {
    protected MarioGame game;

    public AbstractScreen(MarioGame game) {
        this.game = game;
    }

    public abstract void render(float delta);
    public abstract void resize(int width, int height);
    public abstract void show();
    public abstract void hide();
    public abstract void pause();

    @Override
    public void resume() {
        //Assets.instance.init(new AssetManager());
    }

    @Override
    public void dispose() {
        //Assets.instance.dispose();
    }
}
