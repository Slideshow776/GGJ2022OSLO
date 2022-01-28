package no.sandramoen.ggj2022oslo.screens.gameplay

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import no.sandramoen.ggj2022oslo.actors.*
import no.sandramoen.ggj2022oslo.utils.BaseActor
import no.sandramoen.ggj2022oslo.utils.BaseGame
import no.sandramoen.ggj2022oslo.utils.BaseScreen

open class LevelScreen : BaseScreen() {
    val tag = "LevelScreen"

    private lateinit var woman: Player
    private lateinit var man: Player

    private lateinit var winConditionLabel: Label
    private lateinit var restartLabel: Label

    override fun initialize() {
        val tma = TilemapActor("map/map.tmx", mainStage)

        for (obj in tma.getTileList("rock")) {
            val props = obj.properties
            Rock(props.get("x") as Float, props.get("y") as Float, mainStage)
        }

        for (obj in tma.getTileList("ground")) {
            val props = obj.properties
            Ground(props.get("x") as Float, props.get("y") as Float, mainStage)
        }

        woman = Player(460f, 200f, mainStage, true)
        man = Player(300f, 200f, mainStage, false)

        Win(460f, 700f, mainStage)
        Win(300f, 700f, mainStage)

        // camera
        val temp = mainStage.camera as OrthographicCamera
        temp.zoom = .6f
        temp.position.x = 380f
        temp.position.y = 500f
        temp.update()

        // music
        BaseGame.levelMusic!!.play()
        BaseGame.levelMusic!!.volume = BaseGame.musicVolume
        BaseGame.levelMusic!!.isLooping = true

        // UI
        winConditionLabel = Label("A winner is you!", BaseGame.labelStyle)
        winConditionLabel.setFontScale(.7f)
        winConditionLabel.setAlignment(Align.center)
        winConditionLabel.isVisible = false
        uiTable.add(winConditionLabel).row()

        restartLabel = Label("press 'R' to restart", BaseGame.labelStyle)
        restartLabel.setFontScale(.5f)
        restartLabel.color = Color.GRAY
        restartLabel.setAlignment(Align.center)
        restartLabel.isVisible = false
        uiTable.add(restartLabel).padTop(Gdx.graphics.height * .01f)
    }

    override fun update(dt: Float) {
        for (rockActor: BaseActor in BaseActor.getList(mainStage, Rock::class.java.canonicalName)) {
            for (player: BaseActor in BaseActor.getList(mainStage, Player::class.java.canonicalName)) {
                player.preventOverlap(rockActor)
            }
        }

        for (winkActor: BaseActor in BaseActor.getList(mainStage, Win::class.java.canonicalName)) {
            for (playerActor: BaseActor in BaseActor.getList(mainStage, Player::class.java.canonicalName)) {
                playerActor as Player
                if (playerActor.overlaps(winkActor) && playerActor.inPlay) {
                    playerActor.inPlay = false
                    BaseGame.winSound!!.play(BaseGame.soundVolume)
                    playerActor.addAction(Actions.sequence(
                        Actions.fadeOut(1f),
                        Actions.run { playerActor.remove() }
                    ))
                }
            }
        }

        for (playerActor: BaseActor in BaseActor.getList(mainStage, Player::class.java.canonicalName)) {
            var isTouchingGround = false
            for (groundActor: BaseActor in BaseActor.getList(mainStage, Ground::class.java.canonicalName)) {
                if (playerActor.overlaps(groundActor)) {
                    isTouchingGround = true
                    break
                }
            }
            if (!isTouchingGround) {
                playerActor.remove()
                BaseGame.hurtSound!!.play(BaseGame.soundVolume)
                winConditionLabel.isVisible = true
                restartLabel.isVisible = true
                winConditionLabel.setText("Game Over!")
            }
        }

        if (!woman.inPlay && !man.inPlay) {
            winConditionLabel.isVisible = true
            restartLabel.isVisible = true
        }
    }

    override fun keyDown(keycode: Int): Boolean {
        if (Gdx.input.isKeyPressed(Keys.R)) {
            BaseGame.setActiveScreen(LevelScreen())
        }
        return super.keyDown(keycode)
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        return false
    }
}
