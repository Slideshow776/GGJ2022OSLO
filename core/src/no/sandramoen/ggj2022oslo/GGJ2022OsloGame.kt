package no.sandramoen.ggj2022oslo

import no.sandramoen.ggj2022oslo.screens.gameplay.LevelScreen
import no.sandramoen.ggj2022oslo.utils.BaseGame

class GGJ2022OsloGame() : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(LevelScreen())
    }
}