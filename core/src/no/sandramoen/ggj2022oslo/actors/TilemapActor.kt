package no.sandramoen.ggj2022oslo.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import no.sandramoen.ggj2022oslo.utils.BaseActor
import java.util.ArrayList

class TilemapActor(filename: String, theStage: Stage): Actor() {
    companion object {
        // window dimensions
        val windowWidth = Gdx.graphics.width.toFloat()
        val windowHeight = Gdx.graphics.height.toFloat()
    }

    private var tiledMap: TiledMap
    private var tiledCamera: OrthographicCamera
    private var tiledMapRenderer: OrthoCachedTiledMapRenderer

    init {
        // set up tile map, renderer, and camera
        tiledMap = TmxMapLoader().load(filename)

        val tileWidth = tiledMap.properties.get("tilewidth") as Int
        val tileHeight = tiledMap.properties.get("tileheight") as Int
        val numTilesHorizontal = tiledMap.properties.get("width") as Int
        val numTilesVertical = tiledMap.properties.get("height") as Int
        val mapWidth = tileWidth * numTilesHorizontal
        val mapHeight = tileHeight * numTilesVertical

        BaseActor.setWorldBounds(mapWidth.toFloat(), mapHeight.toFloat())

        tiledMapRenderer = OrthoCachedTiledMapRenderer(tiledMap)
        tiledMapRenderer.setBlending(true)

        tiledCamera = OrthographicCamera()
        tiledCamera.setToOrtho(false, windowWidth, windowHeight)
        tiledCamera.update()

        theStage.addActor(this)
    }

    override fun act(dt: Float) {
        super.act(dt)
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        /*super.draw(batch, parentAlpha)*/

        // adjust tilemap camera to stay in sync with main camera
        val mainCamera = stage.camera as OrthographicCamera
        tiledCamera.position.x = mainCamera.position.x
        tiledCamera.position.y = mainCamera.position.y
        tiledCamera.zoom = mainCamera.zoom
        tiledCamera.update()
        tiledMapRenderer.setView(tiledCamera)

        // need the following code to force batch order, otherwise it is batched and rendered last
        batch!!.end()
        tiledMapRenderer.render()
        batch.begin()
    }

    fun getRectangleList(propertyName: String): ArrayList<MapObject> {
        val list = ArrayList<MapObject>()
        for (layer in tiledMap.layers) {
            for (obj in layer.objects) {
                if (obj !is RectangleMapObject)
                    continue

                val props = obj.getProperties()
                if (props.containsKey("name") && props.get("name") == propertyName)
                    list.add(obj)
            }
        }
        return list
    }

    fun getTileList(propertyName: String):ArrayList<MapObject> {
        val list = ArrayList<MapObject>()

        for (layer: MapLayer in tiledMap.layers) {
            for (obj: MapObject in layer.objects) {
                if (obj !is TiledMapTileMapObject)
                    continue

                val props = obj.properties

                // Default MapProperties are stored within associated Tile object
                // Instance-specific overrides are stored in MapObject

                val tmtmo:TiledMapTileMapObject = obj
                val t = tmtmo.tile
                val defaultProps = t.properties

                if (defaultProps.containsKey("name") && defaultProps.get("name") == propertyName)
                    list.add(obj)

                // get list of default property keys
                val propertyKeys = defaultProps.keys

                // iterate over keys; copy default values into props if needed
                while (propertyKeys.hasNext()) {
                    val key = propertyKeys.next()

                    // check if value already exists; if not, create property with default value
                    if (props.containsKey(key)) {
                        continue
                    } else {
                        val value = defaultProps.get(key)
                        props.put(key, value)
                    }
                }
            }
        }
        return list
    }
}
