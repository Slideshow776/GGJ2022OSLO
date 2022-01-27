package no.sandramoen.ggj2022oslo.utils

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import no.sandramoen.ggj2022oslo.screens.gameplay.LevelScreen

class Transition(s: Stage) : BaseActor(0f, 0f, s) {
    private var tag: String = "Transition"
    var duration = .125f

    init {
        loadImage("whitePixel_BIG")
        setSize(BaseGame.WORLD_WIDTH +2, BaseGame.WORLD_HEIGHT +2)
        setPosition(-1f, -1f)
        color = Color.BLACK
        fadeOut()
    }

    fun fadeOut () { addAction(Actions.fadeOut(duration)) }
    fun fadeIn() { addAction(Actions.fadeIn(duration)) }

    fun fadeInToMenuScreen() {
        addAction(Actions.sequence(
                Actions.fadeIn(duration),
                Actions.run {
                    BaseGame.setActiveScreen(LevelScreen())
                }
        ))
    }
}
