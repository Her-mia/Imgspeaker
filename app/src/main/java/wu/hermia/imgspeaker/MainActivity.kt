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
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImgspeakerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    Column (modifier = modifier.fillMaxSize()) {
        ImageView()
        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .wrapContentSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                ElevatedButton(onClick = { }) {
                    Text("Camera")
                }
                ElevatedButton(onClick = { }) {
                    Text("Read")
                }
            }
        }
    }
}


@Composable
fun ImageView(modifier: Modifier = Modifier) {
    val imageModifier = Modifier
        .size(150.dp)

    val image = painterResource(R.drawable.typewriter)
    Image(
        painter = image,
        contentDescription = null,
        modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ImgspeakerTheme {
        ImageView()
    }
}