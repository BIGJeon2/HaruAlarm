package com.jeon.harualarm

import com.jeon.harualarm.util.DateProvider
import org.junit.Test

import org.junit.Assert.*
import java.util.Calendar

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun test_test(){
        assertEquals(4, 2 + 4)
    }

    @Test
    fun addition_isCorrect() {
        val dateProvider = DateProvider()
        val calendar = Calendar.getInstance()
        val days = ArrayList<Calendar>()

        //Add before date
        val beforeDate = dateProvider.getBeforeMonth(calendar)
        val beforeDaySize = calendar.get(Calendar.DAY_OF_WEEK) - 1
        val beforeDayList = dateProvider.getMonthDayList(beforeDate)
        for (idx in beforeDayList.size - beforeDaySize until beforeDayList.size){
            val newDate = beforeDayList[idx]
            days.add(newDate)
        }

        //Add curr date
        val currDayList = dateProvider.getMonthDayList(calendar)
        for (newDate in currDayList){
            days.add(newDate)
        }

        //Add next date
        val nextDate = dateProvider.getNextMonth(calendar)
        val nextDateList = dateProvider.getMonthDayList(nextDate)
        for (idx in 1 until 35 - days.size){
            val newDate = nextDateList[idx]
            days.add(newDate)
        }
        assertEquals(35, days.size)
    }
}