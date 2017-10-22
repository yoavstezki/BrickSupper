package com.yoavstezki.bricksupper.spirites.tileObject;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.yoavstezki.bricksupper.BrickSupper;
import com.yoavstezki.bricksupper.screens.PlayScreen;

/**
 * Created by yoavs on 19/09/2017.
 */

public class Brick extends InteractiveObject {
    public Brick(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(BrickSupper.BRICK_BIT);
        setCategoryFilter(BrickSupper.DESTROYED_BIT);
        getCall().setTile(null);
    }

    @Override
    public void hit() {
        setCategoryFilter(BrickSupper.DESTROYED_BIT);
        getCall().setTile(null);
        screen.getHud().addScore(100);
    }

    public Vector2 getVector() {
        return new Vector2(bounds.x, bounds.y);
    }

    public Boolean isHitLeft(float y) {
        return bounds.y / 100 == y;
    }

    public Boolean isHitRight(float y) {
        return bounds.width - bounds.y == y;
    }
}
