package com.yoavstezki.bricksupper.spirites.item;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.yoavstezki.bricksupper.BrickSupper;
import com.yoavstezki.bricksupper.screens.PlayScreen;
import com.yoavstezki.bricksupper.spirites.Pad;

/**
 * Created by yoavs on 24/09/2017.
 */

public class Ball extends Sprite {

    public Body body;
    private World world;
    private TextureRegion ball;
    private Boolean isStart;
    private State currentState = State.STAND;
    private Vector2 hitPosVector;

    public Ball(PlayScreen screen, Boolean isStart) {
        this.world = screen.getWorld();
        this.ball = new TextureRegion(screen.getAtlas().findRegion("ball"));
        this.isStart = isStart;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / BrickSupper.ppm, 32 / BrickSupper.ppm);
        bodyDef.type = bodyDef.type.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / BrickSupper.ppm);

        fixtureDef.shape = shape;

        fixtureDef.filter.categoryBits = BrickSupper.BALL_BIT;
        fixtureDef.filter.maskBits = BrickSupper.PAD_BIT | BrickSupper.ROOF_BIT | BrickSupper.BRICK_BIT | BrickSupper.SIDE_BOUND_BIT;

        body.createFixture(fixtureDef).setUserData(this);

        setBounds(0, 0, 16 / BrickSupper.ppm, 16 / BrickSupper.ppm);
        setRegion(ball);
    }

    public void move() {
        Vector2 pos = body.getPosition();
        Vector2 linearVelocity = body.getLinearVelocity();

        if (currentState.equals(State.UP)) {
            if (linearVelocity.y >= 1.0f) {
                body.setLinearVelocity(linearVelocity.x, 1.0f);
            }

            if (hitPosVector != null) {
                body.applyLinearImpulse(hitPosVector.x, 1.0f, pos.x, pos.y, true);
                hitPosVector = null;
            } else {
                body.applyLinearImpulse(0, 1.0f, pos.x, pos.y, true);
            }

        } else if (currentState.equals(State.DOWN) || currentState.equals(State.STAND)) {
            if (linearVelocity.y <= -1.0f) {
                    body.setLinearVelocity(linearVelocity.x, -1.0f);
            }

            if (hitPosVector != null) {
                body.applyLinearImpulse(hitPosVector.x, -1.0f, pos.x, pos.y, true);
                hitPosVector = null;
            } else {
                body.applyLinearImpulse(0, -1.0f, pos.x, pos.y, true);
            }
        }
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State stat) {
        this.currentState = stat;
    }

    public void onPad(Pad pad) {

    }

    public void update(float dt) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    public Boolean isStart() {
        return isStart;
    }

    public void setStart(Boolean start) {
        isStart = start;
    }

    public void start(float dt) {
        setStart(true);
        update(dt);
    }

    public Vector2 getHitPosVector() {
        return hitPosVector;
    }

    public void setHitPosVector(Vector2 hitPosVector) {
        this.hitPosVector = hitPosVector;
    }

    public enum State {UP, DOWN, STAND}
}
