package eu.wewox.lazytable.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import eu.wewox.lazytable.data.ktorHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.jetbrains.skia.Image

@Composable
actual fun AsyncImage(model: String, contentDescription: String?) {
    val imageBitmap by produceState<ImageBitmap?>(null, model) {
        value = loadPicture(model).getOrNull()
    }

    imageBitmap?.let {
        Image(
            bitmap = it,
            contentDescription = contentDescription,
        )
    }
}

private suspend fun loadPicture(url: String): Result<ImageBitmap> =
    try {
        val image: ByteArray = ktorHttpClient.get(url).body()
        Result.success(Image.makeFromEncoded(image).toComposeImageBitmap())
    } catch (e: Exception) {
        Result.failure(e)
    }
