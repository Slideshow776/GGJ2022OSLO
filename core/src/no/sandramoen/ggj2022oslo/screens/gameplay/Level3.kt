package no.sandramoen.ggj2022oslo.screens.gameplay

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import no.sandramoen.ggj2022oslo.actors.Overlay
import no.sandramoen.ggj2022oslo.utils.BaseActor
import no.sandramoen.ggj2022oslo.utils.BaseGame
import no.sandramoen.ggj2022oslo.utils.GameUtils

class Level3 : BaseLevelScreen("level3") {

    override fun initialize() {
        super.initialize()

        man.reversedHorizontal = true
        woman.reversedHorizontal = true

        man.reversedVertical = true
        woman.reversedVertical = true
    }

    override fun keyDown(keycode: Int): Boolean {
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            GameUtils.stopAllMusic()
            Overlay(0f, 0f, mainStage, comingIn = false)
            val temp = BaseActor(0f, 0f, mainStage)
            temp.addAction(Actions.sequence(
                Actions.delay(.5f),
                Actions.run {
                    if (lostTheGame) BaseGame.setActiveScreen(Level3())
                    else BaseGame.setActiveScreen(Level4())
                }
            ))
        }
        return super.keyDown(keycode)
    }

    override fun cameraSetup() {
        super.cameraSetup()

        val temp = mainStage.camera as OrthographicCamera
        temp.zoom = .81f // higher number = zoom out
        temp.position.x = 478f // higher number = world to the left
        temp.position.y = 500f
        temp.update()
    }
}
