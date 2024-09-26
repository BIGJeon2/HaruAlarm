package com.jeon.harualarm.api.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "response", strict = false)
data class HolidayResponse(
    @field:Element(name = "header")
    var header: Header,

    @field:Element(name = "body")
    var body: Body
)

@Root(name = "header", strict = false)
data class Header(
    @field:Element(name = "resultCode")
    var resultCode: String,

    @field:Element(name = "resultMsg")
    var resultMsg: String
)

@Root(name = "body", strict = false)
data class Body(
    @field:ElementList(entry = "item", inline = true)
    var items: List<HolidayItem>,

    @field:Element(name = "numOfRows")
    var numOfRows: Int,

    @field:Element(name = "pageNo")
    var pageNo: Int,

    @field:Element(name = "totalCount")
    var totalCount: Int
)

@Root(name = "item", strict = false)
data class HolidayItem(
    @field:Element(name = "dateKind")
    var dateKind: String,

    @field:Element(name = "dateName")
    var dateName: String,

    @field:Element(name = "isHoliday")
    var isHoliday: String,

    @field:Element(name = "locdate")
    var locdate: String,

    @field:Element(name = "seq")
    var seq: Int
)
