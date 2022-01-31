package no.sandramoen.ggj2022oslo.screens.gameplay

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import no.sandramoen.ggj2022oslo.actors.Overlay
import no.sandramoen.ggj2022oslo.utils.BaseActor
import no.sandramoen.ggj2022oslo.utils.BaseGame
import no.sandramoen.ggj2022oslo.utils.GameUtils

class Level1: BaseLevelScreen("level1") {

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.R || keycode == Keys.BACK) {
            Gdx.app.error(tag, "new level")
            GameUtils.stopAllMusic()
            Overlay(0f, 0f, mainStage, comingIn = false)
            val temp = BaseActor(0f, 0f, mainStage)
            temp.addAction(
                Actions.sequence(
                Actions.delay(.5f),
                Actions.run {
                    if (lostTheGame) BaseGame.setActiveScreen(Level1())
                    else BaseGame.setActiveScreen(Level2())
                }
            ))
        }
        return super.keyDown(keycode)
    }

    override fun cameraSetup() {
        super.cameraSetup()

        val camera = mainStage.camera as OrthographicCamera
        if (Gdx.app.type == Application.ApplicationType.Android) {
            camera.zoom = .4f // higher number = zoom out
        } else {
            camera.zoom = .7f // higher number = zoom out
        }
        camera.position.x = 350f // higher number = world to the left
        camera.position.y = 500f
        camera.update()
    }
}
