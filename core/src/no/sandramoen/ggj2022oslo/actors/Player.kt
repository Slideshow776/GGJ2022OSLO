package no.sandramoen.ggj2022oslo.actors

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import no.sandramoen.ggj2022oslo.utils.BaseActor
import com.badlogic.gdx.utils.Array
import no.sandramoen.ggj2022oslo.utils.BaseGame
import no.sandramoen.ggj2022oslo.utils.GameUtils

class Player(x: Float, y: Float, s: Stage, val woman: Boolean = true) : BaseActor(x, y, s) {
    val tag = "Player"
    var inPlay = true
    var oWidth = 0f
    var oHeight = 0f
    var reversedHorizontal = true
    var reversedVertical = false

    var joystickActive = false
    var joystickAngle = -1f

    var alive = true
    private lateinit var upAnimation: Animation<TextureAtlas.AtlasRegion>
    private lateinit var downAnimation: Animation<TextureAtlas.AtlasRegion>
    private lateinit var leftAnimation: Animation<TextureAtlas.AtlasRegion>
    private lateinit var rightAnimation: Animation<TextureAtlas.AtlasRegion>
    private lateinit var currentAnimation: Animation<TextureAtlas.AtlasRegion>

    init {
        animationSetup()

        setAcceleration(100f)
        setMaxSpeed(10f)
        setDeceleration(100f)

        setCollisionAndAnimationSize()

        setPixelSizeToRelativeSize(32)
        oWidth = width
        oHeight = height
        setCollisionAndAnimationSize()

        /*debug = true*/
    }

    override fun act(dt: Float) {
        super.act(dt)
        if (!inPlay) return

        if (Gdx.app.type == Application.ApplicationType.Android) {
            if (joystickActive && joystickAngle >= 0) androidControls()
        } else {
            if (joystickActive && joystickAngle >= 0) androidControls()
            desktopControls()
        }

        // set animation and sound
        if (getSpeed() == 0f) {
            setAnimationPaused(true)
            if (woman) BaseGame.stepsRMusic!!.volume = 0f
            else BaseGame.stepsLMusic!!.volume = 0f
        } else {
            if (woman) {
                BaseGame.stepsRMusic!!.volume = BaseGame.soundVolume * .25f
            } else if (!woman) {
                BaseGame.stepsLMusic!!.volume = BaseGame.soundVolume * .25f
            }
            setAnimationPaused(false)
            val angle = getMotionAngle()
            if (angle >= 45 && angle <= 135) {
                if (currentAnimation != upAnimation) {
                    setTheAnimation(upAnimation)
                }
            } else if (angle > 135 && angle < 225) {
                if (currentAnimation != leftAnimation) {
                    setTheAnimation(leftAnimation)
                }
            } else if (angle >= 225 && angle <= 315) {
                if (currentAnimation != downAnimation) {
                    setTheAnimation(downAnimation)
                }
            } else {
                if (currentAnimation != rightAnimation) {
                    setTheAnimation(rightAnimation)
                }
            }
        }

        // miscellaneous
        applyPhysics(dt)
    }

    fun removeMe() {
        if (alive) {
            alive = false
            if (woman) BaseGame.deathRSound!!.play(BaseGame.soundVolume)
            else BaseGame.deathLSound!!.play(BaseGame.soundVolume)
            setSpeed(getSpeed() * 5)
            addAction(Actions.sequence(
                Actions.parallel(
                    Actions.scaleTo(0f, 0f, .4f),
                    Actions.fadeOut(.4f)
                ),
                Actions.run { remove() }
            ))
        }
    }

    private fun animationSetup() {
        var frameDuration = .25f
        if (woman) {
            var animationImages: Array<TextureAtlas.AtlasRegion> = Array()
            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman4"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman5"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman6"))
            upAnimation =
                Animation(frameDuration, animationImages, Animation.PlayMode.LOOP_PINGPONG)
            animationImages.clear()

            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman1"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman2"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman3"))
            downAnimation =
                Animation(frameDuration, animationImages, Animation.PlayMode.LOOP_PINGPONG)
            animationImages.clear()

            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman10"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman11"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman12"))
            leftAnimation =
                Animation(frameDuration, animationImages, Animation.PlayMode.LOOP_PINGPONG)
            animationImages.clear()

            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman7"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman8"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman9"))
            rightAnimation =
                Animation(frameDuration, animationImages, Animation.PlayMode.LOOP_PINGPONG)
            animationImages.clear()
        } else {
            var animationImages: Array<TextureAtlas.AtlasRegion> = Array()
            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man4"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man5"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man6"))
            upAnimation =
                Animation(frameDuration, animationImages, Animation.PlayMode.LOOP_PINGPONG)
            animationImages.clear()

            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man1"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man2"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man3"))
            downAnimation =
                Animation(frameDuration, animationImages, Animation.PlayMode.LOOP_PINGPONG)
            animationImages.clear()

            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man10"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man11"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man12"))
            leftAnimation =
                Animation(frameDuration, animationImages, Animation.PlayMode.LOOP_PINGPONG)
            animationImages.clear()

            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man7"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man8"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man9"))
            rightAnimation =
                Animation(frameDuration, animationImages, Animation.PlayMode.LOOP_PINGPONG)
            animationImages.clear()
        }

        currentAnimation = upAnimation
        setAnimation(upAnimation)
    }

