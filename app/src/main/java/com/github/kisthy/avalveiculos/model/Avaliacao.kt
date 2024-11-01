package com.github.kisthy.avalveiculos.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


@Entity(tableName = "tbl_avaliacao")
data class Avaliacao(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var placa: String = "",
    var chassi: String = "",
    var marca: String = "",
    var modelo: String = "",
    var data: LocalDate = LocalDate.now(),

    @ColumnInfo(name =  "foto_placa") var fotoPlaca: String = "",
    @ColumnInfo(name =  "foto_chassi") var fotoChassi: String = "",
    @ColumnInfo(name =  "foto_hodometro") var fotoHodometro: String = "",
    @ColumnInfo(name =  "foto_motor") var fotoMotor: String = "",

    var video: String = ""
)
