package br.com.alura.orgs.database.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

class Converters {

    @TypeConverter
    fun fromDoubleToBigDecimal(valor: Double?) : BigDecimal {
        return valor?.let { BigDecimal(valor.toString()) } ?: BigDecimal.ZERO
    }

    @TypeConverter
    fun fromBigDecimalToDouble(valor: BigDecimal?) : Double? {
        return valor?.let { valor.toDouble() }
    }

}