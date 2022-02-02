package no.sandramoen.ggj2022oslo

import no.sandramoen.ggj2022oslo.screens.gameplay.*
import no.sandramoen.ggj2022oslo.screens.shell.ScoreScreen
import no.sandramoen.ggj2022oslo.utils.BaseGame

class GGJ2022OsloGame() : BaseGame() {
    override fun create() {
        super.create()
        setActiveScreen(Level1())
        // setActiveScreen(ScoreScreen(1_000))
    }
}
