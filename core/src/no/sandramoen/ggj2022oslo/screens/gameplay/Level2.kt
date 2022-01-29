package no.sandramoen.ggj2022oslo.screens.gameplay

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import no.sandramoen.ggj2022oslo.utils.BaseGame

class Level2: BaseLevelScreen("level2") {

    override fun initialize() {
        super.initialize()

        man.reversedHorizontal = true
        woman.reversedHorizontal = true

        man.reversedVertical = true
        woman.reversedVertical = true
    }

    override fun keyDown(keycode: Int): Boolean {
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            BaseGame.setActiveScreen(Level1())
        }
        return super.keyDown(keycode)
    }

    override fun cameraSetup() {
        super.cameraSetup()

        val temp = mainStage.camera as OrthographicCamera
        temp.zoom = .75f // higher number = zoom out
        temp.position.x = 480f // higher number = world to the left
        temp.position.y = 500f
        temp.update()
    }
}
