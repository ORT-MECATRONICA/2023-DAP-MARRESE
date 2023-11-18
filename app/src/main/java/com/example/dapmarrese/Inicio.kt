package com.example.dapmarrese

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tpfinaldap.recycleviewclasses.MusicAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import sharedData

class Inicio : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var musicList: ArrayList<Musica>
    private var db = Firebase.firestore
    private lateinit var botonAdd: Button
    private lateinit var idMusica: String
    private lateinit var idCompartido: sharedData

    private lateinit var adapter : MusicAdapter

    private lateinit var viewModel: InicioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)

        db = FirebaseFirestore.getInstance()
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        musicList = arrayListOf()
        botonAdd = view.findViewById(R.id.botonNuevo)

        initRecyclerView()

        botonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_inicio_to_crear)
        }

        return view
    }

    private fun initRecyclerView() {
        db.collection("Canciones").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val superHero:Musica? = data.toObject<Musica>(Musica::class.java)
                    musicList.add(superHero!!)
                }

                adapter = MusicAdapter(musicList,
                    onDeleteClick = {position -> deleteHero(position)
                    },
                    onEditClick = {position -> editSuperHero(position)
                    },
                    onItemClick = {position -> seeSuperHeroData(position)})

                recyclerView.adapter = adapter
            }
        }.addOnFailureListener {
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun seeSuperHeroData(position:Int) {

        idMusica = musicList[position].idFirebase.toString()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.value = idMusica

        findNavController().navigate(R.id.action_inicio_to_informacion)
    }

    fun editSuperHero(position: Int) {
        idMusica = musicList[position].idFirebase.toString()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.value = idMusica

        findNavController().navigate(R.id.action_inicio_to_editar)
    }

    fun deleteHero (position : Int){

        db.collection("Canciones").document(musicList[position].idFirebase.toString()).delete()
            .addOnSuccessListener {
                Toast.makeText(requireContext(),"Cancion eliminada", Toast.LENGTH_SHORT).show()
                adapter.notifyItemRemoved(position)
                musicList.removeAt(position)
            }
            .addOnFailureListener { Toast.makeText(requireContext(),"No se puedo eliminar la cancion", Toast.LENGTH_SHORT).show() }
    }

}