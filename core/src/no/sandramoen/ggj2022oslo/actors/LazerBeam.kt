package no.sandramoen.ggj2022oslo.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.badlogic.gdx.utils.Align
import no.sandramoen.ggj2022oslo.actors.effects.HeartEffect
import no.sandramoen.ggj2022oslo.utils.BaseActor
import no.sandramoen.ggj2022oslo.utils.BaseGame
import no.sandramoen.ggj2022oslo.utils.GameUtils

class LazerBeam(x: Float, y: Float, s: Stage, val goingDown: Boolean = true) : BaseActor(x, y, s) {
    var animationFinished = false

    private val targetY = y
    private val lazerSpeed = .2f
    private var time = 0f
    private var topOutOfScreen = 1_000f

    init {
        loadImage("lazerBeam")
        scaleBy(.1f, 20f)
        setOrigin(Align.bottom)
        startHeartEffects()

        if (goingDown) {
            setPosition(x - 7, topOutOfScreen)
            startMusic()
            BaseGame.lazerBeamDownSound!!.play(BaseGame.soundVolume)
        } else {
            GameUtils.stopAllMusic()
            BaseGame.lazerBeamUpSound!!.play(BaseGame.soundVolume)
        }
    }

    override fun act(dt: Float) {
        super.act(dt)
        time += dt

        if (goingDown) {
            addAction(Actions.sequence(
                goDownAndShrink(),
                removeWithDelay()
            ))
        } else {
            addAction(Actions.sequence(
                Actions.moveTo(x, topOutOfScreen),
                Actions.run { remove() }
            ))
        }
    }

    private fun removeWithDelay(): RunnableAction? {
        return Actions.run {
            animationFinished = true
            if (time >= 3f) remove()
        }
    }

    private fun goDownAndShrink(): SequenceAction? {
        return Actions.sequence(
            Actions.moveTo(x, targetY, lazerSpeed),
            Actions.scaleTo(1f, 0f, .1f)
        )
    }

    private fun startHeartEffects() {
        val duration = if (goingDown) .25f else .0f
        addAction(Actions.sequence(
            Actions.delay(duration),
            Actions.run { addHeartEffects() }
        ))
    }

    private fun addHeartEffects() {
        for (i in 0 until 20) {
            val effect = HeartEffect()
            effect.setScale(Gdx.graphics.height * .0004f)
            effect.setPosition(x + 10f, targetY + 20f)
            stage.addActor(effect)
            effect.start()
        }
    }

    private fun startMusic() {
        addAction(Actions.sequence(
            Actions.run { playIntroMusic() },
            Actions.delay(2f),
            Actions.run { playLevelMusic() }
        ))
    }

    private fun playLevelMusic() {
        GameUtils.playAndLoopMusic(BaseGame.levelMusic)
        GameUtils.playAndLoopMusic(BaseGame.stepsRMusic)
        GameUtils.playAndLoopMusic(BaseGame.stepsLMusic)
    }

    private fun playIntroMusic() {
        BaseGame.introMusic!!.play()
        BaseGame.introMusic!!.volume = BaseGame.musicVolume
    }
}
