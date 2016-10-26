package Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.gdx.game.MarioGame;

import Scenes.Hud;
import Screens.PlayScreen;

/**
 * Created by Felix on 21.10.2016.
 */
public class Brick extends InteractiveTileObject {

    public Brick(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MarioGame.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        setCategoryFilter(MarioGame.DESTROYED_BIT);
        getCell().setTile(null);//Textur entfernen
        Hud.addScore(10);
        MarioGame.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
    }
}
