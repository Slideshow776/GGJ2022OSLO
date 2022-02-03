package no.sandramoen.ggj2022oslo

import no.sandramoen.ggj2022oslo.screens.gameplay.*
import no.sandramoen.ggj2022oslo.screens.shell.MenuScreen
import no.sandramoen.ggj2022oslo.screens.shell.ScoreScreen
import no.sandramoen.ggj2022oslo.screens.shell.SplashScreen
import no.sandramoen.ggj2022oslo.utils.BaseGame
import no.sandramoen.ggj2022oslo.utils.GooglePlayServices

class GGJ2022OsloGame(googlePlayServices: GooglePlayServices?) : BaseGame(googlePlayServices) {
    override fun create() {
        super.create()
        // setActiveScreen(Level1())
        // setActiveScreen(ScoreScreen(1_000))
        // setActiveScreen(MenuScreen())
        setActiveScreen(SplashScreen())
    }
}
