package com.example.dapmarrese

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Crear : Fragment() {

    private lateinit var textCancion: EditText
    private lateinit var textCantante: EditText
    private lateinit var textAno: EditText
    private lateinit var textFoto: EditText
    private var db = Firebase.firestore
    private lateinit var botonSubir: Button

    private lateinit var viewModel: CrearViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crear, container, false)

        textCancion = view.findViewById(R.id.textoCancion)
        textCantante = view.findViewById(R.id.textoCantante)
        textAno = view.findViewById(R.id.textoAno)
        textFoto = view.findViewById(R.id.textoFoto)
        botonSubir = view.findViewById(R.id.botonSubir)

        botonSubir.setOnClickListener {

            val documentId:String = db.collection("Canciones").document().id

            val superHeroeNuevo = hashMapOf(
                "cancion" to textCancion.text.toString(),
                "cantante" to textCantante.text.toString(),
                "ano" to textAno.text.toString(),
                "photo" to textFoto.text.toString(),
                "idFirebase" to documentId
            )

            db.collection("Canciones").document(documentId).set(superHeroeNuevo)
                .addOnSuccessListener {
                    Toast.makeText(context, "subido", Toast.LENGTH_SHORT).show()}
                .addOnFailureListener { e ->
                    Toast.makeText(context, "no se pudo subir", Toast.LENGTH_SHORT).show() }

            findNavController().navigate(R.id.action_crear_to_inicio)
        }


        return view
    }

}