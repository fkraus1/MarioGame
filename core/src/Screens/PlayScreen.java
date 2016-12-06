package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.game.MarioGame;

import Scenes.Hud;
import Sprites.Enemies.Enemy;
import Sprites.Mario;
import Tools.B2WorldCreator;
import Tools.InputHandler;
import Tools.WorldContactListener;


/**
 * Created by Felix on 19.10.2016.
 */
public class PlayScreen implements Screen {

    private MarioGame game;
    private TextureAtlas atlas;

    private OrthographicCamera gamecam;
    private Viewport gameport;
    private Hud hud;

    //Tiled map Variablen
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d Variablen
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //Sprites
    private Mario player;

    private Music music;
    InputHandler inputHandler;

    public PlayScreen(MarioGame game) {
        atlas = new TextureAtlas("Mario_and_Enemies.pack");

        this.game = game;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(MarioGame.V_WIDTH/MarioGame.PPM, MarioGame.V_HEIGHT/MarioGame.PPM, gamecam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        //Hier wird die Map geladen(Verändern, um mehrere Level zu erlauben)
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/MarioGame.PPM);
        gamecam.position.set(gameport.getWorldWidth()/2, (gameport.getWorldHeight()/2)-0.15f, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

        player = new Mario(this);

        world.setContactListener(new WorldContactListener());

        music = MarioGame.manager.get("audio/music/mario_music.ogg", Music.class);
        music.setLooping(true);
        music.setVolume(0.2f);
        music.play();

    }

    /*
     * A public class to stop the background music.
     * Created by Zhang
     */
    public void stopBackgroundMusic(){
        music.stop();
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    public void update(float dt) {
        //Zuerst User-Input verarbeiten
        handleInput(dt);
        //Touch INput
        handleTouchInput(dt);

        world.step(1 / 60f, 6, 2);

        player.update(dt);
        for (Enemy enemy : creator.getGoombas()){
            enemy.update(dt);
            if (enemy.getX() < player.getX() + 224/MarioGame.PPM){
                enemy.b2body.setActive(true);
            }
        }
        hud.update(dt);

        gamecam.position.x = player.b2body.getPosition().x;

        gamecam.update();
        renderer.setView(gamecam);

    }

    private void handleInput(float dt) {
        //inputHandler
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isKeyJustPressed(Input.Keys.UP)
                && player.b2body.getLinearVelocity().y ==0)//ab && experimentell
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <=2 )
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >=-2 )
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            player.b2body.applyLinearImpulse(new Vector2(0, -0.5f), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            game.setScreen(new MenuScreen(game));

    }

    private void handleTouchInput(float dt) {
        if(game.controller.isUpPressed() && player.b2body.getLinearVelocity().y ==0)//ab && experimentell
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        if(game.controller.isRightPressed() && player.b2body.getLinearVelocity().x <=2 )
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if(game.controller.isLeftPressed() && player.b2body.getLinearVelocity().x >=-2 )
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        if(game.controller.isDownPressed())
            player.b2body.applyLinearImpulse(new Vector2(0, -0.5f), player.b2body.getWorldCenter(), true);

    }

    @Override
    public void render(float delta) {
        update(delta);

        //Screen komplett schwarz setzen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //render Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Enemy enemy : creator.getGoombas())
            enemy.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        game.controller.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);
        game.controller.resize(width, height);
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
