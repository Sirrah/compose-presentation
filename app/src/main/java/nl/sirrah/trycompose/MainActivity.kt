package nl.sirrah.trycompose

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.setContent
import androidx.ui.graphics.Image
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.text.ParagraphStyle
import androidx.ui.text.style.TextAlign.Center
import nl.sirrah.trycompose.todo.cleanedup.ToDoAppCleanedUp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                HelloWorld()
            }
        }
    }

    @Composable
    fun HelloWorld() {
        Text("Hello World!")
    }

    @Composable
    fun MyStyledText(text: String) {
        Text(
            text = text,
            modifier = padding(8.dp),
            style = +themeTextStyle {
                h3.copy(color = +themeColor { primary })
            },
            paragraphStyle = ParagraphStyle(textAlign = Center)
        )
    }

//region Example with state

    @Composable
    fun MyCustomLayout(
        title: String,
        block: @Composable() () -> Unit
    ) {
        Column(
            mainAxisAlignment = MainAxisAlignment.SpaceAround,
            crossAxisAlignment = CrossAxisAlignment.Center
        ) {
            MyStyledText(title)
            HeightSpacer(40.dp)
            block()
        }
    }

    private var amount = 0

    /**
     * Will not update views to reflect the current state
     */
    @Composable
    fun CounterWithInt() {
        MyCustomLayout(title = "Counting with an Int") {
            Button(text = "Add", onClick = {
                amount++
            })
            Button(text = "Subtract", onClick = {
                amount--
            })
            MyStyledText(text = "Clicks: $amount")
        }
    }

    /**
     * Will properly re-render everything when either button
     * is pressed.
     */
    @Composable
    fun CounterWithState() {
        MyCustomLayout(title = "Counting with State") {
            val amount = +state { 0 }

            Button(text = "Add", onClick = {
                amount.value++
            })
            Button(text = "Subtract", onClick = {
                amount.value--
            })
            MyStyledText(text = "Clicks: ${amount.value}")
        }
    }

    // Can be any class, including a data class
    @Model
    class CounterModel(var count: Int)

    @Composable
    fun CounterWithModel() {
        MyCustomLayout(title = "Counting with @Model") {
            val amount = +memo { CounterModel(0) }

            Button(text = "Add", onClick = {
                amount.count++
            })
            Button(text = "Subtract", onClick = {
                amount.count--
            })
            MyStyledText(text = "Clicks: ${amount.count}")
        }
    }

//endregion

    companion object {
        const val TAG = "MainActivity"
    }
}
