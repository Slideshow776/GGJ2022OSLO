package no.sandramoen.ggj2022oslo.actors

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Align
import no.sandramoen.ggj2022oslo.utils.BaseActor
import no.sandramoen.ggj2022oslo.utils.BaseGame

class LazerBeam(x: Float, y: Float, s: Stage, val comingDown: Boolean = true) : BaseActor(x, y, s) {
    var animationFinished = false
    private val targetY = y
    private val lazerSpeed = 50f

    init {
        loadImage("lazerBeam")
        scaleBy(.1f, 20f)
        setOrigin(Align.bottom)
        if (comingDown)
            setPosition(x, y + 1_000f)
        BaseGame.lazerBeamSound!!.play(BaseGame.soundVolume, 1.5f, 0f)
    }

    override fun act(dt: Float) {
        super.act(dt)
        if (comingDown && y > targetY) {
            y -= lazerSpeed
        } else if (comingDown) {
            addAction(Actions.sequence(
                Actions.scaleTo(1f, 0f, .1f),
                Actions.run { animationFinished = true }
            ))
        } else { // going up
            y += lazerSpeed
            if (y > 1_000)
                remove()
        }
    }
}