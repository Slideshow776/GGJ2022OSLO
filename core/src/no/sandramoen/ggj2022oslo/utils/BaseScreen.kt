package no.sandramoen.ggj2022oslo.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.StretchViewport
import no.sandramoen.ggj2022oslo.actors.TilemapActor

abstract class BaseScreen : Screen, InputProcessor {
    protected var mainStage: Stage
    protected var uiStage: Stage
    protected var uiTable: Table
    protected var camera: OrthographicCamera

    init {
        mainStage = Stage()
        uiStage = Stage()

        uiTable = Table()
        uiTable.setFillParent(true)
        uiStage.addActor(uiTable)

        camera = mainStage.camera as OrthographicCamera

        mainStage.viewport = StretchViewport(BaseGame.WORLD_WIDTH, BaseGame.WORLD_HEIGHT, camera)
        mainStage.viewport.apply()

        camera.position.set(0f, 0f, 0f)
    }

    abstract fun initialize()
    abstract fun update(dt: Float)

    override fun render(dt: Float) {
        uiStage.act(dt)
        mainStage.act(dt)

        update(dt)

        camera.update()

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // this.mainStage.batch.projectionMatrix = camera.combined

        mainStage.draw()
        uiStage.draw()
    }

    override fun show() {
        val im: InputMultiplexer = Gdx.input.inputProcessor as InputMultiplexer
        im.addProcessor(this)
        im.addProcessor(uiStage)
        im.addProcessor(mainStage)
    }

    override fun hide() {
        val im: InputMultiplexer = Gdx.input.inputProcessor as InputMultiplexer
        im.removeProcessor(this)
        im.removeProcessor(uiStage)
        im.removeProcessor(mainStage)
    }

    override fun resize(width: Int, height: Int) {
        mainStage.viewport.update(width, height)
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f)
    }

    override fun pause() {}
    override fun resume() {}
    override fun dispose() {}

    override fun keyDown(keycode: Int): Boolean { return false }
    override fun keyUp(keycode: Int): Boolean { return false }
    override fun keyTyped(character: Char): Boolean { return false }
    override fun mouseMoved(screenX: Int, screenY: Int): Boolean { return false }
    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean { return false }
    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean { return false }
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean { return false }
    fun isTouchDownEvent(e: Event): Boolean { return e is InputEvent && e.type == Type.touchDown }
}
