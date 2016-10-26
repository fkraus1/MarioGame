package Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.gdx.game.MarioGame;

import Sprites.Enemies.Enemy;
import Sprites.InteractiveTileObject;

/**
 * Created by Felix on 21.10.2016.
 */

public class WorldContactListener implements ContactListener{

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        if (fixA.getUserData() == "head"|| fixB.getUserData() == "head"){
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ?fixB : fixA;

            if (object.getUserData() instanceof InteractiveTileObject){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }

        switch (cDef){
            case MarioGame.ENEMY_HEAD_BIT | MarioGame.MARIO_BIT:
                if (fixA.getFilterData().categoryBits == MarioGame.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead();
                else
                    ((Enemy)fixB.getUserData()).hitOnHead();
                break;
            case MarioGame.ENEMY_BIT | MarioGame.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == MarioGame.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case MarioGame.ENEMY_BIT | MarioGame.GROUND_BIT:
                if (fixA.getFilterData().categoryBits == MarioGame.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case MarioGame.MARIO_BIT | MarioGame.ENEMY_BIT:
                Gdx.app.log("MARIO", "DIED");
                break;
            case MarioGame.MARIO_BIT | MarioGame.GROUND2_BIT:
                Gdx.app.log("MARIO", "DIED");
                break;
            case MarioGame.ENEMY_BIT |MarioGame.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
