package Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gdx.game.MarioGame;

import Screens.PlayScreen;

/**
 * Created by Felix on 20.10.2016.
 */
public class Mario extends Sprite {
    public enum State{FALLING, JUMPING, STANDING, RUNNING, STOMPING}
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    private Animation marioRun;
    private Animation marioJump;
    private TextureRegion marioStomp;
    private float stateTimer;
    private boolean runningRight;


    public Mario(PlayScreen screen) {
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 4; i++){
            frames.add(new TextureRegion(getTexture(), i*16, 0, 16, 16));
        }
        marioRun = new Animation(0.1f, frames);
        frames.clear();

        for (int i =4; i < 6; i++){
            frames.add(new TextureRegion(getTexture(), i*16, 0, 16, 16));
        }
        marioJump = new Animation(0.1f, frames);

        marioStand = new TextureRegion(getTexture(), 0, 0, 16,16);
        defineMario();
        setBounds(0, 0, 16 / MarioGame.PPM, 16 / MarioGame.PPM);
        setRegion(marioStand);

    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));
    }

    private TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch (currentState){
            case JUMPING:
                region = marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
                break;
        }

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer +dt :0;
        previousState = currentState;
        return region;

    }

    private State getState() {
        if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y<0 && previousState==State.JUMPING) ){
            return State.JUMPING;}
        else if (b2body.getLinearVelocity().y < 0){
            return State.FALLING;}
        else if (b2body.getLinearVelocity().x != 0){
            return State.RUNNING;}
        return State.STANDING;
    }

    private void defineMario() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(400/MarioGame.PPM, 100/MarioGame.PPM);//Temporär, ERSETZEN!
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/MarioGame.PPM);
        fdef.filter.categoryBits = MarioGame.MARIO_BIT;
        fdef.filter.maskBits = MarioGame.GROUND_BIT |
                MarioGame.COIN_BIT |
                MarioGame.BRICK_BIT |
                MarioGame.ENEMY_BIT |
                MarioGame.OBJECT_BIT |
                MarioGame.GROUND2_BIT |//Bei Berührung sofortigen Tod einfügen!
                MarioGame.ZIEL_BIT |
                MarioGame.ENEMY_HEAD_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2/MarioGame.PPM, 6/MarioGame.PPM), new Vector2(2/MarioGame.PPM, 6/MarioGame.PPM));
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("head");
    }
}
