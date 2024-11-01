package com.github.kisthy.avalveiculos.screens

import android.widget.VideoView
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberAsyncImagePainter
import com.github.kisthy.avalveiculos.R
import com.github.kisthy.avalveiculos.database.repository.AvaliacaoRepository

@Composable
fun DetailsScreen(avaliacaoId: Long) {

    val context = LocalContext.current
    val avaliacaoRepository = AvaliacaoRepository(context)

    val detalhesAvaliacoes = avaliacaoRepository.detalhesAvaliacao(avaliacaoId)

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
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
                    text = stringResource(id = R.string.titleHistory),
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
                    colors = CardDefaults
                        .cardColors(containerColor = Color(0xfff9f6f6)),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(
                            vertical = 16.dp,
                            horizontal = 32.dp
                        ).verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = detalhesAvaliacoes.placa,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.vermelho_rosa),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = detalhesAvaliacoes.chassi,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.vermelho_rosa),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = "${detalhesAvaliacoes.marca} - ${detalhesAvaliacoes.modelo} ",
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.vermelho_rosa),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = stringResource(id = R.string.placa))
                                Image(
                                    painter = rememberAsyncImagePainter(detalhesAvaliacoes.fotoPlaca),
                                    contentDescription = "Foto da Placa",
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = stringResource(id = R.string.chassi))
                                Image(
                                    painter = rememberAsyncImagePainter(detalhesAvaliacoes.fotoPlaca),
                                    contentDescription = "Foto da Placa",
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                        }
                        Spacer(Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = stringResource(id = R.string.hodometro))
                                Image(
                                    painter = rememberAsyncImagePainter(detalhesAvaliacoes.fotoPlaca),
                                    contentDescription = "Foto da Placa",
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = stringResource(id = R.string.motor))
                                Image(
                                    painter = rememberAsyncImagePainter(detalhesAvaliacoes.fotoPlaca),
                                    contentDescription = "Foto da Placa",
                                    modifier = Modifier.size(50.dp)
                                )
                            }
                        }
                        Spacer(Modifier.height(16.dp))
                        Text(text = stringResource(id = R.string.video))
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White)
                        ) {
                            AndroidView(factory = { context ->
                                VideoView(context).apply {
                                    setVideoPath(detalhesAvaliacoes.video)
                                    setOnPreparedListener { mediaPlayer ->
                                        mediaPlayer.setOnCompletionListener {

                                        }
                                    }
                                    start()
                                }
                            }, modifier = Modifier.fillMaxSize())
                        }
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = detalhesAvaliacoes.data.toString(),
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.vermelho_rosa),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}