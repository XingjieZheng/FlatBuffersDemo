package com.xingjiezheng.flatbuffersdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.flatbuffers.FlatBufferBuilder
import com.google.gson.Gson
import com.xingjiezheng.flatbuffersdemo.model.flat.User
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.ByteBuffer


class MainActivity : AppCompatActivity() {


    var data:ByteArray? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_decode.setOnClickListener {
          var user =  decodeTest(data)
            tv_info.text =  ("id:"+user.id() +" name:"+user.name() + " avatar:"+user.avatarUrl())
        }
        btn_encode.setOnClickListener{
           data =  encodeTest()
            if(data!=null){
            tv_info.text = Gson().toJson(data)
        }
        }
    }

    private fun encodeTest() : ByteArray {
        var builder = FlatBufferBuilder(0)
        val id = 1000L
        val avatarUrl = builder.createString("https://www.xingjienzheng.com")
        val name = builder.createString("xingjiezheng")

        User.startUser(builder)
        User.addId(builder, id)
        User.addAvatarUrl(builder, avatarUrl)
        User.addName(builder, name)
        val eab = User.endUser(builder)
        builder.finish(eab)
        return builder.sizedByteArray()
    }

    private fun decodeTest(data: ByteArray?) : User {
        val byteBuffer = ByteBuffer.wrap(data)
        return User.getRootAsUser(byteBuffer)
    }


}
