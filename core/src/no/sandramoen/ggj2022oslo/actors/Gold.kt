package no.sandramoen.ggj2022oslo.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import no.sandramoen.ggj2022oslo.utils.BaseActor
import no.sandramoen.ggj2022oslo.utils.BaseGame
import no.sandramoen.ggj2022oslo.utils.GameUtils

class Gold(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    private val tag = "Gold"
    private var shaderProgram: ShaderProgram
    private var time = 0f

    init {
        loadImage("goldenCup")
        setPixelSizeToRelativeSize(32)
        setBoundaryRectangle()
        shaderProgram = GameUtils.initShaderProgram(BaseGame.defaultShader, BaseGame.glowShader)
        /*debug = true*/
    }

    override fun act(dt: Float) {
        super.act(dt)
        time += dt * 2f
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        if (BaseGame.enableCustomShaders) {
            try {
                drawWithShader(batch, parentAlpha)
            } catch (error: Throwable) {
                super.draw(batch, parentAlpha)
            }
        } else {
            super.draw(batch, parentAlpha)
        }
    }

    private fun drawWithShader(batch: Batch, parentAlpha: Float) {
        batch.shader = shaderProgram
        shaderProgram!!.setUniformf("u_time", time)
        shaderProgram!!.setUniformf("u_imageSize", Vector2(width, height))
        shaderProgram!!.setUniformi("u_glowRadius", 1)
        super.draw(batch, parentAlpha)
        batch.shader = null
    }
}
