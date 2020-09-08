package com.khb.navermap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.NaverMapSdk.NaverCloudPlatformClient


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NaverMapSdk.getInstance(this).client = NaverCloudPlatformClient(StringKeySet.CLIENT_ID)
    }
}