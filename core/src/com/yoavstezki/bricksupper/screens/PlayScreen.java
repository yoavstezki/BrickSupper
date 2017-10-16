package com.yoavstezki.bricksupper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.yoavstezki.bricksupper.BrickSupper;
import com.yoavstezki.bricksupper.scencs.Hud;
import com.yoavstezki.bricksupper.spirites.Pad;
import com.yoavstezki.bricksupper.spirites.item.Ball;
import com.yoavstezki.bricksupper.tools.WorldContactListener;
import com.yoavstezki.bricksupper.tools.WorldFactory;

/**
 * Created by yoavs on 18/09/2017.
 */

public class PlayScreen implements Screen {
    private final BrickSupper game;
    private TextureAtlas atlas;

    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;
    private World world;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private WorldFactory worldFactory;
    private Box2DDebugRenderer b2dr;
    private Ball ball;
    private Pad pad;
    RevoluteJointDef revoluteJointDef;
    private Joint joint;
    private Boolean needToDisconnect = false;
    public Boolean isDisconnected = false;

    public PlayScreen(BrickSupper game) {
        atlas = new TextureAtlas("Player_and_Ball.pack");
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(BrickSupper.V_WIDTH / BrickSupper.ppm, BrickSupper.V_HEIGHT / BrickSupper.ppm, gameCam);
        hud = new Hud(game.getBatch());

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1y.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / BrickSupper.ppm);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);

        b2dr = new Box2DDebugRenderer();

        worldFactory = new WorldFactory(this);

        pad = new Pad(this);

        ball = new Ball(this, true);


        revoluteJointDef = new RevoluteJointDef();
        connectBodies();
        joint = world.createJoint(revoluteJointDef);


        world.setContactListener(new WorldContactListener(this));
    }

    private void connectBodies() {
        revoluteJointDef.bodyA = pad.body;
        revoluteJointDef.bodyB = ball.body;
        revoluteJointDef.localAnchorA.set(0, 16 / BrickSupper.ppm);
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        b2dr.render(world, gameCam.combined);

        renderer.render();

        SpriteBatch batch = game.getBatch();
        batch.setProjectionMatrix(gameCam.combined);
        batch.begin();
        pad.draw(batch);
        ball.draw(batch);
        batch.end();

        Stage stage = hud.getStage();
        batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
    }

    public void update(float dt) {
        handleInput(dt);

        world.step(1 / 60f, 6, 2);

        if (!world.isLocked() && needToDisconnect) {
            Array<JointEdge> ballJointList = ball.body.getJointList();

            for (JointEdge edge : ballJointList) {
                world.destroyJoint(edge.joint);
            }

            Array<JointEdge> padJointList = pad.body.getJointList();

            for (JointEdge edge : padJointList) {
                world.destroyJoint(edge.joint);
            }

//            ball.body.applyLinearImpulse(new Vector2(0, 1f), ball.body.getWorldCenter(), true);

            needToDisconnect = false;
            isDisconnected = true;
        }

        if (isDisconnected) {
            ball.move();
        }

        pad.update(dt);

        ball.update(dt);

        gameCam.update();
        renderer.setView(gameCam);
    }

    public void handleInput(float dt) {

        Vector2 linearVelocity = pad.body.getLinearVelocity();
        Vector2 position = pad.body.getPosition();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && linearVelocity.x < 1.5) {
            pad.body.applyLinearImpulse(0.2f, 0, position.x, position.y, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && linearVelocity.x > -1.5) {
            pad.body.applyLinearImpulse(-0.2f, 0, position.x, position.y, true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            needToDisconnect = true;
            ball.setCurrentStat(Ball.State.UP);
        }

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    public Hud getHud() {
        return hud;
    }
}
