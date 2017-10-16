package com.yoavstezki.bricksupper.spirites.tileObject;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.yoavstezki.bricksupper.BrickSupper;
import com.yoavstezki.bricksupper.screens.PlayScreen;

/**
 * Created by yoavs on 16/10/2017.
 */

public class SideBound extends InteractiveObject {

    public SideBound(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(BrickSupper.SIDE_BOUND_BIT);
    }

    @Override
    public void hit() {

    }


    public Boolean isLeft() {
        return bounds.getX() == 0;
    }

    public Vector2 getVector(){
        return new Vector2(bounds.x, bounds.y);
    }
}
