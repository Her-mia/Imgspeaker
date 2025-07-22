package wu.hermia.imgspeaker

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import wu.hermia.imgspeaker.ui.theme.ImgspeakerTheme


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
    val context = LocalContext.current
    fun createImageUri(): Uri? {
        val contentValues = ContentValues().apply {
            put(
                MediaStore.Images.Media.DISPLAY_NAME,
                "camera_demo_${System.currentTimeMillis()}.jpg"
            )
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        return context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
    }


    fun runTextRecognition() {
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        mTextButton.setEnabled(false)
        recognizer.process(image)
            .addOnSuccessListener { texts ->
                mTextButton.setEnabled(true)
                processTextRecognitionResult(texts)
            }
            .addOnFailureListener { e -> // Task failed with an exception
                mTextButton.setEnabled(true)
                e.printStackTrace()
            }
    }

    fun processTextRecognitionResult(texts: Text) {
        val blocks = texts.textBlocks
        if (blocks.size == 0) {
            showToast("No text found")
            return
        }
        mGraphicOverlay.clear()
        for (i in blocks.indices) {
            val lines = blocks[i].lines
            for (j in lines.indices) {
                val elements = lines[j].elements
                for (k in elements.indices) {
                    val textGraphic: Graphic = TextGraphic(mGraphicOverlay, elements[k])
                    mGraphicOverlay.add(textGraphic)
                }
            }
        }
    }



    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var tmpUri: Uri? = null
    var image = painterResource(R.drawable.typewriter)
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            imageUri = tmpUri
        }
    }
    imageUri?.let { uri ->
        println(uri)
        image = rememberAsyncImagePainter(uri)
    }

    Column(modifier) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentScale = ContentScale.FillBounds
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
                ElevatedButton(onClick = {
                    tmpUri = createImageUri()
                    tmpUri?.let { uri ->
                        cameraLauncher.launch(uri)

                    }
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