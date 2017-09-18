package com.yoavstezki.bricksupper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yoavstezki.bricksupper.screens.PlayScreen;

public class BrickSupper extends Game {
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float ppm = 100;

    private SpriteBatch batch;

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
