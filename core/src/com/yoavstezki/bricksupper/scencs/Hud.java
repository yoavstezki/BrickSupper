package com.yoavstezki.bricksupper.scencs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.yoavstezki.bricksupper.BrickSupper;

/**
 * Created by yoavs on 18/09/2017.
 */

public class Hud implements Disposable {

    private Stage stage;
    private Viewport viewport;
    private Label scoreLabel;

    private Integer score;

    public Hud(SpriteBatch spriteBatch) {

        score = 0;

        viewport = new FitViewport(BrickSupper.V_WIDTH, BrickSupper.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        Table table = new Table();
        defineTable(table);

        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label levelLabel = new Label("1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label levelTitleLabel = new Label("LEVEL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label scoreTitleLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(scoreTitleLabel).expandX();
        table.add(levelTitleLabel).expandX();
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();

        stage.addActor(table);

    }

    private void defineTable(Table table) {
        table.top();
        table.setFillParent(true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public void addScore(int value){
        score+= value;
        scoreLabel.setText(String.format("%06d", score));
    }


}
