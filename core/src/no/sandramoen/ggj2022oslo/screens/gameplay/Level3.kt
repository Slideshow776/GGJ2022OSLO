package no.sandramoen.ggj2022oslo.screens.gameplay

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import no.sandramoen.ggj2022oslo.actors.Gold
import no.sandramoen.ggj2022oslo.actors.Overlay
import no.sandramoen.ggj2022oslo.utils.BaseActor
import no.sandramoen.ggj2022oslo.utils.BaseGame
import no.sandramoen.ggj2022oslo.utils.GameUtils

class Level3(private var incomingScore: Int) : BaseLevelScreen("level3", incomingScore) {

    override fun initialize() {
        super.initialize()
        scoreLabel.addListener(object : ActorGestureListener() {
            override fun tap(event: InputEvent?, x: Float, y: Float, count: Int, button: Int) {
                levelAttemptOver()
            }
        })

        man.reversedHorizontal = true
        woman.reversedHorizontal = true

        man.reversedVertical = true
        woman.reversedVertical = true
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.R) levelAttemptOver()
        return super.keyDown(keycode)
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (gameOver) levelAttemptOver()
        return super.touchDown(screenX, screenY, pointer, button)
    }

    override fun cameraSetup() {
        super.cameraSetup()

        val camera = mainStage.camera as OrthographicCamera
        if (Gdx.app.type == Application.ApplicationType.Android) {
            camera.zoom = .47f // higher number = zoom out
        } else {
            camera.zoom = .81f // higher number = zoom out
        }
        camera.position.x = 478f // higher number = world to the left
        camera.position.y = 500f
        camera.update()
    }

    private fun levelAttemptOver() {
        changingScreen = true
        GameUtils.stopAllMusic()
        Overlay(0f, 0f, mainStage, comingIn = false)
        if (!completedTheGame)
            scoreLabel.setText("Score: 0")
        val temp = BaseActor(0f, 0f, mainStage)
        temp.addAction(
            Actions.sequence(
                Actions.delay(.5f),
                Actions.run {
                    if (lostTheGame) BaseGame.setActiveScreen(Level3(incomingScore))
                    else {
                        if (completedTheGame)
                            BaseGame.setActiveScreen(Level4(score))
                        else
                            BaseGame.setActiveScreen(Level4(0))
                    }
                }
            ))
    }
}
