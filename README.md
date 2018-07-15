# FlatBuffersDemo
FlatBuffers Android Demo

###Step1
生成fbs文件：    
如下fbs目录下的文件user_schema.fab
    
    namespace User;
    
    table User {
      	  name : string;
        id : long;
        avatar_url : string;
    }
    
    root_type User;
    
    

###Step2
将fbs文件转成FlatBuffers的二进制java文件格式：
（1）使用fbs-tools目录下的工具flatc.exe（官网提供），
（2）在window下打开cmd执行命令 flatc.exe -j -b user_schema.fbs，
（3）生成文件User/User.java，将该文件拷贝到工程中使用。

###Setp3
在Java中使用
   
    //User对象转Byte数组
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
    
    //Byte数组转User对象
    private fun decodeTest(data: ByteArray?) : User {
            val byteBuffer = ByteBuffer.wrap(data)
            return User.getRootAsUser(byteBuffer)
    }

