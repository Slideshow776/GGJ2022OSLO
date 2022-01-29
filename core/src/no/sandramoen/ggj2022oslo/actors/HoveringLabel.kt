package no.sandramoen.ggj2022oslo.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import no.sandramoen.ggj2022oslo.utils.BaseActor
import no.sandramoen.ggj2022oslo.utils.BaseGame

class HoveringLabel(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        val label = Label("I miss you!", BaseGame.labelStyle)
        label.setFontScale(.24f)
        label.color = Color.PINK
        println("${x}, ${label.prefWidth} ${x - label.prefWidth}")
        setPosition(x - label.prefWidth / 2, y)
        addActor(label)

        addAction(
            Actions.parallel(
                Actions.fadeOut(5f),
                Actions.moveBy(50f, 100f, 5f)
            )
        )
    }
}
