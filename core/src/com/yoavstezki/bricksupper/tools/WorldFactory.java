package com.yoavstezki.bricksupper.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.yoavstezki.bricksupper.screens.PlayScreen;
import com.yoavstezki.bricksupper.spirites.tileObject.Brick;
import com.yoavstezki.bricksupper.spirites.tileObject.Ground;
import com.yoavstezki.bricksupper.spirites.tileObject.Roof;
import com.yoavstezki.bricksupper.spirites.tileObject.SideBound;

/**
 * Created by yoavs on 19/09/2017.
 */

public class WorldFactory {
    public WorldFactory(PlayScreen screen) {
        for (int i = 2; i < 6; i++) {
            defineObject(i, screen);
        }
    }

    private void defineObject(int index, PlayScreen screen) {
        for (MapObject object : screen.getMap().getLayers().get(index).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle bounds = ((RectangleMapObject) object).getRectangle();

            switch (index) {
                case 2:
                    new Brick(screen, object);
                    break;
                case 3:
                    new SideBound(screen, object);
                    break;
                case 4:
                    new Ground(screen, object);
                    break;
                case 5:
                    new Roof(screen, object);
                    break;
            }
        }
    }
}
