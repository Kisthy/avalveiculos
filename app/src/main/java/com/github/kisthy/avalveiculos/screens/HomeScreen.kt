package com.github.kisthy.avalveiculos.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.github.kisthy.avalveiculos.R
import com.github.kisthy.avalveiculos.components.AddPhotoButton
import com.github.kisthy.avalveiculos.components.AddVideoButton
import com.github.kisthy.avalveiculos.components.CampoFormulario
import com.github.kisthy.avalveiculos.components.PhotoPreview
import com.github.kisthy.avalveiculos.database.repository.AvaliacaoRepository
import com.github.kisthy.avalveiculos.model.Avaliacao
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(homeScreenViewModel: HomeScreenViewModel) {

    val placa by homeScreenViewModel.placa.observeAsState(initial = "")
    val chassi by homeScreenViewModel.chassi.observeAsState(initial = "")
    val marca by homeScreenViewModel.marca.observeAsState(initial = "")
    val modelo by homeScreenViewModel.modelo.observeAsState(initial = "")

    val context = LocalContext.current
    val avaliacaoRepository = AvaliacaoRepository(context)

    var fotoPlacaUri by remember { mutableStateOf<Uri?>(null) }
    var fotoChassiUri by remember { mutableStateOf<Uri?>(null) }
    var fotoHodometroUri by remember { mutableStateOf<Uri?>(null) }
    var fotoMotorUri by remember { mutableStateOf<Uri?>(null) }

    var showErrorDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text(text = stringResource(id = R.string.erro)) },
            text = { Text(text = stringResource(id = R.string.erroMensagem)) },
            confirmButton = {
                Button(onClick = { showErrorDialog = false }) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }
        )
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text(text = stringResource(id = R.string.sucesso)) },
            text = { Text(text = stringResource(id = R.string.sucessoMensagem)) },
            confirmButton = {
                Button(onClick = { showSuccessDialog = false }) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }
        )
    }

    var videoUri by remember { mutableStateOf<Uri?>(null) }
    var videoThumbnailUri by remember { mutableStateOf<Uri?>(null) }

    var errorPlaca by remember { mutableStateOf(false) }
    var errorChassi by remember { mutableStateOf(false) }
    var errorMarca by remember { mutableStateOf(false) }
    var errorModelo by remember { mutableStateOf(false) }
    var errorFotoPlaca by remember { mutableStateOf(false) }
    var errorFotoChassi by remember { mutableStateOf(false) }
    var errorFotoHodometro by remember { mutableStateOf(false) }
    var errorFotoMotor by remember { mutableStateOf(false) }
    var errorVideo by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { _ ->

    }

    LaunchedEffect(Unit) {
        requestPermissions(permissionLauncher)
    }

    var hasCameraPermission =
        remember { context.checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED }

    var showDialog by remember { mutableStateOf(!hasCameraPermission) }

    LaunchedEffect(Unit) {
        while (!hasCameraPermission) {
            delay(1000)
            hasCameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
            showDialog = !hasCameraPermission
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {  },
            title = { Text(text = stringResource(id = R.string.camError)) },
            text = { Text(text = stringResource(id = R.string.camErrorMensagem)) },
            confirmButton = {
                Button(onClick = {  }) {
                    Text(text = stringResource(id = R.string.ok))
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }

    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(colorResource(R.color.vermelho_rosa))
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.bmi),
                    contentDescription = "bmi",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(shape = CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.title),
                    color = colorResource(R.color.white),
                    fontSize = 20.sp
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {

                Card(
                    modifier = Modifier
                        .offset(y = (-30).dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xfff9f6f6)),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                            .padding(vertical = 16.dp, horizontal = 32.dp)
                            .verticalScroll(scrollState)
                    ) {
                        Text(
                            text = stringResource(id = R.string.subtitle),
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.vermelho_rosa),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // Campos do formulário
                        CampoFormulario(
                            label = stringResource(id = R.string.placaLabel),
                            placeholder = stringResource(id = R.string.placaPlace),
                            value = placa,
                            keyboardType = KeyboardType.Text,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            attValor = { homeScreenViewModel.onPlacaChanged(it) },
                            isError = errorPlaca
                        )
                        if (errorPlaca) {
                            Text(
                                text = stringResource(id = R.string.placaError),
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 14.sp,
                                color = Color.Red,
                                textAlign = TextAlign.End
                            )
                        }
                        CampoFormulario(
                            label = stringResource(id = R.string.chassiLabel),
                            placeholder = stringResource(id = R.string.chassiPlace),
                            value = chassi,
                            keyboardType = KeyboardType.Text,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            attValor = { homeScreenViewModel.onChassiChanged(it) },
                            isError = errorChassi
                        )
                        if (errorChassi) {
                            Text(
                                text = stringResource(id = R.string.chassiError),
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 14.sp,
                                color = Color.Red,
                                textAlign = TextAlign.End
                            )
                        }
                        CampoFormulario(
                            label = stringResource(id = R.string.marcaLabel),
                            placeholder = stringResource(id = R.string.marcaPlace),
                            value = marca,
                            keyboardType = KeyboardType.Text,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            attValor = { homeScreenViewModel.onMarcaChanged(it) },
                            isError = errorMarca
                        )
                        if (errorMarca) {
                            Text(
                                text = stringResource(id = R.string.marcaError),
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 14.sp,
                                color = Color.Red,
                                textAlign = TextAlign.End
                            )
                        }
                        CampoFormulario(
                            label = stringResource(id = R.string.modeloLabel),
                            placeholder = stringResource(id = R.string.modeloPlace),
                            value = modelo,
                            keyboardType = KeyboardType.Text,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            attValor = { homeScreenViewModel.onModeloChanged(it) },
                            isError = errorModelo
                        )
                        if (errorModelo) {
                            Text(
                                text = stringResource(id = R.string.modeloError),
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 14.sp,
                                color = Color.Red,
                                textAlign = TextAlign.End
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Row() {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = stringResource(id = R.string.placa))
                                AddPhotoButton { uri -> fotoPlacaUri = uri }
                                PhotoPreview(fotoPlacaUri)
                                if (errorFotoPlaca) {
                                    Text(
                                        text = stringResource(id = R.string.photoError),
                                        fontSize = 14.sp,
                                        color = Color.Red,
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(2.dp))
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = stringResource(id = R.string.chassi))
                                AddPhotoButton { uri -> fotoChassiUri = uri }
                                PhotoPreview(fotoChassiUri)
                                if (errorFotoChassi) {
                                    Text(
                                        text = stringResource(id = R.string.photoError),
                                        fontSize = 14.sp,
                                        color = Color.Red,
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))
                        Row() {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = stringResource(id = R.string.hodometro))
                                AddPhotoButton { uri -> fotoHodometroUri = uri }
                                PhotoPreview(fotoHodometroUri)
                                if (errorFotoHodometro) {
                                    Text(
                                        text = stringResource(id = R.string.photoError),
                                        fontSize = 14.sp,
                                        color = Color.Red,
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(2.dp))
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Text(text = stringResource(id = R.string.motor))
                                AddPhotoButton { uri -> fotoMotorUri = uri }
                                PhotoPreview(fotoMotorUri)
                                if (errorFotoMotor) {
                                    Text(
                                        text = stringResource(id = R.string.photoError),
                                        fontSize = 14.sp,
                                        color = Color.Red,
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                        Row() {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                AddVideoButton(
                                    onVideoSelected = { uri -> videoUri = uri },
                                    onThumbnailReady = { uri -> videoThumbnailUri = uri }
                                )
                                if (videoThumbnailUri != null) {
                                    Image(
                                        painter = rememberAsyncImagePainter(videoThumbnailUri),
                                        contentDescription = "Thumbnail do vídeo",
                                        modifier = Modifier.size(50.dp)
                                    )
                                } else {
                                    Text(
                                        text = stringResource(id = R.string.videoNull),
                                    )
                                }
                                if (errorVideo) {
                                    Text(
                                        text = stringResource(id = R.string.videoError),
                                        fontSize = 14.sp,
                                        color = Color.Red,
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))
                        Button(
                            onClick = {
                                if (placa.isEmpty()) errorPlaca = true else errorPlaca = false
                                if (chassi.isEmpty()) errorChassi = true else errorChassi = false
                                if (marca.isEmpty()) errorMarca = true else errorMarca = false
                                if (modelo.isEmpty()) errorModelo = true else errorModelo = false
                                if (fotoPlacaUri == null) errorFotoPlaca =
                                    true else errorFotoPlaca = false
                                if (fotoChassiUri == null) errorFotoChassi =
                                    true else errorFotoChassi = false
                                if (fotoHodometroUri == null) errorFotoHodometro =
                                    true else errorFotoHodometro = false
                                if (fotoMotorUri == null) errorFotoMotor =
                                    true else errorFotoMotor = false
                                if (videoUri == null) errorVideo = true else errorVideo = false

                                if (placa.isEmpty() || chassi.isEmpty() || marca.isEmpty() || modelo.isEmpty() ||
                                    fotoPlacaUri == null || fotoChassiUri == null || fotoHodometroUri == null ||
                                    fotoMotorUri == null || videoUri == null
                                ) {
                                    showErrorDialog = true
                                } else {
                                    val avaliacao = Avaliacao(
                                        id = 0,
                                        placa = placa,
                                        chassi = chassi,
                                        marca = marca,
                                        modelo = modelo,
                                        fotoPlaca = fotoPlacaUri.toString(),
                                        fotoChassi = fotoChassiUri.toString(),
                                        fotoHodometro = fotoHodometroUri.toString(),
                                        fotoMotor = fotoMotorUri.toString(),
                                        video = videoUri.toString()
                                    )
                                    avaliacaoRepository.salvar(avaliacao)
                                    showSuccessDialog = true
                                }


                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.vermelho_rosa))
                        ) {
                            Text(
                                text = stringResource(id = R.string.save),
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun requestPermissions(permissionLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>) {
    permissionLauncher.launch(
        arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    )
}
