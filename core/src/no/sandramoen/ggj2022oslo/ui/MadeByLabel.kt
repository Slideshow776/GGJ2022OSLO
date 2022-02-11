package no.sandramoen.ggj2022oslo.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import no.sandramoen.ggj2022oslo.utils.BaseGame
import no.sandramoen.ggj2022oslo.utils.GameUtils

class MadeByLabel {
    var label: Label
    init {
        label = Label("Made by Sandra Moen 2022", BaseGame.labelStyle)
        label.setFontScale(.4f)
        label.setAlignment(Align.center)
        label.color = Color.GRAY
        label.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                super.clicked(event, x, y)
                BaseGame.clickSound!!.play(BaseGame.soundVolume)
                label.addAction(
                    Actions.sequence(
                        Actions.delay(.5f),
                        Actions.run { Gdx.net.openURI("https://sandramoen.no"); }
                    ))
            }
        })
        GameUtils.addWidgetEnterExitEffect(label, Color.GRAY, Color.DARK_GRAY)
        // label.debug = true
    }
}
