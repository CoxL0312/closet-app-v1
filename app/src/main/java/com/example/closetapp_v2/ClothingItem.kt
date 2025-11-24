package com.example.closetapp_v2

public data class ClothingItem(
    var name: String = "Empty",
    var style: String = "Empty",
    var material: String = "Empty",
    var color: String = "Empty",
    var comfort: Int = 5,//1-10 scale
    var occasion: String = "Empty"
)