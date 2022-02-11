package no.sandramoen.ggj2022oslo.actors

import com.badlogic.gdx.scenes.scene2d.Stage
import no.sandramoen.ggj2022oslo.utils.BaseActor
import no.sandramoen.ggj2022oslo.utils.GameUtils

class Ground(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    val tag = "Ground"

    init {
        setPixelSizeToRelativeSize(64)
        setBoundaryRectangle()
        /*debug = true*/
    }
}
