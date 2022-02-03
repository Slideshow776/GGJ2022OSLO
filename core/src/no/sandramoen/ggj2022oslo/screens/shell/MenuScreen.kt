package no.sandramoen.ggj2022oslo.screens.shell

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.utils.Align
import no.sandramoen.ggj2022oslo.actors.Background
import no.sandramoen.ggj2022oslo.screens.gameplay.Level1
import no.sandramoen.ggj2022oslo.ui.MadeByLabel
import no.sandramoen.ggj2022oslo.utils.BaseGame
import no.sandramoen.ggj2022oslo.utils.BaseScreen
import no.sandramoen.ggj2022oslo.utils.GameUtils

class MenuScreen : BaseScreen() {
    private lateinit var tag: String
    private lateinit var startButton: TextButton
    private lateinit var optionsButton: TextButton
    private lateinit var titleLabel1: Label
    private lateinit var titleLabel2: Label

    override fun initialize() {
        tag = "MenuScreen.kt"

        // audio
        // GameUtils.playAndLoopMusic(BaseGame.levelMusic)

        // background
        Background(mainStage)
        cameraSetup()

        // title
        titleLabel1 = Label("Binary", BaseGame.labelStyle)
        titleLabel1.setFontScale(1.25f)
        titleLabel1.setAlignment(Align.center)
        titleLabel2 = Label("Non-Binary", BaseGame.labelStyle)
        titleLabel2.setFontScale(1.25f)
        titleLabel2.setAlignment(Align.center)

        // menu
        val buttonScale = .8f
        startButton = TextButton("Start", BaseGame.textButtonStyle)
        startButton.label.setFontScale(buttonScale)
        startButton.addListener(object : ActorGestureListener() {
            override fun tap(event: InputEvent?, x: Float, y: Float, count: Int, button: Int) {
                BaseGame.clickSound!!.play(BaseGame.soundVolume)
                startButton.label.color = BaseGame.lightPink
                startGame()
            }
        })
        GameUtils.addTextButtonEnterExitEffect(startButton)

        optionsButton = TextButton("Options", BaseGame.textButtonStyle)
        optionsButton.label.setFontScale(buttonScale)
        optionsButton.addListener(object : ActorGestureListener() {
            override fun tap(event: InputEvent?, x: Float, y: Float, count: Int, button: Int) {
                BaseGame.clickSound!!.play(BaseGame.soundVolume)
                optionsButton.label.color = BaseGame.lightPink
                changeToOptionsScreen()
            }
        })
        GameUtils.addTextButtonEnterExitEffect(optionsButton)

        val buttonsTable = Table()
        buttonsTable.add(startButton).row()
        buttonsTable.add(optionsButton).row()

        // gui setup
        val padding = Gdx.graphics.height * .01f
        val table = Table()
        table.add(titleLabel1).padTop(padding * 10).row()
        table.add(titleLabel2).padTop(padding).row()
        table.add(buttonsTable).fillY().expandY().padBottom(padding * 12)
        table.row()
        table.add(MadeByLabel().label).padBottom(padding * 2).width(Gdx.graphics.width * 1f).height(Gdx.graphics.height * .05f)
        table.setFillParent(true)
        uiTable.add(table).fill().expand()
        /*table.debug = true*/
    }

    override fun update(dt: Float) {}
    override fun scrolled(amountX: Float, amountY: Float): Boolean { return false }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.BACK || keycode == Keys.ESCAPE || keycode == Keys.BACKSPACE)
            exitGame()
        else if (keycode == Keys.ENTER)
            startGame()
        return false
    }

    private fun startGame() {
        startButton.addAction(Actions.sequence(
                Actions.delay(.5f),
                Actions.run { BaseGame.setActiveScreen(Level1()) }
        ))
    }

    private fun changeToOptionsScreen() {
        optionsButton.addAction(Actions.sequence(
                Actions.delay(.5f),
                Actions.run { BaseGame.setActiveScreen(OptionsScreen()) }
        ))
    }

    private fun exitGame() {
        titleLabel1.addAction(Actions.sequence(
                Actions.run {
                    super.dispose()
                    Gdx.app.exit()
                }
        ))
    }

    private fun cameraSetup() {
        // camera
        val camera = mainStage.camera as OrthographicCamera
        if (Gdx.app.type == Application.ApplicationType.Android) {
            camera.zoom = .4f // higher number = zoom out
            camera.position.x = 350f // higher number = world to the left
            camera.position.y = 500f
        } else {
            camera.zoom = .7f // higher number = zoom out
        }
        camera.update()
    }
}
