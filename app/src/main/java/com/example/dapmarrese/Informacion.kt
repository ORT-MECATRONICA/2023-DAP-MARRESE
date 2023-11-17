package com.example.dapmarrese

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import sharedData

class Informacion : Fragment() {

    private lateinit var viewModel: InformacionViewModel
    private lateinit var idCompartido: sharedData
    private var db = Firebase.firestore

    private lateinit var cancionData: TextView
    private lateinit var cantanteData: TextView
    private lateinit var anoData: TextView
    private lateinit var photoData: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_informacion, container, false)

        cancionData = view.findViewById(R.id.cancionData)
        cantanteData = view.findViewById(R.id.cantanteData)
        anoData = view.findViewById(R.id.anoData)
        photoData = view.findViewById(R.id.photoData)

        db = FirebaseFirestore.getInstance()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.observe(viewLifecycleOwner) { data1 ->

            db.collection("Canciones").document(data1).get().addOnSuccessListener {

                cancionData.text = (it.data?.get("cancion").toString())
                cantanteData.text = (it.data?.get("cantante").toString())
                anoData.text = (it.data?.get("ano").toString())
                Glide.with(photoData.context).load(it.data?.get("photo").toString()).into(photoData)

            }.addOnFailureListener {
                Toast.makeText(context, "no se encontraron datos", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

}