package no.sandramoen.ggj2022oslo.screens.gameplay

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import no.sandramoen.ggj2022oslo.actors.Gold
import no.sandramoen.ggj2022oslo.actors.Overlay
import no.sandramoen.ggj2022oslo.utils.BaseActor
import no.sandramoen.ggj2022oslo.utils.BaseGame
import no.sandramoen.ggj2022oslo.utils.GameUtils

class Level2(private var incomingScore: Int) : BaseLevelScreen("level2", incomingScore) {

    override fun initialize() {
        super.initialize()

        man.reversedHorizontal = true
        woman.reversedHorizontal = true

        man.reversedVertical = true
        woman.reversedVertical = true
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.R || keycode == Keys.BACK) {
            changingScreen = true
            GameUtils.stopAllMusic()
            Overlay(0f, 0f, mainStage, comingIn = false)
            if (!completedTheGame)
                scoreLabel.setText("Score: $incomingScore")
            val temp = BaseActor(0f, 0f, mainStage)
            temp.addAction(
                Actions.sequence(
                    Actions.delay(.5f),
                    Actions.run {
                        if (lostTheGame) BaseGame.setActiveScreen(Level2(incomingScore))
                        else {
                            if (completedTheGame)
                                BaseGame.setActiveScreen(Level3(score))
                            else
                                BaseGame.setActiveScreen(Level3(incomingScore))
                        }
                    }
                ))
        }
        return super.keyDown(keycode)
    }

    override fun cameraSetup() {
        super.cameraSetup()

        val camera = mainStage.camera as OrthographicCamera
        if (Gdx.app.type == Application.ApplicationType.Android) {
            camera.zoom = .47f // higher number = zoom out
        } else {
            camera.zoom = .85f // higher number = zoom out
        }
        camera.position.x = 480f // higher number = world to the left
        camera.position.y = 500f
        camera.update()
    }
}
