package br.com.alura.orgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import br.com.alura.orgs.databinding.DialogImageFormBinding
import br.com.alura.orgs.extensions.tryToLoadImage

class ImageFormDialog(private val context: Context) {

    fun show(
        defaultUrl: String? = null,
        imageLoad: (imagem: String) -> Unit
    )  {
        DialogImageFormBinding
            .inflate(LayoutInflater.from(context)).apply {

                defaultUrl?.let {
                    formularioImagemImageview.tryToLoadImage(it)
                    formularioImagemUrl.setText(it)
                }

                formularioImagemBotaoCarregar.setOnClickListener {
                    val url = formularioImagemUrl.text.toString()
                    formularioImagemImageview.tryToLoadImage(url)
                }

                AlertDialog.Builder(context)
                    .setView(root)
                    .setPositiveButton("Confirmar") { _, _ ->
                        val url = formularioImagemUrl.text.toString()
                        imageLoad(url)
                    }
                    .setNegativeButton("Cancelar") { _, _ ->

                    }
                    .show()
            }
    }
}