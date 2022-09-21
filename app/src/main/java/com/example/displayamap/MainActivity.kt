package com.example.displayamap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esri.arcgisruntime.mapping.view.MapView

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.geometry.PointCollection
import com.esri.arcgisruntime.geometry.Polygon
import com.esri.arcgisruntime.geometry.Polyline
import com.esri.arcgisruntime.geometry.SpatialReferences
import com.esri.arcgisruntime.mapping.ArcGISMap
import com.esri.arcgisruntime.mapping.BasemapStyle
import com.esri.arcgisruntime.mapping.Viewpoint
import com.esri.arcgisruntime.mapping.view.Graphic
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay
import com.esri.arcgisruntime.symbology.SimpleFillSymbol
import com.esri.arcgisruntime.symbology.SimpleLineSymbol
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol

import com.example.displayamap.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        setApiKeyForApp()

        setupMap()

        addGraphics()
    }

    private val activityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mapView: MapView by lazy {
        activityMainBinding.mapView
    }

    // set up your map here. You will call this method from onCreate()
    private fun setupMap() {

        // create a map with the BasemapStyle streets
        val map = ArcGISMap(BasemapStyle.ARCGIS_TOPOGRAPHIC)

        // set the map to be displayed in the layout's MapView
        mapView.map = map

        // set the viewpoint, Viewpoint(latitude, longitude, scale)
        mapView.setViewpoint(Viewpoint(45.3876, -75.6960, 72000.0))
    }

    override fun onPause() {
        mapView.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.resume()
    }

    override fun onDestroy() {
        mapView.dispose()
        super.onDestroy()
    }

    private fun setApiKeyForApp(){
        ArcGISRuntimeEnvironment.setApiKey("MY API HIDDEN")

    }

    private fun addGraphics() {

        // create a graphics overlay and add it to the map view
        val graphicsOverlay = GraphicsOverlay()
        mapView.graphicsOverlays.add(graphicsOverlay)

        val blueOutlineSymbol = SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, -0xff9c01, 2f)

        val polygonPoints = PointCollection(SpatialReferences.getWgs84()).apply {
            // Point(latitude, longitude)
            add(Point(-75.701076, 45.378869))
            add(Point(-75.700143, 45.379464))
            add(Point(-75.696770, 45.381683))
            add(Point(-75.689431, 45.383732))
            add(Point(-75.697077, 45.393278))
            add(Point(-75.700231, 45.390231))
            add(Point(-75.699037, 45.384303))
            add(Point(-75.701235, 45.381302))
        }
        // create a polygon geometry from the point collection
        val polygon = Polygon(polygonPoints)

        // create an orange fill symbol with 20% transparency and the blue simple line symbol
        val polygonFillSymbol =
            SimpleFillSymbol(SimpleFillSymbol.Style.SOLID, -0x7f00a8cd, blueOutlineSymbol)

        // create a polygon graphic from the polygon geometry and symbol
        val polygonGraphic = Graphic(polygon, polygonFillSymbol)
        // add the polygon graphic to the graphics overlay
        graphicsOverlay.graphics.add(polygonGraphic)
    }

}
