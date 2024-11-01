package com.github.kisthy.avalveiculos.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.github.kisthy.avalveiculos.R

@Composable
fun CampoFormulario(
    label: String,
    placeholder: String,
    value: String,
    keyboardType: KeyboardType,
    modifier: Modifier,
    attValor: (String) -> Unit,
    isError: Boolean,
) {
    OutlinedTextField(
        value = value,
        onValueChange = attValor,
        modifier = modifier,
        label = {
            Text(text = label)
        },
        placeholder = {
            Text(text = placeholder)
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = colorResource(id = R.color.vermelho_rosa),
            focusedBorderColor = colorResource(id = R.color.cinza)
        ),
        shape = RoundedCornerShape(16.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        isError = isError
    )
}