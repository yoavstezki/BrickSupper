package com.yoavstezki.bricksupper.spirites.tileObject;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.yoavstezki.bricksupper.BrickSupper;
import com.yoavstezki.bricksupper.screens.PlayScreen;

/**
 * Created by yoavs on 19/09/2017.
 */

public class Roof extends InteractiveObject {
    public Roof(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(BrickSupper.ROOF_BIT);
    }

    @Override
    public void hit() {

    }

    public Vector2 getVector(){
        return new Vector2(bounds.x, bounds.y);
    }
}
