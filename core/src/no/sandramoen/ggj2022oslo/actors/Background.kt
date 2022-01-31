package no.sandramoen.ggj2022oslo.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import no.sandramoen.ggj2022oslo.utils.BaseActor

class Background(s: Stage) : BaseActor(0f, 0f, s) {
    private lateinit var image00: BaseActor
    private lateinit var image01: BaseActor

    private lateinit var image10: BaseActor
    private lateinit var image11: BaseActor

    private lateinit var image20: BaseActor
    private lateinit var image21: BaseActor

    private lateinit var image30: BaseActor
    private lateinit var image31: BaseActor

    private lateinit var image40: BaseActor
    private lateinit var image41: BaseActor

    private lateinit var image50: BaseActor
    private lateinit var image51: BaseActor

    private val speedControl = 1.0f
    private val layer0Speed = .1f * speedControl
    private val layer1Speed = .2f * speedControl
    private val layer2Speed = .3f * speedControl
    private val layer3Speed = .4f * speedControl
    private val layer4Speed = .5f * speedControl
    private val layer5Speed = .6f * speedControl

    init {
        layer0Setup(s)
        layer1Setup(s)
        layer2Setup(s)
        layer3Setup(s)
        layer4Setup(s)
        layer5Setup(s)
    }

    override fun act(dt: Float) {
        super.act(dt)
        layerAct(image00, image01, layer0Speed)
        layerAct(image10, image11, layer1Speed)
        layerAct(image20, image21, layer2Speed)
        layerAct(image30, image31, layer3Speed)
        layerAct(image40, image41, layer4Speed)
        layerAct(image50, image51, layer5Speed)
    }

    private fun layer0Setup(s: Stage) {
        image00 = BaseActor(0f, 0f, s)
        image00.loadImage("space0")
        image00.scaleY = 1.1f

        image01 = BaseActor(image00.width, 0f, s)
        image01.loadImage("space0")
        image01.scaleX = 1.1f // overlapping hack
        image01.scaleY = 1.1f
    }

    private fun layer1Setup(s: Stage) {
        image10 = BaseActor(0f, 0f, s)
        image10.loadImage("space1")

        image11 = BaseActor(image10.width, 0f, s)
        image11.loadImage("space1")
        image11.scaleX = 1.1f // overlapping hack
        image11.x = Gdx.graphics.width.toFloat()
    }

    private fun layer2Setup(s: Stage) {
        image20 = BaseActor(0f, 0f, s)
        image20.loadImage("space2")

        image21 = BaseActor(image20.width, 0f, s)
        image21.loadImage("space2")
        image21.scaleX = 1.1f // overlapping hack
        image21.x = Gdx.graphics.width.toFloat()
    }

    private fun layer3Setup(s: Stage) {
        image30 = BaseActor(0f, 0f, s)
        image30.loadImage("space3")

        image31 = BaseActor(image30.width, 0f, s)
        image31.loadImage("space3")
        image31.scaleX = 1.1f // overlapping hack
        image31.x = Gdx.graphics.width.toFloat()
    }

    private fun layer4Setup(s: Stage) {
        image40 = BaseActor(0f, 0f, s)
        image40.loadImage("space4")

        image41 = BaseActor(image20.width, 0f, s)
        image41.loadImage("space4")
        image41.scaleX = 1.1f // overlapping hack
        image41.x = Gdx.graphics.width.toFloat()
    }

    private fun layer5Setup(s: Stage) {
        image50 = BaseActor(0f, 0f, s)
        image50.loadImage("space5")

        image51 = BaseActor(image20.width, 0f, s)
        image51.loadImage("space5")
        image51.scaleX = 1.1f // overlapping hack
        image51.x = Gdx.graphics.width.toFloat()
    }

    private fun layerAct(actor1: BaseActor, actor2: BaseActor, speed: Float) {
        actor1.x -= speed
        actor2.x -= speed

        if (actor1.x + actor1.width <= 0)
            actor1.x = Gdx.graphics.width.toFloat()

        if (actor2.x + actor2.width <= 0)
            actor2.x = Gdx.graphics.width.toFloat()
    }
}
