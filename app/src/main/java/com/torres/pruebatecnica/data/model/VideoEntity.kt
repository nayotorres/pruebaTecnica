package com.torres.pruebatecnica.data.model

import com.google.gson.annotations.SerializedName

data class VideoEntity( @SerializedName("id")
                        var id:Int?=null,
                        @SerializedName("results")
                        var results:ArrayList<VideoDetail>?=null )

data class VideoDetail(@SerializedName("name")
                       var name:String?=null,
                       @SerializedName("key")
                       var key:String?=null,
                       @SerializedName("site")
                       var site:String?=null,
                       @SerializedName("official")
                       var official:Boolean?=null,
                       @SerializedName("id")
                       var id:String?=null)