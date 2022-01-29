package no.sandramoen.ggj2022oslo.screens.gameplay

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import no.sandramoen.ggj2022oslo.utils.BaseGame

class Level1: BaseLevelScreen("level1") {

    override fun keyDown(keycode: Int): Boolean {
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            BaseGame.setActiveScreen(Level2())
        }
        return super.keyDown(keycode)
    }
}
