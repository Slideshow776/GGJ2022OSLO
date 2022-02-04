package no.sandramoen.ggj2022oslo.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

class GameUtils {
    companion object {
        private val tag = "GameUtils.kt"

        fun isTouchDownEvent(event: Event): Boolean { // Custom type checker
            return event is InputEvent && event.type == InputEvent.Type.touchDown
        }

        fun saveGameState() {
            BaseGame.prefs!!.putBoolean("loadPersonalParameters", true)
            BaseGame.prefs!!.putBoolean("googlePlayServices", BaseGame.isGPS)
            BaseGame.prefs!!.putFloat("musicVolume", BaseGame.musicVolume)
            BaseGame.prefs!!.putFloat("soundVolume", BaseGame.soundVolume)
            BaseGame.prefs!!.putInteger("highScore", BaseGame.highScore)
            BaseGame.prefs!!.flush()
        }

        fun loadGameState() {
            BaseGame.prefs = Gdx.app.getPreferences("binaryNonBinaryGameState")
            BaseGame.loadPersonalParameters = BaseGame.prefs!!.getBoolean("loadPersonalParameters")
            BaseGame.isGPS = BaseGame.prefs!!.getBoolean("googlePlayServices")
            BaseGame.musicVolume = BaseGame.prefs!!.getFloat("musicVolume")
            BaseGame.soundVolume = BaseGame.prefs!!.getFloat("soundVolume")
            BaseGame.highScore = BaseGame.prefs!!.getInteger("highScore")
        }

        fun stopAllMusic() {
            BaseGame.levelMusic!!.stop()
            BaseGame.introMusic!!.stop()
            BaseGame.stepsRMusic!!.stop()
            BaseGame.stepsLMusic!!.stop()
        }

        fun initShaderProgram(vertexShader: String?, fragmentShader: String?): ShaderProgram {
            ShaderProgram.pedantic = false
            val shaderProgram = ShaderProgram(vertexShader, fragmentShader)
            if (!shaderProgram!!.isCompiled)
                Gdx.app.error(tag, "Couldn't compile shader: " + shaderProgram.log)
            return shaderProgram
        }

        fun setMusicVolume(volume: Float) {
            if (volume > 1f || volume < 0f)
                Gdx.app.error(tag, "setMusicVolume()'s parameter needs to be within [0-1]. Volume is: $volume")
            BaseGame.musicVolume = volume
            BaseGame.levelMusic!!.volume = BaseGame.musicVolume
        }

        fun playAndLoopMusic(music: Music?, volume: Float = BaseGame.musicVolume) {
            music!!.play()
            music!!.volume = volume
            music!!.isLooping = true
        }

        fun addTextButtonEnterExitEffect(textButton: TextButton, enterColor: Color = BaseGame.lightPink, exitColor: Color = Color.WHITE) {
            textButton.addListener(object : ClickListener() {
                override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Actor?) {
                    textButton.label.color = enterColor
                    super.enter(event, x, y, pointer, fromActor)
                }

                override fun exit(event: InputEvent?, x: Float, y: Float, pointer: Int, toActor: Actor?) {
                    textButton.label.color = exitColor
                    super.exit(event, x, y, pointer, toActor)
                }
            })
        }

        fun addWidgetEnterExitEffect(widget: Widget, enter: Color = BaseGame.lightPink, exit: Color = Color.WHITE) {
            widget.addListener(object : ClickListener() {
                override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Actor?) {
                    widget.color = enter
                    super.enter(event, x, y, pointer, fromActor)
                }

                override fun exit(event: InputEvent?, x: Float, y: Float, pointer: Int, toActor: Actor?) {
                    widget.color = exit
                    super.exit(event, x, y, pointer, toActor)
                }
            })
        }

        fun normalizeValues(x: Float, min: Float, max: Float): Float {
            val dividend = x - min
            val divisor = max - min
            return dividend / divisor
        }

        fun pulseWidget(actor: Actor, lowestAlpha: Float = .7f, duration: Float = 1f) {
            actor.addAction(Actions.forever(Actions.sequence(
                    Actions.alpha(lowestAlpha, duration / 2),
                    Actions.alpha(1f, duration / 2)
            )))
        }
    }
}
