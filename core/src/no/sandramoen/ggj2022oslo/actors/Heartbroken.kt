package no.sandramoen.ggj2022oslo.actors

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.Align
import no.sandramoen.ggj2022oslo.utils.BaseActor

class Heartbroken(x: Float, y: Float, s: Stage, woman: Boolean = true) : BaseActor(x, y, s) {
    init {
        loadImage("heartbroken")
        setPixelSizeToRelativeSize(32)
        setPosition(x, y)
        Align.center

        if (woman) flip()

        // animation
        val wobbleDuration = .5f
        val wobbleAmount = 2.5f
        val wobbleFrequency = .5f
        val originalRotation = 0f
        addAction(
            Actions.parallel(
                Actions.forever(
                    Actions.sequence( // wobbling
                        Actions.rotateBy(wobbleAmount, wobbleDuration),
                        Actions.rotateTo(originalRotation, wobbleDuration),
                        Actions.rotateBy(-wobbleAmount, wobbleDuration),
                        Actions.rotateTo(originalRotation, wobbleDuration),
                        Actions.rotateBy(wobbleAmount, wobbleDuration),
                        Actions.rotateTo(originalRotation, wobbleDuration),
                        Actions.rotateBy(-wobbleAmount, wobbleDuration),
                        Actions.rotateTo(originalRotation, wobbleDuration),
                        Actions.delay(wobbleFrequency)
                    )
                ),
                Actions.parallel(
                    Actions.fadeOut(5f),
                    Actions.moveBy(5f, 10f, 5f)
                )
            )

        )
    }
}
