package no.sandramoen.ggj2022oslo.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import no.sandramoen.ggj2022oslo.utils.BaseActor

class Vignette(s: Stage) : BaseActor(0f, 0f, s) {
    private val tag = "Vignette"

    init {
        loadImage("vignette")
        setSize(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    }
}
