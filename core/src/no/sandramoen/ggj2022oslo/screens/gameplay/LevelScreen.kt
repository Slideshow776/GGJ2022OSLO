package no.sandramoen.ggj2022oslo.screens.gameplay

import no.sandramoen.ggj2022oslo.actors.Vignette
import no.sandramoen.ggj2022oslo.utils.BaseScreen

open class LevelScreen : BaseScreen() {

    override fun initialize() {
        Vignette(mainStage)
    }

    override fun update(dt: Float) {}
}
