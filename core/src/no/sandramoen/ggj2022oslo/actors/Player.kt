package no.sandramoen.ggj2022oslo.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.scenes.scene2d.Stage
import no.sandramoen.ggj2022oslo.utils.BaseActor

class Player(x: Float, y: Float, s: Stage, woman: Boolean = true) : BaseActor(x, y, s) {
    val tag = "Player"
    var inPlay = true

    init {
        if (woman) loadImage("woman")
        else loadImage("man")

        setAcceleration(800f)
        setMaxSpeed(100f)
        setDeceleration(800f)
    }

    override fun act(dt: Float) {
        super.act(dt)
        if (!inPlay) return

        // keys
        if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
            accelerateAtAngle(90f)
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
            accelerateAtAngle(0f)
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
            accelerateAtAngle(270f)
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
            accelerateAtAngle(180f)
        }

        applyPhysics(dt)
    }
}