    private fun androidControls() {
        if (reversedHorizontal && reversedVertical) {
            if (woman) accelerateAtAngle(joystickAngle)
            else accelerateAtAngle(joystickAngle + 180)
        } else if (reversedVertical) {
            if (joystickAngle >= 45 && joystickAngle <= 135) { // up
                if (woman) accelerateAtAngle(90f)
                else accelerateAtAngle(270f)
            } else if (joystickAngle > 135 && joystickAngle < 225) { // left
                accelerateAtAngle(180f)
            } else if (joystickAngle >= 225 && joystickAngle <= 315) { // down
                if (woman) accelerateAtAngle(270f)
                else accelerateAtAngle(90f)
            } else { // right
                accelerateAtAngle(0f)
            }
        } else if (reversedHorizontal) {
            if (joystickAngle >= 45 && joystickAngle <= 135) { // up
                accelerateAtAngle(90f)
            } else if (joystickAngle > 135 && joystickAngle < 225) { // left
                if (woman) accelerateAtAngle(180f)
                else accelerateAtAngle(0f)
            } else if (joystickAngle >= 225 && joystickAngle <= 315) { // down
                accelerateAtAngle(270f)
            } else { // right
                if (woman) accelerateAtAngle(0f)
                else accelerateAtAngle(180f)
            }

        } else {
            if (joystickAngle >= 45 && joystickAngle <= 135) { // up
                accelerateAtAngle(90f)
            } else if (joystickAngle > 135 && joystickAngle < 225) { // left
                accelerateAtAngle(joystickAngle)
            } else if (joystickAngle >= 225 && joystickAngle <= 315) { // down
                accelerateAtAngle(270f)
            } else { // right
                accelerateAtAngle(joystickAngle)
            }
        }
    }

    private fun desktopControls() {
        if (reversedHorizontal && reversedVertical) {
            if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
                if (woman) accelerateAtAngle(90f)
                else accelerateAtAngle(270f)
            }
            if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
                if (woman) accelerateAtAngle(0f)
                else accelerateAtAngle(180f)
            }
            if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
                if (woman) accelerateAtAngle(270f)
                else accelerateAtAngle(90f)
            }
            if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
                if (woman) accelerateAtAngle(180f)
                else accelerateAtAngle(0f)
            }
        } else if (reversedVertical) {
            if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
                if (woman) accelerateAtAngle(90f)
                else accelerateAtAngle(270f)
            }
            if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
                accelerateAtAngle(0f)
            }
            if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
                if (woman) accelerateAtAngle(270f)
                else accelerateAtAngle(90f)
            }
            if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
                accelerateAtAngle(180f)
            }
        } else if (reversedHorizontal) {
            if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
                accelerateAtAngle(90f)
            }
            if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
                if (woman) accelerateAtAngle(0f)
                else accelerateAtAngle(180f)
            }
            if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
                accelerateAtAngle(270f)
            }
            if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
                if (woman) accelerateAtAngle(180f)
                else accelerateAtAngle(0f)
            }
        } else {
            if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
                accelerateAtAngle(90f)
            }
            if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
                accelerateAtAngle(0f)
            }
            if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S)) {
                accelerateAtAngle(270f)
            }
            if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
                accelerateAtAngle(180f)
            }
        }
    }

    private fun setTheAnimation(animation: Animation<TextureAtlas.AtlasRegion>) {
        setAnimation(animation)
        currentAnimation = animation
        setCollisionAndAnimationSize()
        setBoundaryRectangle()
    }

    private fun setCollisionAndAnimationSize() {
        setPixelSizeToRelativeSize(16)
        setAnimationSize(oWidth * 1.5f, oHeight * 1.5f)
    }
}
