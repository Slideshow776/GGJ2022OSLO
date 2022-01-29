package no.sandramoen.ggj2022oslo

import no.sandramoen.ggj2022oslo.screens.gameplay.BaseLevelScreen
import no.sandramoen.ggj2022oslo.screens.gameplay.Level1
import no.sandramoen.ggj2022oslo.utils.BaseGame

class GGJ2022OsloGame() : BaseGame() {
    override fun create() {
        super.create()

        /*val level1 = "level1"
        val baseLevelScreen = BaseLevelScreen(level1)
        setActiveScreen(baseLevelScreen)*/
        setActiveScreen(Level1())
    }
}
