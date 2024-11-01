package com.github.kisthy.avalveiculos.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.kisthy.avalveiculos.model.Avaliacao

@Dao
interface AvaliacaoDao {

    @Insert
    fun salvar(avaliacao: Avaliacao): Long

    @Query("SELECT * FROM tbl_avaliacao WHERE id = :id")
    fun detalhesAvaliacao(id: Long): Avaliacao

    @Query("SELECT * FROM tbl_avaliacao ORDER BY placa ASC")
    fun listarAvaliacoes(): List<Avaliacao>

}