package no.sandramoen.ggj2022oslo.screens.gameplay

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import no.sandramoen.ggj2022oslo.actors.*
import no.sandramoen.ggj2022oslo.actors.effects.StarEffect
import no.sandramoen.ggj2022oslo.utils.BaseActor
import no.sandramoen.ggj2022oslo.utils.BaseGame
import no.sandramoen.ggj2022oslo.utils.BaseScreen
import no.sandramoen.ggj2022oslo.utils.GameUtils

open class BaseLevelScreen(var tiledLevel: String) : BaseScreen() {
    val tag = "LevelScreen"

    private lateinit var woman: Player
    private lateinit var man: Player
    private lateinit var lazerBeam: LazerBeam

    private lateinit var winConditionLabel: Label
    private lateinit var restartLabel: Label
    private lateinit var timeLabel: Label

    private var time: Int = 0
    private var timeTilGameOver: Int = 300
    private var timePassed: Float = 0f
    private var spawnBrokenHearts = true

    override fun initialize() {
        Space(mainStage)
        tiledSetup()
        cameraSetup()
        GameUtils.playAndLoopMusic(BaseGame.levelMusic)
        uiSetup()

        lazerBeam = LazerBeam(woman.x, woman.y, mainStage)
        woman.inPlay = false
        woman.isVisible = false
        man.isVisible = false
        man.inPlay = false

        woman.addAction(Actions.sequence(
            Actions.delay(2f),
            Actions.run {
                HoveringLabel(woman.x, woman.y, mainStage)
                HoveringLabel(man.x, man.y, mainStage)
            }
        ))
    }

    override fun update(dt: Float) {
        checkRockCollision()
        checkGoldPickup()
        checkIfPlayerIsOnGround()
        checkWinConditionAndCountTime(dt)

        handleLazerBeamComindDown()

        // broken hearts
        if (BaseActor.count(mainStage, Gold::class.java.canonicalName) == 0 && spawnBrokenHearts) {
            spawnBrokenHearts = false
            HoveringLabel(woman.x, woman.y, mainStage)
            HoveringLabel(man.x, man.y, mainStage)
            Heartbroken(woman.x, woman.y, mainStage)
            Heartbroken(man.x, man.y, mainStage)
        }
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        return false
    }

    private fun checkRockCollision() {
        for (rockActor: BaseActor in BaseActor.getList(mainStage, Rock::class.java.canonicalName)) {
            for (player: BaseActor in BaseActor.getList(mainStage, Player::class.java.canonicalName)) {
                player.preventOverlap(rockActor)
            }
        }
    }

    private fun checkIfPlayerIsOnGround() {
        for (playerActor: BaseActor in BaseActor.getList(mainStage, Player::class.java.canonicalName)) {
            var isTouchingGround = false
            for (groundActor: BaseActor in BaseActor.getList(mainStage, Ground::class.java.canonicalName)) {
                if (playerActor.overlaps(groundActor)) {
                    isTouchingGround = true
                    break
                }
            }
            if (!isTouchingGround) { // game over
                playerActor.remove()
                BaseGame.hurtSound!!.play(BaseGame.soundVolume)
                showGameOver()
            }
        }
    }

    private fun showGameOver() {
        winConditionLabel.isVisible = true
        restartLabel.isVisible = true
        winConditionLabel.setText("Game Over!")
    }

    private fun checkGoldPickup() {
        for (winActor: BaseActor in BaseActor.getList(mainStage, Gold::class.java.canonicalName)) {
            for (playerActor: BaseActor in BaseActor.getList(mainStage, Player::class.java.canonicalName)) {
                playerActor as Player
                if (playerActor.overlaps(winActor)) {
                    BaseGame.goldSound!!.play(BaseGame.soundVolume)
                    val effect = StarEffect()
                    effect.setScale(Gdx.graphics.height * .0002f)
                    effect.setPosition(winActor.x + 10f, winActor.y + 20f)
                    mainStage.addActor(effect)
                    effect.start()
                    winActor.remove()
                }
            }
        }
    }

    private fun checkWinConditionAndCountTime(dt: Float) {
        if (woman.overlaps(man) && BaseActor.count(mainStage, Gold::class.java.canonicalName) == 0 && !winConditionLabel.isVisible) {
            // WIN!
            winConditionLabel.isVisible = true
            restartLabel.isVisible = true
            LazerBeam(woman.x, woman.y, mainStage, comingDown = false)
            woman.remove()
            man.remove()
            BaseGame.winSound!!.play(BaseGame.soundVolume)
        } else if (time >= 0) {
            countTime(dt)
        }else {
            showGameOver()
            woman.remove()
            man.remove()
        }
    }

    private fun countTime(dt: Float) {
        timePassed += dt
        time = timeTilGameOver - timePassed.toInt()
        if (time >= 0)
            timeLabel.setText("Time: $time")
    }

    private fun handleLazerBeamComindDown() {
        if (lazerBeam != null && lazerBeam.animationFinished && lazerBeam.comingDown) {
            woman.inPlay = true
            woman.isVisible = true
            man.isVisible = true
            man.inPlay = true
            lazerBeam.remove()
        }
    }

    private fun uiSetup() {
        val padding = Gdx.graphics.height * .01f

        timeLabel = Label("Time: 0", BaseGame.labelStyle)
        timeLabel.setFontScale(.5f)
        timeLabel.setAlignment(Align.center)
        uiTable.add(timeLabel).expandY().top().padTop(padding).row()

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
        uiTable.add(restartLabel).padTop(padding).expandY().top()

        /*uiTable.debug = true*/
    }

    private fun cameraSetup() {
        val temp = mainStage.camera as OrthographicCamera
        temp.zoom = .6f // higher number = zoom in
        temp.position.x = 350f
        temp.position.y = 500f
        temp.update()
    }

    private fun tiledSetup() {
        val tma = TilemapActor("map/$tiledLevel.tmx", mainStage)

        for (obj in tma.getTileList("rock")) {
            val props = obj.properties
            Rock(props.get("x") as Float, props.get("y") as Float, mainStage)
        }

        for (obj in tma.getTileList("ground")) {
            val props = obj.properties
            Ground(props.get("x") as Float, props.get("y") as Float, mainStage)
        }

        val startPoint = tma.getTileList("start")[0]
        val props = startPoint.properties
        woman = Player(props.get("x") as Float, props.get("y") as Float, mainStage)
        man = Player(props.get("x") as Float, props.get("y") as Float, mainStage, woman = false)

        for (obj in tma.getTileList("gold")) {
            val props = obj.properties
            Gold(props.get("x") as Float, props.get("y") as Float, mainStage)
        }
    }
}
