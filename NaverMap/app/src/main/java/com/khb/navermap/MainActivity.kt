package com.khb.navermap

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.NaverMapSdk.NaverCloudPlatformClient
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.widget.ZoomControlView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var mapFragment: MapFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSetting()
        initMap()

        button.setOnClickListener {
            mapFragment.getMapAsync { setMarker(it.cameraPosition.target.latitude, it.cameraPosition.target.longitude, it) }
        }
    }
    private fun initSetting() {
        NaverMapSdk.getInstance(this).client = NaverCloudPlatformClient(StringKeySet.CLIENT_ID)
        supportFragmentManager.let { fm ->
            mapFragment = fm.findFragmentById(R.id.mapFrame) as MapFragment?
                ?: MapFragment.newInstance(NaverMapOptions().zoomControlEnabled(false))
                    .also { map -> fm.beginTransaction().add(R.id.mapFrame, map).commit() }
        }
    }
    private fun initMap() {
        mapFragment.getMapAsync {
            val zoomControlView = findViewById<ZoomControlView>(R.id.zoom)
            zoomControlView.map = it
            it.mapType = NaverMap.MapType.Navi
            it.isNightModeEnabled = true
            it.setLayerGroupEnabled("LAYER_GROUP_BUILDING", true)

            setMarker(37.5670135, 126.9783740, it)
        }
    }
    private fun setMarker(lat: Double, lon: Double, naverMap: NaverMap) {
        Marker().apply {
            position = LatLng(lat, lon)
            setOnClickListener {
                Toast.makeText(applicationContext, "마커 클릭됨", Toast.LENGTH_SHORT).show()
                true
            }
            icon = OverlayImage.fromResource(R.drawable.ic_flag)
            width = 100
            height = 110
            map = naverMap
        }
    }
}
