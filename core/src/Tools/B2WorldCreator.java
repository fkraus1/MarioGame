package Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gdx.game.MarioGame;

import Screens.PlayScreen;
import Sprites.Brick;
import Sprites.Coin;
import Sprites.Enemies.Goomba;

/**
 * Created by Felix on 21.10.2016.
 */
public class B2WorldCreator {
    private Array<Goomba> goombas;


    public B2WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Boden
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ MarioGame.PPM, (rect.getY() + rect.getHeight() / 2)/MarioGame.PPM);

            body= world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/MarioGame.PPM, rect.getHeight()/2/MarioGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //Pipes
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/MarioGame.PPM, (rect.getY() + rect.getHeight() / 2)/MarioGame.PPM);

            body= world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/MarioGame.PPM, rect.getHeight()/2/MarioGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = MarioGame.OBJECT_BIT;
            body.createFixture(fdef);
        }

        //Bricks
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Brick(screen, rect);
        }

        //Coin-Boxen
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(screen, rect);
        }

        //Untere Weltgrenze
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/MarioGame.PPM, (rect.getY() + rect.getHeight() / 2)/MarioGame.PPM);

            body= world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/MarioGame.PPM, rect.getHeight()/2/MarioGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = MarioGame.GROUND2_BIT;
            body.createFixture(fdef);
        }

        //Ziel
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/MarioGame.PPM, (rect.getY() + rect.getHeight() / 2)/MarioGame.PPM);

            body= world.createBody(bdef);

            shape.setAsBox(rect.getWidth()/2/MarioGame.PPM, rect.getHeight()/2/MarioGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //Goombas erzeugen
        goombas = new Array<Goomba>();
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            goombas.add(new Goomba(screen, rect.getX()/MarioGame.PPM, rect.getY()/MarioGame.PPM));
        }
    }

    public Array<Goomba> getGoombas() {
        return goombas;
    }

}
