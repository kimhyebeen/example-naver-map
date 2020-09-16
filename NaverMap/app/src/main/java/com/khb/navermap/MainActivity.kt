package com.khb.navermap

import android.widget.Toast
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.android.synthetic.main.activity_main.*
import ted.gun0912.clustering.naver.TedNaverClustering


class MainActivity : BaseDemoActivity() {
    lateinit var tedNaverClustering: TedNaverClustering<MarkerItem>
    lateinit var naverMap: NaverMap
    override fun onMapReady(naverMap: NaverMap) {
        // 카메라의 첫 화면 포커스 위치 지정 -> yapp 프로젝트에서는 현재 위치로 지정하면 될 듯
        this.naverMap = naverMap
        settingNaverMap()

        naverMap.moveCamera(
            CameraUpdate.toCameraPosition(
                CameraPosition(
                    NaverMap.DEFAULT_CAMERA_POSITION.target,
                    NaverMap.DEFAULT_CAMERA_POSITION.zoom
                )
            )
        )

        // # 3번에서 말한 TedNaverClustering
        tedNaverClustering = TedNaverClustering.with<MarkerItem>(this, naverMap)
            .customMarker { clusterItem ->
                Marker(clusterItem.position).apply {
                    icon = OverlayImage.fromResource(R.drawable.ic_flag)
                    width = 100
                    height = 110
                    title = clusterItem.position.latitude.toString()
                }

            }
            .markerClickListener { naverItem ->
                // 마커 클릭 기능
                val position = naverItem.position
                Toast.makeText(
                    this,
                    "${position.latitude},${position.longitude} 클릭됨",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .clusterClickListener { cluster ->
                // 클러스터링 클릭 기능
                val position = cluster.position
                Toast.makeText(
                    this,
                    "${cluster.size}개 클러스터 ${position.latitude},${position.longitude} 클릭됨",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .make()

        // 현재위치 마커 추가버튼
        addMarkerButton.setOnClickListener {
            val cameraPosition = naverMap.cameraPosition.target // 현재 카메라 위치
            tedNaverClustering.addItem(MarkerItem(cameraPosition.latitude, cameraPosition.longitude)) // 클러스터링 할 마커로 추가해요
            // 카메라의 현재 위치 포커스
            val cameraUpdate = CameraUpdate.scrollTo(
                LatLng(
                    cameraPosition.latitude,
                    cameraPosition.longitude
                )
            )
            naverMap.moveCamera(cameraUpdate)
        }

    }

    private fun settingNaverMap() {
        naverMap.apply {
            mapType = NaverMap.MapType.Navi
            isNightModeEnabled = true
//            uiSettings.isZoomControlEnabled = false
            uiSettings.isZoomGesturesEnabled = true
//            uiSettings.isLocationButtonEnabled = true
            setLayerGroupEnabled("LAYER_GROUP_BUILDING", true)
        }
    }
    /*
    private lateinit var mapFragment: MapFragment
    private lateinit var naverMap: NaverMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSetting()
        initMap()

        addMarkerButton.setOnClickListener {
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
            it.mapType = NaverMap.MapType.Navi
            it.isNightModeEnabled = true
            it.uiSettings.isZoomGesturesEnabled = true
            it.uiSettings.isZoomControlEnabled = false

            it.setLayerGroupEnabled("LAYER_GROUP_BUILDING", true)

            it.uiSettings.isLocationButtonEnabled = true

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

     */
}
