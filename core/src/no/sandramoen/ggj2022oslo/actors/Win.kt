package no.sandramoen.ggj2022oslo.actors

import com.badlogic.gdx.scenes.scene2d.Stage
import no.sandramoen.ggj2022oslo.utils.BaseActor

class Win(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    init {
        loadImage("goldenCup")
    }
}
