package com.yoavstezki.bricksupper.spirites.tileObject;

import com.badlogic.gdx.maps.MapObject;
import com.yoavstezki.bricksupper.BrickSupper;
import com.yoavstezki.bricksupper.screens.PlayScreen;

/**
 * Created by yoavs on 30/09/2017.
 */

public class Ground extends InteractiveObject {
    public Ground(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(BrickSupper.GROUND_BIT);
    }

    @Override
    public void hit() {

    }
}
