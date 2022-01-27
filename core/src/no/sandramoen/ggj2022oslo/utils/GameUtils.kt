package no.sandramoen.ggj2022oslo.utils

import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

/**
 * Utility class of often used custom methods.
 */
class GameUtils {
    companion object {
        private val tag = "GameUtils.kt"

        /**
         * Detects if [event] is of type InputEvent.Type.touchDown
         */
        fun isTouchDownEvent(event: Event): Boolean { // Custom type checker
            return event is InputEvent && event.type == InputEvent.Type.touchDown
        }

        /**
         * Play, set volume and loop [music].
         */
        fun playAndLoopMusic(music: Music?) {
            music!!.play()
            music!!.volume = BaseGame.musicVolume
            music!!.isLooping = true
        }

        /**
         * Adds an [enter]/[exit] color effect on the [textButton].
         */
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

        /**
         * Adds an [enter]/[exit] color effect on the [widget].
         */
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

        /**
         * Returns the normalized value of [x] withing [min] and [max].
         */
        fun normalizeValues(x: Float, min: Float, max: Float): Float {
            val dividend = x - min
            val divisor = max - min
            return dividend / divisor
        }

        /**
         * Adds an action to pulse [actor] forever down to [lowestAlpha] with a total frequency of [duration].
         */
        fun pulseWidget(actor: Actor, lowestAlpha: Float, duration: Float) {
            actor.addAction(Actions.forever(Actions.sequence(
                    Actions.alpha(lowestAlpha, duration / 2),
                    Actions.alpha(1f, duration / 2)
            )))
        }
    }
}
