package Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.gdx.game.MarioGame;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

/**
 * Created by Y.Zhang on 2016/11/30.
 */
public class MenuScreen extends AbstractScreen implements Screen{
    private static final String TAG = MenuScreen.class.getName();
    private MarioGame game;

    private Stage stage;
    private Texture upTexture;
    private Texture downTexture;
    private Button startBtn;
    private Button optionBtn;

    public MenuScreen(MarioGame game) {
        super(game);
        this.game = game;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        initStartBtn();
        //if(Gdx.input.isTouched()) onStartClicked();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Texture background = new Texture(Gdx.files.internal("start.png"));
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.batch.end();
        stage.act();
        stage.draw();
    }

    public void rebuildStage(){

    }

    @Override public void resize(int width, int height) {}
    @Override public void show() {
        rebuildStage();
    }
    @Override public void hide() {}
    public void pause() {}

    private void initStartBtn() {
        upTexture = new Texture(Gdx.files.internal("up.png"));
        downTexture = new Texture(Gdx.files.internal("down.png"));
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(upTexture));
        style.down = new TextureRegionDrawable(new TextureRegion(downTexture));
        startBtn = new Button(style);

        startBtn.setPosition(10, 50);

        startBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onStartClicked();
            }
        });

        stage.addActor(startBtn);
    }

    private void onStartClicked() {
        game.setScreen(new LevelScreen(game));
    }

    private void onOptionsClicked() {
        //Do nothing...
    }
}
