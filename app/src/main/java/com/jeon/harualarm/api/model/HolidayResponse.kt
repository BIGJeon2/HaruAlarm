package com.jeon.harualarm.api.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "response")
data class HolidayResponse(
    @Element(name="header")
    val header: Header,
    @Element (name="body")
    val body: Body
)

@Xml(name = "header")
data class Header(
    @PropertyElement(name = "resultCode")
    var resultCode: String,

    @PropertyElement(name = "resultMsg")
    var resultMsg: String
)

@Xml(name = "body")
data class Body(
    @Element(name = "items")
    var items: List<HolidayItem>,

    @PropertyElement(name = "numOfRows")
    var numOfRows: Int,

    @PropertyElement(name = "pageNo")
    var pageNo: Int,

    @PropertyElement(name = "totalCount")
    var totalCount: Int
)

@Xml(name = "item")
data class HolidayItem(
    @Element(name = "dateKind")
    var dateKind: String,

    @Element(name = "dateName")
    var dateName: String,

    @Element(name = "isHoliday")
    var isHoliday: String,

    @Element(name = "locdate")
    var locdate: String,

    @Element(name = "seq")
    var seq: Int
)
