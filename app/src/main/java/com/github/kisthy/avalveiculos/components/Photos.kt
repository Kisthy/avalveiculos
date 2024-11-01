package com.github.kisthy.avalveiculos.components

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.github.kisthy.avalveiculos.R
import java.io.File
import java.util.Date
import java.util.Locale

@Composable
fun AddPhotoButton(
    onImageSelected: (Uri) -> Unit
) {
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }

    var isFlashEnabled by remember { mutableStateOf(false) }

    val takePictureLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                imageUri.value?.let { onImageSelected(it) }
            }
        }
    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { onImageSelected(it) }
        }

    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = {
                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    createImageFile(context)
                )
                imageUri.value = uri
                takePictureLauncher.launch(uri)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = "Camera",
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
            onClick = { pickImageLauncher.launch("image/*") }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_gallery),
                contentDescription = "Camera",
                tint = colorResource(id = R.color.black)
            )
        }
    }
}

private fun createImageFile(context: Context): File {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir: File = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
    return File.createTempFile(
        "JPEG_${timeStamp}_",
        ".jpg",
        storageDir
    )
}

@Composable
fun PhotoPreview(uri: Uri?) {
    uri?.let {
        Image(
            painter = rememberAsyncImagePainter(it),
            contentDescription = null,
            modifier = Modifier.size(50.dp)
        )
    } ?: run {
        Text(text = stringResource(id = R.string.photoNull))
    }
}

