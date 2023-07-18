package com.example.assessment.data.buying.remoteApi.reponse

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ItemToSell")
data class BuyingApiResponse(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("price") var price: Int,
    @SerializedName("quantity") var quantity: Int,
    @SerializedName("type") var type: Int
)
