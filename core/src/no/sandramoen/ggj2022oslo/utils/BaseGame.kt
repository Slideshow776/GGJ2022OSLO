package no.sandramoen.ggj2022oslo.utils

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import kotlin.system.measureTimeMillis

abstract class BaseGame() : Game(), AssetErrorListener {
    private val tag = "BaseGame.kt"

    init { game = this }

    companion object {
        private var game: BaseGame? = null

        lateinit var assetManager: AssetManager
        lateinit var fontGenerator: FreeTypeFontGenerator
        const val WORLD_WIDTH = 100f
        const val WORLD_HEIGHT = 100f
        const val scale = 1.0f
        var RATIO = 0f
        val lightPink = Color(1f, .816f, .94f, 1f)
        var enableCustomShaders = true

        // game assets
        var labelStyle: LabelStyle? = null
        var textButtonStyle: TextButtonStyle? = null
        var textureAtlas: TextureAtlas? = null
        var soundVolume = .75f
        var musicVolume = .125f


        // game state

        fun setActiveScreen(s: BaseScreen) { game?.setScreen(s) }
    }

    override fun create() {
        Gdx.input.inputProcessor = InputMultiplexer() // discrete input

        // global variables
        RATIO = Gdx.graphics.width.toFloat() / Gdx.graphics.height

        // asset manager
        val time = measureTimeMillis {
            assetManager = AssetManager()
            assetManager.setErrorListener(this)
            assetManager.load("images/included/packed/ggj2022oslo.pack.atlas", TextureAtlas::class.java)

            // music
            // assetManager.load("audio/music/251461__joshuaempyre__arcade-music-loop.wav", Music::class.java)


            // sounds
            // assetManager.load("audio/sound/blip.wav", Sound::class.java)


            // fonts
            val resolver = InternalFileHandleResolver()
            assetManager.setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(resolver))
            assetManager.setLoader(BitmapFont::class.java, ".ttf", FreetypeFontLoader(resolver))
            assetManager.setLoader(Text::class.java, TextLoader(InternalFileHandleResolver()))

            // shaders
            // assetManager.load(AssetDescriptor("shaders/default.vs", Text::class.java, TextLoader.TextParameter()))
            // assetManager.load(AssetDescriptor("shaders/vignette.fs", Text::class.java, TextLoader.TextParameter() ))

            // skins
            // assetManager.load("skins/arcade/arcade.json", Skin::class.java)

            // i18n
            // assetManager.load("i18n/MyBundle", I18NBundle::class.java, I18NBundleParameter(Locale(currentLocale)))

            assetManager.finishLoading()

            textureAtlas = assetManager.get("images/included/packed/ggj2022oslo.pack.atlas") // all images are found in this global static variable

            // audio
            // levelMusic = assetManager.get("audio/music/251461__joshuaempyre__arcade-music-loop.wav", Music::class.java)

            // blipSound = assetManager.get("audio/sound/blip.wav", Sound::class.java)

            // text files
            // defaultShader = assetManager.get("shaders/default.vs", Text::class.java).getString()
            // vignetteShader = assetManager.get("shaders/vignette.fs", Text::class.java).getString()

            // skin
            // skin = assetManager.get("skins/arcade/arcade.json", Skin::class.java)

            // i18n
            // myBundle = assetManager["i18n/MyBundle", I18NBundle::class.java]

            // fonts
            FreeTypeFontGenerator.setMaxTextureSize(2048) // solves font bug that won't show some characters like "." and "," in android
            fontGenerator = FreeTypeFontGenerator(Gdx.files.internal("fonts/ARCADE_R.TTF"))
            val fontParameters = FreeTypeFontParameter()
            fontParameters.size = (.038f * Gdx.graphics.height).toInt() // Font size is based on width of screen...
            fontParameters.color = Color.WHITE
            fontParameters.borderWidth = 2f
            fontParameters.borderColor = Color.BLACK
            fontParameters.borderStraight = true
            fontParameters.minFilter = TextureFilter.Linear
            fontParameters.magFilter = TextureFilter.Linear
            val customFont = fontGenerator.generateFont(fontParameters)

            val buttonFontParameters = FreeTypeFontParameter()
            buttonFontParameters.size = (.04f * Gdx.graphics.height).toInt() // If the resolutions height is 1440 then the font size becomes 86
            buttonFontParameters.color = Color.WHITE
            buttonFontParameters.borderWidth = 2f
            buttonFontParameters.borderColor = Color.BLACK
            buttonFontParameters.borderStraight = true
            buttonFontParameters.minFilter = TextureFilter.Linear
            buttonFontParameters.magFilter = TextureFilter.Linear
            val buttonCustomFont = fontGenerator.generateFont(buttonFontParameters)

            labelStyle = LabelStyle()
            labelStyle!!.font = customFont

            textButtonStyle = TextButtonStyle()
            val buttonTexUp = textureAtlas!!.findRegion("blankPixel") // button
            // val buttonTexDown = textureAtlas!!.findRegion("button-pressed")
            val buttonPatchUp = NinePatch(buttonTexUp, 44, 24, 24, 24)
            // val buttonPatchDown = NinePatch(buttonTexDown, 44, 24, 24, 24)
            textButtonStyle!!.up = NinePatchDrawable(buttonPatchUp)
            // textButtonStyle!!.down = NinePatchDrawable(buttonPatchDown)
            textButtonStyle!!.font = buttonCustomFont
            textButtonStyle!!.fontColor = Color.WHITE
        }
        Gdx.app.error(tag, "Asset manager took $time ms to load all game assets.")
    }

    override fun dispose() {
        super.dispose()
        try { // TODO: uncomment this when development is done
            assetManager.dispose()
            fontGenerator.dispose()
        } catch (error: UninitializedPropertyAccessException) {
            Gdx.app.error("BaseGame", "$error")
        }
    }

    override fun error(asset: AssetDescriptor<*>, throwable: Throwable) {
        Gdx.app.error(tag, "Could not load asset: " + asset.fileName, throwable)
    }
}
