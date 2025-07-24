package wu.hermia.imgspeaker

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
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

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var texts by remember { mutableStateOf<Text?>(null) }
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

        fun processTextRecognitionResult(texts: Text) {
            Log.e("processTextRecognitionResult", texts.toString())
            val blocks = texts.textBlocks
            for (i in blocks.indices) {
                val lines = blocks[i].lines
                for (j in lines.indices) {
                    val elements = lines[j].elements
                    for (k in elements.indices) {
                        elements[k].text
                    }
                }
            }
        }


        val inputImage = InputImage.fromFilePath(context, uri)
        Log.e("uri", uri.toString())
        Log.e("inputImage", inputImage.toString())
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
        recognizer.process(inputImage)
            .addOnSuccessListener {
                OnSuccessListener<Text?> {
                    texts ->
                        Log.e("addOnSuccessListener", texts.toString())
                        texts?.let { it1 -> processTextRecognitionResult(it1) }
                    }
                }.addOnFailureListener(
                    OnFailureListener {
                        e -> e.printStackTrace()
                    }
                )

    }

//    fun processTextRecognitionResult(texts: Text) {
//        Log.e("processTextRecognitionResult", texts.toString())
//        val blocks = texts.textBlocks
//        for (i in blocks.indices) {
//            val lines = blocks[i].lines
//            for (j in lines.indices) {
//                val elements = lines[j].elements
//                for (k in elements.indices) {
//                    elements[k].text
//                }
//            }
//        }
//    }
//
//
//    val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.typewriter)
//    var inputImage = InputImage.fromBitmap(bitmap, 0)
//    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
//    recognizer.process(inputImage)
//        .addOnSuccessListener {
//            Log.e("addOnSuccessListener", texts.toString())
//            texts?.let { it1 -> processTextRecognitionResult(it1) }
//        }
//
//    Log.e("TextRecognition.getClient ", texts.toString())



    val textMeasurer = rememberTextMeasurer()
    Column(modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        )
        {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillBounds
            )
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val layoutResult = textMeasurer.measure(
                    text = AnnotatedString("Hello!"),
                )

                val topLeft = Offset(350f, 550f)
                val textSize = layoutResult.size.toSize()

                // 绘制矩形边框
                drawRect(
                    color = Color.Blue,
                    topLeft = topLeft,
                    size = Size(
                        width = textSize.width + 30f * 2,
                        height = textSize.height + 30f * 2
                    ),
                    style = Stroke(width = 3f)
                )

                // 绘制文字
                drawText(
                    textLayoutResult = layoutResult,
                    color = Color.Blue,
                    topLeft = topLeft
                )
            }
//                    .drawWithCache {
//                        val path = Path()
//                        path.moveTo(350f, 550f)
//                        path.lineTo(350f, 600f)
//                        path.lineTo(450f, 600f)
//                        path.lineTo(450f,550f)
//                        path.close()
//                        onDrawBehind {
//                            drawPath(path, Color.Blue, style = Stroke(width = 2f))
//                        }
//                    }
//            ) {translate(left = 350f, top = 550f) {
//                drawText(textMeasurer, "Hello")}
//            }
        }

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
                ElevatedButton(onClick = {

                }) {
                    Text("Read")
                }
            }
        }
    }
}