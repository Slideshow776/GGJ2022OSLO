package no.sandramoen.ggj2022oslo.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import no.sandramoen.ggj2022oslo.utils.BaseActor
import no.sandramoen.ggj2022oslo.utils.BaseGame

class Gold(x: Float, y: Float, s: Stage) : BaseActor(x, y, s) {
    private val tag = "Gold"
    private var vertexShader: String? = null
    private var fragmentShader: String? = null
    private var shaderProgram: ShaderProgram? = null
    private var time = 0f

    init {
        loadImage("goldenCup")

        // shaders
        vertexShader = BaseGame.defaultShader.toString()
        fragmentShader = BaseGame.glowShader.toString()
        shaderProgram = ShaderProgram(vertexShader, fragmentShader)

        // to detect errors in GPU compilation
        if (!shaderProgram!!.isCompiled) Gdx.app.error(tag, "Couldn't compile shader: " + shaderProgram!!.log)

        time = 0f
    }

    override fun act(dt: Float) {
        super.act(dt)
        time += dt * 2f
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        if (BaseGame.enableCustomShaders) {
            try {
                batch.shader = shaderProgram
                shaderProgram!!.setUniformf("u_time", time)
                shaderProgram!!.setUniformf("u_imageSize", Vector2(width, height))
                shaderProgram!!.setUniformi("u_glowRadius", 1)
                super.draw(batch, parentAlpha)
                batch.shader = null
            } catch (error: Throwable) {
                super.draw(batch, parentAlpha)
            }
        } else {
            super.draw(batch, parentAlpha)
        }
    }
}
