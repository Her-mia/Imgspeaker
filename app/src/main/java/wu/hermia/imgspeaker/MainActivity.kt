package wu.hermia.imgspeaker

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {

    private var imageUri: Uri? = null

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

    val REQUEST_IMAGE_CAPTURE = 101


    public fun dispatchTakePictureIntent() {
        imageUri = null
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, "New Picture")
            values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera")
            imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }

    }
}


@Composable
fun MyApp(modifier: Modifier = Modifier) {
    Column(modifier) {
        MyImageView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 8.dp)
                    .fillMaxWidth()
                    .height(72.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val context = LocalContext.current
                val activity = context as? MainActivity
                ElevatedButton(onClick = {
                    activity?.dispatchTakePictureIntent()
                }) {
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
fun MyImageView(modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.typewriter)
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
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
