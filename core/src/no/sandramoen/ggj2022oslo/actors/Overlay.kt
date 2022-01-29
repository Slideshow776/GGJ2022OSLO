package no.sandramoen.ggj2022oslo.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import no.sandramoen.ggj2022oslo.utils.BaseActor

class Overlay(x: Float, y: Float, s: Stage, comingIn: Boolean = true) : BaseActor(x, y, s) {
    init {
        loadImage("overlay")
        setSize(Gdx.graphics.width.toFloat() * 2f, Gdx.graphics.height.toFloat() * .9f)

        if (comingIn) {
            setPosition(x, y + 70)
            addAction(Actions.moveBy(-Gdx.graphics.width.toFloat() * 2, 0f, .25f))
        } else { // coming out
            setPosition(Gdx.graphics.width.toFloat(), y + 50)
            addAction(Actions.moveBy(-Gdx.graphics.width.toFloat() * 2, 0f, .9f))
        }
    }
}
