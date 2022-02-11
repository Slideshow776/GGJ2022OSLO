package no.sandramoen.ggj2022oslo.actors

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import no.sandramoen.ggj2022oslo.utils.BaseActor
import no.sandramoen.ggj2022oslo.utils.BaseGame

class Overlay(x: Float, y: Float, s: Stage, comingIn: Boolean = true) : BaseActor(x, y, s) {
    val tag = "Overlay"

    init {
        loadImage("overlay")
        setSize(BaseGame.WORLD_WIDTH, BaseGame.WORLD_HEIGHT)

        if (comingIn) {
            setPosition(0f, y)
            addAction(Actions.moveBy(-BaseGame.WORLD_WIDTH * 2, 0f, .9f))
        } else {
            setPosition(BaseGame.WORLD_WIDTH, y)
            addAction(Actions.moveBy(-BaseGame.WORLD_WIDTH * 2, 0f, .9f))
        }
    }
}
