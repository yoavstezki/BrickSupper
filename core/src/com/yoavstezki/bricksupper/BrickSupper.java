package com.yoavstezki.bricksupper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yoavstezki.bricksupper.screens.PlayScreen;

public class BrickSupper extends Game {
    public static final int V_WIDTH = 270;
    public static final int V_HEIGHT = 375;
    public static final float ppm = 100;

    private SpriteBatch batch;

    public static final short ROOF_BIT = 1;
    public static final short BALL_BIT = 2;
    public static final short PAD_BIT = 4;
    public static final short BRICK_BIT = 8;
    public static final short GROUND_BIT = 16;
    public static final short DESTROYED_BIT = 32;
    public static final short SIDE_BOUND_BIT = 64;



    @Override
    public void create() {
        batch = new SpriteBatch();

        setScreen(new PlayScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
