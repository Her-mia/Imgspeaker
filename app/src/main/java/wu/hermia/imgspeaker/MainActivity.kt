package wu.hermia.imgspeaker

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import wu.hermia.imgspeaker.ui.theme.ImgspeakerTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImgspeakerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ImageView(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun ImageView(modifier: Modifier=Modifier) {
    val imageModifier = Modifier
        .size(150.dp)
    val image = painterResource(R.drawable.typewriter)
    Image(
        painter = image,
        contentDescription = null,
        modifier = Modifier)}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ImgspeakerTheme {
        ImageView()
    }
}