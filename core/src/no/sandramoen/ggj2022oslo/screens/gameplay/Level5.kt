package no.sandramoen.ggj2022oslo.screens.gameplay

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import no.sandramoen.ggj2022oslo.actors.Overlay
import no.sandramoen.ggj2022oslo.utils.BaseActor
import no.sandramoen.ggj2022oslo.utils.BaseGame
import no.sandramoen.ggj2022oslo.utils.GameUtils

class Level5(private var incomingScore: Int) : BaseLevelScreen("level5", incomingScore) {

    override fun initialize() {
        super.initialize()
        scoreLabel.addListener(object : ActorGestureListener() {
            override fun tap(event: InputEvent?, x: Float, y: Float, count: Int, button: Int) {
                levelAttemptOver()
            }
        })

        man.reversedHorizontal = true
        woman.reversedHorizontal = false

        man.reversedVertical = false
        woman.reversedVertical = false
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.R || keycode == Keys.ENTER) levelAttemptOver()
        return super.keyDown(keycode)
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (gameOver) levelAttemptOver()
        return super.touchDown(screenX, screenY, pointer, button)
    }

    override fun cameraSetup() {
        super.cameraSetup()

        val camera = mainStage.camera as OrthographicCamera
        camera.zoom = .6f // higher number = zoom out
        camera.update()
    }

    private fun levelAttemptOver() {
        changingScreen = true
        GameUtils.stopAllMusic()
        Overlay(0f, 0f, mainStage, comingIn = false)
        val temp = BaseActor(0f, 0f, mainStage)
        temp.addAction(
            Actions.sequence(
                Actions.delay(.5f),
                Actions.run {
                    if (lostTheGame) BaseGame.setActiveScreen(Level5(incomingScore))
                    else {
                        if (completedTheLevel) {
                            BaseGame.setActiveScreen(Level6(score))
                            if (Gdx.app.type == Application.ApplicationType.Android && BaseGame.gps != null && BaseGame.gps!!.isSignedIn())
                                BaseGame.gps!!.submitScore(score)
                        } else
                            BaseGame.setActiveScreen(Level6(incomingScore))
                    }
                }
            ))
    }
}
