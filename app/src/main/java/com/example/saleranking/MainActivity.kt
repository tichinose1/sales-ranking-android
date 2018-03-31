package com.example.saleranking

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val results = arrayOf(
                Result("モンスターストライク", "https://is3-ssl.mzstatic.com/image/thumb/Purple128/v4/d7/3a/ae/d73aae7d-b2c0-998f-c7a1-a2f60215b880/AppIcon-1x_U007emarketing-85-220-7.png/200x200bb.png"),
                Result("パズル＆ドラゴンズ","https://is1-ssl.mzstatic.com/image/thumb/Purple118/v4/c3/97/35/c397356b-bf5f-cae6-724f-5dc638a17f6c/AppIcon-1x_U007emarketing-0-85-220-0-9.png/200x200bb.png")
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MainAdapter(results)
    }
}
