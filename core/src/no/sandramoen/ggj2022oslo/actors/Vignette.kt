package no.sandramoen.ggj2022oslo.actors

import com.badlogic.gdx.scenes.scene2d.Stage
import no.sandramoen.ggj2022oslo.utils.BaseActor

class Vignette(s: Stage) : BaseActor(0f, 0f, s) {
    private val tag = "Vignette"

    init {
        loadImage("vignette")
        setPosition(0f, 0f)
        setSize(100f, 100f)
    }
}
