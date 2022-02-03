package no.sandramoen.ggj2022oslo.screens.shell

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import no.sandramoen.ggj2022oslo.actors.Background
import no.sandramoen.ggj2022oslo.actors.Overlay
import no.sandramoen.ggj2022oslo.screens.gameplay.Level1
import no.sandramoen.ggj2022oslo.utils.BaseActor
import no.sandramoen.ggj2022oslo.utils.BaseGame
import no.sandramoen.ggj2022oslo.utils.BaseScreen
import no.sandramoen.ggj2022oslo.utils.GameUtils

class ScoreScreen(var score: Int) : BaseScreen() {
    override fun initialize() {
        // score
        if (score > BaseGame.highScore) {
            BaseGame.highScore = score
            GameUtils.saveGameState()
        }

        // miscellaneous
        Background(mainStage)
        cameraSetup()
        uiSetup()
    }

    override fun update(dt: Float) {}

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        return false
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.R || keycode == Input.Keys.BACK) {
            GameUtils.stopAllMusic()
            Overlay(0f, 0f, mainStage, comingIn = false)
            BaseActor(0f, 0f, mainStage).addAction(
                Actions.sequence(
                    Actions.delay(.5f),
                    Actions.run { BaseGame.setActiveScreen(MenuScreen()) }
                ))
        }
        return super.keyDown(keycode)
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

    private fun uiSetup() {
        val highScoreLabel = Label("High Score: ${BaseGame.highScore}", BaseGame.labelStyle)
        highScoreLabel.setFontScale(.85f)
        highScoreLabel.setAlignment(Align.center)
        highScoreLabel.color = Color.GOLD
        uiTable.add(highScoreLabel).row()

        val padding = Gdx.graphics.height * .02f
        val scoreLabel = Label("Score: $score", BaseGame.labelStyle)
        scoreLabel.setFontScale(.65f)
        scoreLabel.setAlignment(Align.center)
        if (score != BaseGame.highScore)
            uiTable.add(scoreLabel).padTop(padding).row()

        val restartLabel = Label("", BaseGame.labelStyle)
        if (Gdx.app.type == Application.ApplicationType.Android) restartLabel.setText("Touch to restart")
        else restartLabel.setText("press 'R' to restart")
        restartLabel.setFontScale(.5f)
        restartLabel.setAlignment(Align.center)
        restartLabel.color = Color.GRAY
        GameUtils.pulseWidget(restartLabel)
        uiTable.add(restartLabel).padTop(padding).row()
    }
}