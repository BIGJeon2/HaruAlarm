package com.jeon.harualarm.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jeon.database.Entity.TodoEvent
import com.jeon.harualarm.ui.theme.HaruAlarmTheme
import com.jeon.harualarm.ui.theme.MainColor
import com.jeon.harualarm.viewmodels.FakeCalendarViewModel
import de.drick.compose.edgetoedgepreviewlib.CameraCutoutMode
import de.drick.compose.edgetoedgepreviewlib.EdgeToEdgeTemplate
import de.drick.compose.edgetoedgepreviewlib.NavigationMode

class NextTodoView() {

    @Composable
    fun NextTodoCardView(nextTodo: TodoEvent){
        var todo = remember {
            nextTodo
        }

        Card {
            Text(text = nextTodo.dateID)
        }

    }

}

@Preview
@Composable
fun NextTodoViewPreview(){
    EdgeToEdgeTemplate(
        navMode = NavigationMode.ThreeButton,
        cameraCutoutMode = CameraCutoutMode.Middle,
        showInsetsBorder = true,
        isStatusBarVisible = true,
        isNavigationBarVisible = true,
        isInvertedOrientation = false
    ){
        HaruAlarmTheme {
            val vm = FakeCalendarViewModel()
            Column(
                modifier = Modifier.background(MainColor).fillMaxSize()
            ){
                NextTodoView().NextTodoCardView(vm.nextTodo)
            }
        }
    }
}