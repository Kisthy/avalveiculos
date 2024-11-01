package com.github.kisthy.avalveiculos.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.github.kisthy.avalveiculos.R
import com.github.kisthy.avalveiculos.model.Avaliacao

@Composable
fun CardAvaliacao(avaliacao: Avaliacao, navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(2f)
            ) {
                Text(
                    text = avaliacao.placa,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${avaliacao.marca} - ${avaliacao.modelo} ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = avaliacao.data.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            OutlinedButton(
                onClick = {
                    navController.navigate("details/${avaliacao.id}")
                },
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.visualizar),
                    color = colorResource(id = R.color.vermelho_rosa)
                )
            }
        }
    }
}
