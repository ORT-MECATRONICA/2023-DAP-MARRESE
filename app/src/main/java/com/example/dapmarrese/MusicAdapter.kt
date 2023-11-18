package com.example.tpfinaldap.recycleviewclasses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dapmarrese.Musica
import com.example.dapmarrese.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MusicAdapter(
    musicList: ArrayList<Musica>,
    private val onDeleteClick : (Int)->Unit,
    private val onEditClick : (Int) -> Unit,
    private val onItemClick: (Int) -> Unit

): RecyclerView.Adapter<MusicAdapter.MusicViewHolder>(){
    private var musicList: ArrayList<Musica>

    init {
        this.musicList  = musicList
    }

    class MusicViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val cancion= view.findViewById<TextView>(R.id.textoCancion)
        val cantante= view.findViewById<TextView>(R.id.textoCantante)
        val ano= view.findViewById<TextView>(R.id.textoAno)
        val photo = view.findViewById<ImageView>(R.id.foto)
        val editar = view.findViewById<Button>(R.id.botonEditar)
        val eliminar = view.findViewById<Button>(R.id.botonEliminar)

        fun render(musicModel: Musica){
            cancion.text = musicModel.cancion
            cantante.text = musicModel.cantante
            ano.text = musicModel.ano

            Glide.with(photo.context).load(musicModel.photo).into(photo)

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MusicViewHolder(layoutInflater.inflate(R.layout.item, parent, false))
    }
    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val item = musicList[position]
        holder.render(item)
        holder.eliminar.setOnClickListener {
            onDeleteClick(position)
        }
        holder.editar.setOnClickListener {
            onEditClick(position)
        }
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
        holder.photo.setOnClickListener {
            onItemClick(position)
        }
    }
    override fun getItemCount(): Int = musicList.size

}