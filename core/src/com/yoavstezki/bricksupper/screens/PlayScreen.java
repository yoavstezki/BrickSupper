package com.yoavstezki.bricksupper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.yoavstezki.bricksupper.BrickSupper;
import com.yoavstezki.bricksupper.scencs.Hud;

/**
 * Created by yoavs on 18/09/2017.
 */

public class PlayScreen implements Screen {

    private final BrickSupper game;
    private TextureAtlas atlas;

    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    public PlayScreen(BrickSupper game) {
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(BrickSupper.V_WIDTH / BrickSupper.ppm, BrickSupper.V_HEIGHT / BrickSupper.ppm, gameCam);
        hud = new Hud(game.getBatch());


    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Stage stage = hud.getStage();
        game.getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
