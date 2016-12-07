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
 * Created by Y.Zhang on 2016/12/4.
 */
public class LevelScreen extends AbstractScreen implements Screen{
    private static final String TAG = MenuScreen.class.getName();
    private MarioGame game;

    private Stage stage;
    private Button level1Btn;
    private Button level2Btn;
    private Button level3Btn;
    private Button backBtn;

    public LevelScreen(MarioGame game) {
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
        Texture background = new Texture(Gdx.files.internal("bg.png"));
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
        Button.ButtonStyle style1 = new Button.ButtonStyle();
        Button.ButtonStyle style2 = new Button.ButtonStyle();
        Button.ButtonStyle style3 = new Button.ButtonStyle();
        Button.ButtonStyle style = new Button.ButtonStyle();
        style1.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("1.png"))));
        style1.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("1.png"))));
        style2.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("2.png"))));
        style2.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("2.png"))));
        style3.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("3.png"))));
        style3.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("3.png"))));
        style.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("up.png"))));
        style.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("down.png"))));
        level1Btn = new Button(style1);
        level2Btn = new Button(style2);
        level3Btn = new Button(style3);
        backBtn = new Button(style);

        level1Btn.setPosition(50, 50);
        level2Btn.setPosition(250, 50);
        level3Btn.setPosition(450, 50);
        backBtn.setPosition(550, 20);

        level1Btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onStartClicked("1");
            }
        });
        level2Btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onStartClicked("2");
            }
        });
        level3Btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onStartClicked("3");
            }
        });
        backBtn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                onBackClicked();
            }
        });

        stage.addActor(level1Btn);
        stage.addActor(level2Btn);
        stage.addActor(level3Btn);
        stage.addActor(backBtn);
    }

    private void onStartClicked(String num) {
        game.setScreen(new PlayScreen(game, "level"+num+".tmx"));
    }

    private void onBackClicked() {
        game.setScreen(new MenuScreen(game));
    }
}
