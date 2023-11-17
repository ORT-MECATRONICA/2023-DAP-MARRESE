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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import sharedData

class Editar : Fragment() {

    private lateinit var viewModel: EditarViewModel

    private lateinit var idCompartido: sharedData
    private var db = Firebase.firestore

    private lateinit var cancionNueva: EditText
    private lateinit var cantanteNuevo: EditText
    private lateinit var anoNuevo: EditText
    private lateinit var photoNuevo: EditText

    private lateinit var IDcancion: String

    private lateinit var botonSubirDatos: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_editar, container, false)

        cancionNueva = view.findViewById(R.id.cancionNueva)
        cantanteNuevo = view.findViewById(R.id.cantanteNuevo)
        anoNuevo = view.findViewById(R.id.anoNuevo)
        photoNuevo = view.findViewById(R.id.photoNuevo)

        botonSubirDatos = view.findViewById(R.id.botonSubirDatos)

        db = FirebaseFirestore.getInstance()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.observe(viewLifecycleOwner) { data1 ->

            db.collection("Canciones").document(data1).get().addOnSuccessListener {

                cancionNueva.setText(it.data?.get("cancion").toString())
                cantanteNuevo.setText(it.data?.get("cantante").toString())
                anoNuevo.setText(it.data?.get("ano").toString())
                photoNuevo.setText(it.data?.get("photo").toString())
                IDcancion = it.data?.get("idFirebase").toString()

            }.addOnFailureListener {
                Toast.makeText(context, "no se encontraron datos", Toast.LENGTH_SHORT).show()
            }

            botonSubirDatos.setOnClickListener {

                val superHeroeNuevo = hashMapOf(
                    "cancion" to cancionNueva.text.toString(),
                    "cantante" to cantanteNuevo.text.toString(),
                    "ano" to anoNuevo.text.toString(),
                    "photo" to photoNuevo.text.toString(),
                    "idFirebase" to IDcancion
                )

                db.collection("Canciones").document(data1).set(superHeroeNuevo)
                    .addOnSuccessListener {
                        Toast.makeText(context, "subido", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "no se pudo subir", Toast.LENGTH_SHORT).show()
                    }

                findNavController().navigate(R.id.action_editar_to_inicio)
            } }

        return view
    }

}