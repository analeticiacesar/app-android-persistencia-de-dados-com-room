package br.com.alura.orgs.extensions

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

fun BigDecimal.formatForBrazilianCurrency(): String {
    val formatter: NumberFormat = NumberFormat
        .getCurrencyInstance(Locale("pt", "br"))
    return formatter.format(this)
}