package no.sandramoen.ggj2022oslo.actors

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import no.sandramoen.ggj2022oslo.utils.BaseActor
import no.sandramoen.ggj2022oslo.utils.BaseGame

class HoveringLabel(x: Float, y: Float, s: Stage, woman: Boolean = true) : BaseActor(x, y, s) {
    init {
        var text = ""
        when (MathUtils.random(1, 11)) {
            1 -> text = "I miss you..."
            2 -> text = "Where is my beloved?"
            3 -> text = "I feel lonely..."
            4 -> text = "I need companionship"
            5 -> text = "I want my Dearest!"
            5 -> text = "I want my Dearest!"
            6 -> text = "I feel sad without you"
            7 -> text = "I hope I see you again"
            8 -> text = "I long for you"
            9 -> text = "I yearn for you"
            10 -> text = "I can’t wait to see you again"
            11 -> text = "When will I see you again?"
        }
        val label = Label("$text", BaseGame.labelStyle)
        if (woman) label.color = Color.PINK
        else label.color = Color.CYAN

        val group = Group()
        group.addActor(label)
        if (Gdx.app.type == Application.ApplicationType.Android)
            group.setScale(.02f, .02f)
        else
            group.setScale(.035f, .035f)
        group.setPosition(-5f, 3f)
        addActor(group)

        addAction(
            Actions.parallel(
                Actions.sequence(
                    Actions.delay(1f),
                    Actions.fadeOut(4f)
                ),
                Actions.moveBy(5f, 10f, 5f)
            )
        )
    }
}
