package me.lancer.spineruntimesdemo.model;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Event;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.SkeletonRendererDebug;

public class SpineTest extends ApplicationAdapter {

    OrthographicCamera camera;
    PolygonSpriteBatch batch;
    SkeletonRenderer renderer;
    SkeletonRendererDebug debugRenderer;

    TextureAtlas atlas;
    Skeleton skeleton;
    AnimationState state;
    SkeletonJson json;

    TextureAtlas atlas1;
    Skeleton skeleton1;
    AnimationState state1;
    SkeletonJson json1;

    public void create() {
        camera = new OrthographicCamera();
        batch = new PolygonSpriteBatch();
        renderer = new SkeletonRenderer();
        renderer.setPremultipliedAlpha(true); // PMA results in correct blending without outlines.
        debugRenderer = new SkeletonRendererDebug();
        debugRenderer.setBoundingBoxes(false);
        debugRenderer.setRegionAttachments(false);

        createSpine();
        createSpine1();
    }

    private void createSpine() {
        atlas = new TextureAtlas(Gdx.files.internal("spine/skeleton.atlas"));
        json = new SkeletonJson(atlas); // This loads skeleton JSON data, which is stateless.
        json.setScale(0.7f); // Load the skeleton at 60% the size it was in Spine.
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("spine/skeleton.json"));

        skeleton = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
        skeleton.setPosition(175, 50);
//        skeleton.setPosition(500, 500);

        AnimationStateData stateData = new AnimationStateData(skeletonData); // Defines mixing (crossfading) between animations.
//        stateData.setMix("run", "jump", 0.2f);
//        stateData.setMix("jump", "run", 0.2f);

        state = new AnimationState(stateData); // Holds the animation state for a skeleton (current animation, time, etc).
        state.setTimeScale(1.0f); // Slow all animations down to 50% speed.

        // Queue animations on track 0.
        state.setAnimation(0, "animation", true);
        state.addListener(new AnimationState.AnimationStateListener() {
            @Override
            public void start(AnimationState.TrackEntry entry) {
            }

            @Override
            public void interrupt(AnimationState.TrackEntry entry) {

            }

            @Override
            public void end(AnimationState.TrackEntry entry) {

            }

            @Override
            public void dispose(AnimationState.TrackEntry entry) {

            }

            @Override
            public void complete(AnimationState.TrackEntry entry) {

            }

            @Override
            public void event(AnimationState.TrackEntry entry, Event event) {

            }
        });

//        state.addAnimation(0, "animation", true, 0); // Run after the jump.
    }

    private void createSpine1() {
        atlas1 = new TextureAtlas(Gdx.files.internal("pipi/pipi_speak.atlas"));
        json1 = new SkeletonJson(atlas1); // This loads skeleton JSON data, which is stateless.
        json1.setScale(0.7f); // Load the skeleton at 60% the size it was in Spine.
        SkeletonData skeletonData = json1.readSkeletonData(Gdx.files.internal("pipi/pipi_speak.json"));

        skeleton1 = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
//        skeleton.setPosition(175, 50);
        skeleton1.setPosition(175 * 2, 120 * 2);

        AnimationStateData stateData = new AnimationStateData(skeletonData); // Defines mixing (crossfading) between animations.
//        stateData.setMix("run", "jump", 0.2f);
//        stateData.setMix("jump", "run", 0.2f);

        state1 = new AnimationState(stateData); // Holds the animation state for a skeleton (current animation, time, etc).
        state1.setTimeScale(1.0f); // Slow all animations down to 50% speed.

        // Queue animations on track 0.
        state1.setAnimation(0, "happy", true);

//        state.addAnimation(0, "animation", true, 0); // Run after the jump.
    }

    @Override
    public void render() {
        state.update(Gdx.graphics.getDeltaTime()); // Update the animation time.
        state1.update(Gdx.graphics.getDeltaTime()); // Update the animation time.

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glClearColor(0, 0, 0, 0);

        state.apply(skeleton); // Poses skeleton using current animations. This sets the bones' local SRT.
        skeleton.updateWorldTransform(); // Uses the bones' local SRT to compute their world SRT.
        state1.apply(skeleton1); // Poses skeleton using current animations. This sets the bones' local SRT.
        skeleton1.updateWorldTransform(); // Uses the bones' local SRT to compute their world SRT.

        // Configure the camera, SpriteBatch, and SkeletonRendererDebug.
        camera.update();
        batch.getProjectionMatrix().set(camera.combined);
        debugRenderer.getShapeRenderer().setProjectionMatrix(camera.combined);

        batch.begin();
        renderer.draw(batch, skeleton); // Draw the skeleton images.
        renderer.draw(batch, skeleton1); // Draw the skeleton images.
        batch.end();

//        debugRenderer.draw(skeleton); // Draw debug lines.
    }

    public void resize(int width, int height) {
        camera.setToOrtho(false); // Update camera with new size.
    }

    public void dispose() {
        atlas.dispose();
        atlas1.dispose();
    }

    public void setAnimate() {
        setAnimate(state, "animation");
        setAnimate(state1, "happy");
//        setAnimate("run");
    }

    public void setAnimate(AnimationState state, String animate) {
        state.addAnimation(0, animate, true, 0);
    }

    public void zoomBig() {
        camera.zoom = 0.5f;
    }

    public void zoomSmall() {
        camera.zoom = 1f;
    }
}
