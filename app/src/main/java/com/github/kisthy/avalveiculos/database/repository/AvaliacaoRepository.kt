package com.github.kisthy.avalveiculos.database.repository

import android.content.Context
import com.github.kisthy.avalveiculos.database.dao.AvaliacaoDb
import com.github.kisthy.avalveiculos.model.Avaliacao

class AvaliacaoRepository(context: Context) {

    var db = AvaliacaoDb.getDatabase(context).avaliacaoDao()

    fun salvar(avaliacao: Avaliacao): Long{
        return db.salvar(avaliacao = avaliacao)
    }

    fun detalhesAvaliacao(id: Long): Avaliacao{
        return db.detalhesAvaliacao(id = id)
    }

    fun listarAvaliacoes(): List<Avaliacao>{
        return db.listarAvaliacoes()
    }

}