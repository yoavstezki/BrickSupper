package com.yoavstezki.bricksupper.spirites.tileObject;

import com.badlogic.gdx.maps.MapObject;
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
    }

    @Override
    public void hit() {
        setCategoryFilter(BrickSupper.DESTROYED_BIT);
        getCall().setTile(null);
        screen.getHud().addScore(100);
    }
}
