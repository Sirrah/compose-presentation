package nl.sirrah.trycompose.todo.cleanedup

import androidx.compose.*
import androidx.lifecycle.MutableLiveData
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.px
import androidx.ui.core.sp
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.selection.Toggleable
import androidx.ui.foundation.shape.border.Border
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.layout.CrossAxisAlignment.Companion.Center
import androidx.ui.layout.MainAxisAlignment.SpaceBetween
import androidx.ui.material.Button
import androidx.ui.material.Checkbox
import androidx.ui.material.ripple.Ripple
import androidx.ui.material.surface.Card
import androidx.ui.material.themeColor
import androidx.ui.material.themeTextStyle
import androidx.ui.text.ParagraphStyle
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontFamily
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextAlign

@Model
data class ToDoItem(
    var description: String,
    var completed: Boolean = false
)

@Model
data class ToDoList(var items: List<ToDoItem>) {

    fun add(item: ToDoItem) {
        items += item
    }
}

@Composable
fun ToDoAppCleanedUp() {
    val data = +memo {
        ToDoList(
            listOf(
                ToDoItem("Write presentation", true),
                ToDoItem("Do presentation"),
                ToDoItem("Use Jetpack Compose")
            )
        )
    }

    /**
     * Data travels "down" into the UI tree, events bubble "up"
     */
    Column(crossAxisAlignment = Center) {
        SimpleListAdapter(data.items) { item ->
            ToDoItem(item) { completed ->
                item.completed = completed
            }
        }
        Button(
            text = "Add work",
            onClick = { data.add(ToDoItem("More things")) })
    }
}

@Composable
fun <T> SimpleListAdapter(
    items: List<T>,
    block: @Composable() (T) -> Unit
) {
    Column {
        items.forEach {
            Padding(top = 8.dp, bottom = 8.dp) {
                block(it)
            }
        }
    }
}

@Composable
fun ToDoItem(item: ToDoItem, onCheckedChange: (Boolean) -> Unit) {
    val color = +colorFor(item)

    MyCard(color) {
        MyToggle(
            description = item.description,
            checked = item.completed,
            color = color,
            onCheckedChange = onCheckedChange
        )
    }
}

private fun colorFor(item: ToDoItem): Effect<Color> {
    return themeColor {
        if (item.completed) primary else secondary
    }
}

@Composable
fun MyCard(color: Color, block: @Composable() () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.px),
        border = Border(color, 1.dp),
        elevation = 4.dp
    ) {
        block()
    }
}

val defaultTextStyle = TextStyle(
    fontFamily = FontFamily("Roboto"),
    fontWeight = FontWeight.normal,
    fontSize = 32.sp
)

@Composable
fun MyToggle(
    description: String,
    checked: Boolean,
    color: Color,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        mainAxisAlignment = SpaceBetween,
        crossAxisAlignment = Center
    ) {
        // Not all elements have a 'modifier' property yet
        // so use the old form of Padding
        Padding(left = 4.dp, right = 8.dp) {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
        Text(
            text = description,
            style = +themeTextStyle {
                defaultTextStyle.copy(color = color)
            },
            paragraphStyle = ParagraphStyle(TextAlign.Center),
            modifier = padding(4.dp)
        )
    }
}
