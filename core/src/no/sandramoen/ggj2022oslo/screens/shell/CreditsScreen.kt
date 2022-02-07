package no.sandramoen.ggj2022oslo.screens.shell

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.utils.Align
import no.sandramoen.ggj2022oslo.utils.BaseGame
import no.sandramoen.ggj2022oslo.utils.BaseScreen
import no.sandramoen.ggj2022oslo.utils.GameUtils

class CreditsScreen : BaseScreen() {
    override fun initialize() {

        val title0 = Label("Game designer and coder", BaseGame.labelStyle)
        titleSetup(title0)
        val credit0 = Label("Sandra Moen", BaseGame.labelStyle)
        creditsSetup(credit0)

        val title1 = Label("Assisting coder", BaseGame.labelStyle)
        titleSetup(title1)
        val credit1 = Label("ITR", BaseGame.labelStyle)
        creditsSetup(credit1)

        val title2 = Label("Audio by", BaseGame.labelStyle)
        titleSetup(title2)
        val credit2 = Label("Jonathan Hildebrand", BaseGame.labelStyle)
        creditsSetup(credit2)

        val title3 = Label("Tile graphics by", BaseGame.labelStyle)
        titleSetup(title3)
        val credit3 = Label("Jennipher Karlsson\nJohannes Eliassen", BaseGame.labelStyle)
        creditsSetup(credit3)

        val backButton = TextButton("Back", BaseGame.textButtonStyle)
        backButton.align(Align.left)
        backButton.label.setAlignment(Align.left)
        backButton.addListener(object : ActorGestureListener() {
            override fun tap(event: InputEvent?, x: Float, y: Float, count: Int, button: Int) {
                BaseGame.clickSound!!.play(BaseGame.soundVolume)
                backButton.label.color = BaseGame.lightPink
                backButton.addAction(Actions.sequence(
                    Actions.delay(.5f),
                    Actions.run { BaseGame.setActiveScreen(MenuScreen()) }
                ))
            }
        })
        GameUtils.addTextButtonEnterExitEffect(backButton)

        val padding = Gdx.graphics.height * .035f
        uiTable.add(title0).row()
        uiTable.add(credit0).padTop(padding * .25f).row()
        uiTable.add(title1).padTop(padding).row()
        uiTable.add(credit1).padTop(padding * .25f).row()
        uiTable.add(title2).padTop(padding).row()
        uiTable.add(credit2).padTop(padding * .25f).row()
        uiTable.add(title3).padTop(padding).row()
        uiTable.add(credit3).padTop(padding * .25f).row()
        uiTable.add(backButton).padTop(padding * 3)
        /*uiTable.debug = true*/
    }

    override fun update(dt: Float) {}
    override fun scrolled(amountX: Float, amountY: Float): Boolean { return true }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACKSPACE)
            BaseGame.setActiveScreen(MenuScreen())
        return false
    }

    private fun creditsSetup(label: Label) {
        label.setFontScale(.5f)
        label.setAlignment(Align.center)
        label.color = Color.GOLD
    }

    private fun titleSetup(label: Label) {
        label.setFontScale(.4f)
        label.setAlignment(Align.center)
    }
}