package nl.sirrah.trycompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.*
import androidx.ui.core.Text
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import androidx.ui.material.themeTextStyle

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
        Text(text = "Hello World!", style = +themeTextStyle { h1 })
    }
}
