package com.khb.navermap

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.OnMapReadyCallback

abstract class BaseDemoActivity(@LayoutRes private val layoutId: Int = R.layout.activity_main): AppCompatActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        // 왼쪽 상단 뒤로가기 버튼 활성화
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        setUpMap()
    }

    private fun setUpMap() {
        // 지도를 화면에 가져와요.
        // 자세한 설명은 https://navermaps.github.io/android-map-sdk/guide-ko/2-1.html를 확인해주세요.
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(SecretKeySet.CLIENT_ID)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFrame) as MapFragment?
            ?: run {
                MapFragment.newInstance().also {
                    supportFragmentManager.beginTransaction().add(R.id.mapFrame, it).commit()
                }
            }
        // BaseDemoActivity를 상속받아 작성하는 Activity코드에서 콜백함수인 onMapReady()를 구현하게 됩니다.
        mapFragment.getMapAsync(this)
    }

    /**
     * 왼쪽 상단에 위치한 뒤로가기 버튼은 네비게이션 동작이므로,
     * 뒤로가기 버튼의 기능을 정의해줘야 합니다.
     */
    override fun onOptionsItemSelected(item: MenuItem) =
        if (item.itemId == android.R.id.home) {
            finish() // 현재 화면 끝내기
            true
        } else {
            super.onOptionsItemSelected(item)
        }
}