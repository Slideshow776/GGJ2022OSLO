package no.sandramoen.ggj2022oslo.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Align
import no.sandramoen.ggj2022oslo.actors.effects.HeartEffect
import no.sandramoen.ggj2022oslo.utils.BaseActor
import no.sandramoen.ggj2022oslo.utils.BaseGame
import no.sandramoen.ggj2022oslo.utils.GameUtils

class LazerBeam(x: Float, y: Float, s: Stage, val comingDown: Boolean = true) : BaseActor(x, y, s) {
    var animationFinished = false
    private val targetY = y
    private val lazerSpeed = 50f

    private var time = 0f

    init {
        loadImage("lazerBeam")
        scaleBy(.1f, 20f)
        setOrigin(Align.bottom)
        if (comingDown) {
            setPosition(x, y + 1_000f)
            addAction(Actions.sequence(
                Actions.run {
                    BaseGame.introMusic!!.play()
                    BaseGame.introMusic!!.volume = BaseGame.musicVolume
                },
                Actions.delay(2f),
                Actions.run {
                    GameUtils.playAndLoopMusic(BaseGame.levelMusic)
                }
            ))

            BaseGame.lazerBeamDownSound!!.play(BaseGame.soundVolume)
        } else {
            GameUtils.stopAllMusic()
            BaseGame.lazerBeamUpSound!!.play(BaseGame.soundVolume)
        }

        val duration = if (comingDown) .25f else .0f
        addAction(Actions.sequence(
            Actions.delay(duration),
            Actions.run {
                for (i in 0 until 20) {
                    val effect = HeartEffect()
                    effect.setScale(Gdx.graphics.height * .0004f)
                    effect.setPosition(x + 10f, targetY + 50f)
                    stage.addActor(effect)
                    effect.start()
                }
            }
        ))
    }

    override fun act(dt: Float) {
        super.act(dt)
        time += dt
        if (comingDown && y > targetY) {
            y -= lazerSpeed
        } else if (comingDown) {
            addAction(Actions.sequence(
                Actions.scaleTo(1f, 0f, .1f),
                Actions.run {
                    animationFinished = true
                    if (time >= 2.2f)
                        remove()
                }
            ))
        } else { // going up
            y += lazerSpeed
            if (y > 1_000)
                remove()
        }
    }
}
