package no.sandramoen.ggj2022oslo

import no.sandramoen.ggj2022oslo.screens.shell.SplashScreen
import no.sandramoen.ggj2022oslo.utils.BaseGame
import no.sandramoen.ggj2022oslo.utils.GooglePlayServices

class GGJ2022OsloGame(googlePlayServices: GooglePlayServices?) : BaseGame(googlePlayServices) {
    override fun create() {
        super.create()
        setActiveScreen(SplashScreen())
    }
}
