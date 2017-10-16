package com.yoavstezki.bricksupper.tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.yoavstezki.bricksupper.BrickSupper;
import com.yoavstezki.bricksupper.screens.PlayScreen;
import com.yoavstezki.bricksupper.spirites.Pad;
import com.yoavstezki.bricksupper.spirites.item.Ball;
import com.yoavstezki.bricksupper.spirites.tileObject.Brick;
import com.yoavstezki.bricksupper.spirites.tileObject.Roof;
import com.yoavstezki.bricksupper.spirites.tileObject.SideBound;

/**
 * Created by yoavs on 25/09/2017.
 */

public class WorldContactListener implements ContactListener {


    private PlayScreen screen;

    public WorldContactListener(PlayScreen screen) {

        this.screen = screen;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        int cDef = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;

        switch (cDef) {
            case BrickSupper.BALL_BIT | BrickSupper.BRICK_BIT: {
                if (fixtureA.getFilterData().categoryBits == BrickSupper.BALL_BIT) {
                    Ball ball = (Ball) fixtureA.getUserData();
                    Brick brick = (Brick) fixtureB.getUserData();
                    ball.hit();
                    brick.hit();
                } else {
                    Ball ball = (Ball) fixtureB.getUserData();
                    Brick brick = (Brick) fixtureA.getUserData();
                    ball.hit();
                    brick.hit();

                }
                break;
            }
            case BrickSupper.BALL_BIT | BrickSupper.ROOF_BIT: {
                if (fixtureA.getFilterData().categoryBits == BrickSupper.BALL_BIT) {
                    Ball ball = (Ball) fixtureA.getUserData();
                    Roof roof = (Roof) fixtureB.getUserData();
                    ball.setCurrentStat(Ball.State.DOWN);
                    Vector2 contactVector = contact.getWorldManifold().getPoints()[0];
                    Vector2 roofVector = roof.getVector();

                    float degree = (float) (Math.atan2(contactVector.y, contactVector.x) - Math.atan2(roofVector.y, roofVector.x));


                    ball.move();
                } else {
                    Ball ball = (Ball) fixtureB.getUserData();
                    Roof roof = (Roof) fixtureB.getUserData();
                    ball.setCurrentStat(Ball.State.DOWN);
                    Vector2 contactVector = contact.getWorldManifold().getPoints()[0];
                    Vector2 roofVector = roof.getVector();

                    float degree = (float) (Math.atan2(contactVector.y, contactVector.x) - Math.atan2(roofVector.y, roofVector.x));


                    ball.move();
                }
                break;
            }

            case BrickSupper.BALL_BIT | BrickSupper.SIDE_BOUND_BIT: {
                if (fixtureA.getFilterData().categoryBits == BrickSupper.BALL_BIT) {
                    Ball ball = (Ball) fixtureA.getUserData();
                    SideBound sideBound = (SideBound) fixtureB.getUserData();
                    Vector2 contactVector = contact.getWorldManifold().getPoints()[0];
                    Vector2 sideBoundVector = sideBound.getVector();

                    if (sideBound.isLeft()) {
                        float degree = (float) (Math.atan2(contactVector.y, contactVector.x) - Math.atan2(sideBoundVector.y, sideBoundVector.x));
                        ball.setHitPosVector(new Vector2(contactVector.x + degree, 0));
                    } else {
                        float degree = (float) (Math.atan2(sideBoundVector.y, sideBoundVector.x) - Math.atan2(contactVector.y, contactVector.x));
                        ball.setHitPosVector(new Vector2(degree - contactVector.x, 0));
                    }

                    ball.move();
                } else {
                    Ball ball = (Ball) fixtureB.getUserData();
                    SideBound sideBound = (SideBound) fixtureA.getUserData();
                    Vector2 contactVector = contact.getWorldManifold().getPoints()[0];
                    Vector2 sideBoundVector = sideBound.getVector();

                    if (sideBound.isLeft()) {
                        float degree = (float) (Math.atan2(contactVector.y, contactVector.x) - Math.atan2(sideBoundVector.y, sideBoundVector.x));
                        ball.setHitPosVector(new Vector2(contactVector.x + degree, 0));
                    } else {
                        float degree = (float) (Math.atan2(sideBoundVector.y, sideBoundVector.x) - Math.atan2(contactVector.y, contactVector.x));
                        ball.setHitPosVector(new Vector2(degree - contactVector.x, 0));
                    }

                    ball.move();
                }

                break;
            }

            case BrickSupper.BALL_BIT | BrickSupper.PAD_BIT: {
                if (screen.isDisconnected) {
                    if (fixtureA.getFilterData().categoryBits == BrickSupper.BALL_BIT) {
                        Ball ball = (Ball) fixtureA.getUserData();
                        Pad pad = (Pad) fixtureB.getUserData();
                        ball.setCurrentStat(Ball.State.UP);

                        float hitX = contact.getWorldManifold().getPoints()[0].x - pad.getX();
                        float hitY = contact.getWorldManifold().getPoints()[0].y - pad.getY();

                        float x = calcXHit(hitX / pad.getWidth());

                        ball.setHitPosVector(new Vector2(x, hitY));
                        ball.move();
                    } else {
                        Ball ball = (Ball) fixtureB.getUserData();
                        Pad pad = (Pad) fixtureA.getUserData();

                        ball.setCurrentStat(Ball.State.UP);
                        float hitX = contact.getWorldManifold().getPoints()[0].x - pad.getX();
                        float hitY = contact.getWorldManifold().getPoints()[0].y - pad.getY();

                        float x = calcXHit(hitX / pad.getWidth());

                        ball.setHitPosVector(new Vector2(x, hitY));
                        ball.move();
                    }
                }
                break;
            }

        }
    }

    private float calcXHit(float x) {
        if (x > 0.5) {
            return x + 0.5f;
        } else if (x < 0.5) {
            return x - 1.0f;
        }
        return 0;
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
