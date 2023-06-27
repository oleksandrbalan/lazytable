package eu.wewox.lazytable.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import eu.wewox.lazytable.data.ktorHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.jetbrains.skia.Image

@Composable
actual fun AsyncImage(model: String, contentDescription: String?) {
    var imageBitmap: ImageBitmap? by remember { mutableStateOf(null) }

    imageBitmap?.let {
        Image(
            bitmap = it,
            contentDescription = "",
        )
    }

    LaunchedEffect(model) {
        loadPicture(model)
            .onSuccess {
                imageBitmap = it
            }
    }
}

private suspend fun loadPicture(url: String): Result<ImageBitmap> {
    val client = ktorHttpClient
    return try {
        val image: ByteArray = client.get(url).body()
        Result.success(Image.makeFromEncoded(image).toComposeImageBitmap())
    } catch (e: Exception) {
        Result.failure(e)
    }
}
