package no.sandramoen.ggj2022oslo.screens.shell

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Event
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.addListener
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import no.sandramoen.ggj2022oslo.actors.ShockwaveBackground
import no.sandramoen.ggj2022oslo.screens.gameplay.Level1
import no.sandramoen.ggj2022oslo.utils.BaseActor
import no.sandramoen.ggj2022oslo.utils.BaseGame
import no.sandramoen.ggj2022oslo.utils.BaseScreen
import no.sandramoen.ggj2022oslo.utils.GameUtils

class SplashScreen : BaseScreen() {
    private lateinit var tag: String
    private lateinit var shockwaveBackground: ShockwaveBackground

    override fun initialize() {
        tag = "SplashScreen"

        // image with effect
        shockwaveBackground = ShockwaveBackground("images/excluded/splash.jpg", mainStage)

        // black overlay
        val background = BaseActor(0f, 0f, mainStage)
        background.loadImage("whitePixel_BIG")
        background.color = Color.BLACK
        background.touchable = Touchable.childrenOnly
        background.setSize(BaseGame.WORLD_WIDTH+2, BaseGame.WORLD_HEIGHT+2)
        background.setPosition(0f, 0f)
        var totalDurationInSeconds = 6f
        background.addAction(
            Actions.sequence(
                Actions.fadeIn(0f),
                Actions.fadeOut(totalDurationInSeconds / 4),
                Actions.run {
                    // google play services
                    if (Gdx.app.type == Application.ApplicationType.Android && BaseGame.isGPS && BaseGame.gps != null)
                        BaseGame.gps!!.signIn()
                },
                Actions.delay(totalDurationInSeconds / 4),
                Actions.fadeIn(totalDurationInSeconds / 4)
            )
        )
        background.addAction(Actions.after(Actions.run {
            dispose()
            // GameUtils.stopAllMusic()
            BaseGame.setActiveScreen(MenuScreen())
        }))
    }

    override fun update(dt: Float) {}

    override fun dispose() {
        super.dispose()
        shockwaveBackground.shaderProgram.dispose()
        shockwaveBackground.remove()
    }

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACKSPACE)
            Gdx.app.exit()
        return false
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean { return false }
}
