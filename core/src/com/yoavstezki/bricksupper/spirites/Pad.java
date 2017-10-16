package com.yoavstezki.bricksupper.spirites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.yoavstezki.bricksupper.BrickSupper;
import com.yoavstezki.bricksupper.screens.PlayScreen;

/**
 * Created by yoavs on 24/09/2017.
 */

public class Pad extends Sprite {
    private World world;
    private TextureRegion pad;
    public Body body;
    private Boolean isBallOnPad = false;

    public Pad(PlayScreen screen) {
        this.world = screen.getWorld();

        this.pad = new TextureRegion(screen.getAtlas().findRegion("pad"));

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / BrickSupper.ppm, 32 / BrickSupper.ppm);
        bodyDef.type = bodyDef.type.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        EdgeShape padShape = new EdgeShape();
        padShape.set(new Vector2(-37 / BrickSupper.ppm, 6 / BrickSupper.ppm), new Vector2(38 / BrickSupper.ppm, 6 / BrickSupper.ppm));
        fixtureDef.filter.categoryBits = BrickSupper.PAD_BIT;
        fixtureDef.filter.maskBits = BrickSupper.ROOF_BIT | BrickSupper.BALL_BIT | BrickSupper.GROUND_BIT | BrickSupper.SIDE_BOUND_BIT;
        fixtureDef.shape = padShape;

        body.createFixture(fixtureDef).setUserData(this);


        setBounds(0, 0, 75 / BrickSupper.ppm, 16 / BrickSupper.ppm);
        setRegion(pad);
    }

    public void update(float dt){
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    public Boolean isBallOnPad() {
        return isBallOnPad;
    }

    public void setBallOnPad(Boolean ballOnPad) {
        isBallOnPad = ballOnPad;
    }
}
