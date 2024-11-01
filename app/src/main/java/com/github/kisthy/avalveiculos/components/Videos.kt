package com.github.kisthy.avalveiculos.components

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.core.content.FileProvider
import com.arthenica.ffmpegkit.FFmpegKit
import com.arthenica.ffmpegkit.ReturnCode
import com.github.kisthy.avalveiculos.R
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AddVideoButton(
    onVideoSelected: (Uri) -> Unit,
    onThumbnailReady: (Uri?) -> Unit
) {
    val context = LocalContext.current
    val videoUri = remember { mutableStateOf<Uri?>(null) }
    val thumbnailUri = remember { mutableStateOf<Uri?>(null) }
    var isFlashEnabled by remember { mutableStateOf(false) }

    val recordVideoLauncher = rememberLauncherForActivityResult(ActivityResultContracts.CaptureVideo()) { isSuccess ->
        if (isSuccess) {
            videoUri.value?.let { uri ->
                onVideoSelected(uri)

                generateVideoThumbnail(uri, context) { generatedThumbnailUri ->
                    thumbnailUri.value = generatedThumbnailUri
                    onThumbnailReady(generatedThumbnailUri)
                }
            }
        }
    }

    val pickVideoLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            onVideoSelected(it)
            videoUri.value = it
            generateVideoThumbnail(it, context) { generatedThumbnailUri ->
                thumbnailUri.value = generatedThumbnailUri
                onThumbnailReady(generatedThumbnailUri)
            }
        }
    }

    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = {
                    val videoFile = createVideoFile(context)
                    val uri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.provider",
                        videoFile
                    )
                    videoUri.value = uri
                    recordVideoLauncher.launch(uri)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_video),
                    contentDescription = "Gravar Vídeo",
                    tint = colorResource(id = R.color.black)
                )
            }

            IconButton(onClick = { isFlashEnabled = !isFlashEnabled }) {
                Icon(
                    painter = painterResource(id = if (isFlashEnabled) R.drawable.ic_flash_on else R.drawable.ic_flash_off),
                    contentDescription = if (isFlashEnabled) "Flash On" else "Flash Off",
                    tint = colorResource(id = R.color.black)
                )
            }

            IconButton(
                onClick = { pickVideoLauncher.launch("video/*") }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_gallery),
                    contentDescription = "Selecionar Vídeo",
                    tint = colorResource(id = R.color.black)
                )
            }
        }
    }
}


private fun createVideoFile(context: Context): File {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES) ?: context.filesDir
    return File.createTempFile(
        "VIDEO_${timeStamp}_",
        ".mp4",
        storageDir
    )
}

private fun generateVideoThumbnail(videoUri: Uri, context: Context, onThumbnailReady: (Uri?) -> Unit) {
    val videoFile = getFileFromUri(context, videoUri)
    val outputDir = context.cacheDir
    val outputFile = File(outputDir, "thumbnail_${System.currentTimeMillis()}.jpg")
    val command = arrayOf(
        "-i", videoFile.absolutePath,
        "-ss", "00:00:00.100",
        "-vframes", "1",
        outputFile.absolutePath
    )

    FFmpegKit.executeAsync(command.joinToString(" ")) { session ->
        val returnCode = session.returnCode
        if (ReturnCode.isSuccess(returnCode) && outputFile.exists() && outputFile.length() > 0) {
            onThumbnailReady(Uri.fromFile(outputFile))
        } else {
            onThumbnailReady(null)
        }
    }
}


private fun getFileFromUri(context: Context, uri: Uri): File {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    val tempFile = File(context.cacheDir, getFileName(context, uri) ?: "temp_video.mp4")
    inputStream?.use { input ->
        FileOutputStream(tempFile).use { output ->
            input.copyTo(output)
        }
    }
    return tempFile
}

private fun getFileName(context: Context, uri: Uri): String? {
    var name: String? = null
    context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex != -1) {
                name = cursor.getString(nameIndex)
            }
        }
    }
    return name
}
