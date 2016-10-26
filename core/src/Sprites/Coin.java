package Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.gdx.game.MarioGame;

import Scenes.Hud;
import Screens.PlayScreen;

/**
 * Created by Felix on 21.10.2016.
 */
public class Coin extends InteractiveTileObject {

    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 27+1;//ID aus Tiled +1, wegen unterschiedlicher Zählweise

    public Coin(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(MarioGame.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        if (getCell().getTile().getId() == BLANK_COIN){
            MarioGame.manager.get("audio/sounds/bump.wav", Sound.class).play();
        } else{
            MarioGame.manager.get("audio/sounds/coin.wav", Sound.class).play();
            getCell().setTile(tileSet.getTile(BLANK_COIN));
            Hud.addScore(100);
        }

    }
}
