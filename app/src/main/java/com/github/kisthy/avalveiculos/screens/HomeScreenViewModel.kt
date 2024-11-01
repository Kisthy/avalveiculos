package com.github.kisthy.avalveiculos.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeScreenViewModel: ViewModel() {
    private val _placa = MutableLiveData<String>()
    val placa: LiveData<String> = _placa

    private val _chassi = MutableLiveData<String>()
    val chassi: LiveData<String> = _chassi

    private val _marca = MutableLiveData<String>()
    val marca: LiveData<String> = _marca

    private val _modelo = MutableLiveData<String>()
    val modelo: LiveData<String> = _modelo


    fun onPlacaChanged(novaPlaca: String){
        _placa.value = novaPlaca
    }

    fun onChassiChanged(novoChassi: String){
        _chassi.value = novoChassi
    }

    fun onMarcaChanged(novaMarca: String){
        _marca.value = novaMarca
    }

    fun onModeloChanged(novoModelo: String){
        _modelo.value = novoModelo
    }
}