package com.jeon.harualarm.api.model


import com.google.gson.annotations.SerializedName

data class Holidays(
    val response: Response = Response()
) {
    data class Response(
        val body: Body = Body(),
        val header: Header = Header()
    ) {
        data class Body(
            val items: Items = Items(),
            val numOfRows: Int = 0,
            val pageNo: Int = 0,
            val totalCount: Int = 0
        ) {
            data class Items(
                val item: List<Item> = listOf()
            ) {
                data class Item(
                    val dateKind: String = "",
                    val dateName: String = "",
                    val isHoliday: String = "",
                    val locdate: Int = 0,
                    val seq: Int = 0
                )
            }
        }

        data class Header(
            val resultCode: String = "",
            val resultMsg: String = ""
        )
    }
}