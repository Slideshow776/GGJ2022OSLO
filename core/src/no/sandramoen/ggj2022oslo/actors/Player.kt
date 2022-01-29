package no.sandramoen.ggj2022oslo.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Stage
import no.sandramoen.ggj2022oslo.utils.BaseActor
import com.badlogic.gdx.utils.Array
import no.sandramoen.ggj2022oslo.utils.BaseGame

class Player(x: Float, y: Float, s: Stage, val woman: Boolean = true) : BaseActor(x, y, s) {
    val tag = "Player"
    var inPlay = true

    // animations
    private var upAnimation: Animation<TextureAtlas.AtlasRegion>
    private var downAnimation: Animation<TextureAtlas.AtlasRegion>
    private var leftAnimation: Animation<TextureAtlas.AtlasRegion>
    private var rightAnimation: Animation<TextureAtlas.AtlasRegion>

    private var currentAnimation: Animation<TextureAtlas.AtlasRegion>

    init {
        // animations
        var frameDuration = .25f
        if (woman) {
            var animationImages: Array<TextureAtlas.AtlasRegion> = Array()
            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman4"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman5"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman6"))
            upAnimation = Animation(frameDuration, animationImages, Animation.PlayMode.LOOP_PINGPONG)
            animationImages.clear()

            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman1"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman2"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman3"))
            downAnimation = Animation(frameDuration, animationImages, Animation.PlayMode.LOOP_PINGPONG)
            animationImages.clear()

            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman10"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman11"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman12"))
            leftAnimation = Animation(frameDuration, animationImages, Animation.PlayMode.LOOP_PINGPONG)
            animationImages.clear()

            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman7"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman8"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("woman/woman9"))
            rightAnimation = Animation(frameDuration, animationImages, Animation.PlayMode.LOOP_PINGPONG)
            animationImages.clear()
        } else {
            var animationImages: Array<TextureAtlas.AtlasRegion> = Array()
            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man4"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man5"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man6"))
            upAnimation = Animation(frameDuration, animationImages, Animation.PlayMode.LOOP_PINGPONG)
            animationImages.clear()

            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man1"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man2"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man3"))
            downAnimation = Animation(frameDuration, animationImages, Animation.PlayMode.LOOP_PINGPONG)
            animationImages.clear()

            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man10"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man11"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man12"))
            leftAnimation = Animation(frameDuration, animationImages, Animation.PlayMode.LOOP_PINGPONG)
            animationImages.clear()

            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man7"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man8"))
            animationImages.add(BaseGame.textureAtlas!!.findRegion("man/man9"))
            rightAnimation = Animation(frameDuration, animationImages, Animation.PlayMode.LOOP_PINGPONG)
            animationImages.clear()
        }

        currentAnimation = upAnimation
        setAnimation(upAnimation)

        setAcceleration(800f)
        setMaxSpeed(100f)
        setDeceleration(800f)
    }

    override fun act(dt: Float) {
        super.act(dt)
        if (!inPlay) return

        // keys
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

        if (getSpeed() == 0f) {
            setAnimationPaused(true)
        } else {
            setAnimationPaused(false)
            val angle = getMotionAngle()
            if (angle >= 45 && angle <= 135) {
                if (currentAnimation != upAnimation) {
                    setAnimation(upAnimation)
                    currentAnimation = upAnimation
                }
            } else if (angle > 135 && angle < 225) {
                if (currentAnimation != leftAnimation) {
                    setAnimation(leftAnimation)
                    currentAnimation = leftAnimation
                }
            } else if (angle >= 225 && angle <= 315) {
                if (currentAnimation != downAnimation) {
                    setAnimation(downAnimation)
                    currentAnimation = downAnimation
                }
            } else {
                if (currentAnimation != rightAnimation) {
                    setAnimation(rightAnimation)
                    currentAnimation = rightAnimation
                }
            }
        }

        applyPhysics(dt)
    }
}
