package com.example.dapmarrese

import android.content.ContentValues
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Registrar : Fragment() {

    lateinit var botonRegistrar: Button
    lateinit var mail: EditText
    lateinit var password: EditText
    lateinit var datoMail: String
    lateinit var datoPassword: String
    private lateinit var auth: FirebaseAuth

    private lateinit var viewModel: RegistrarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registrar, container, false)

        botonRegistrar = view.findViewById(R.id.BotonRegister)
        mail = view.findViewById(R.id.textUsuario)
        password = view.findViewById(R.id.textContraseña)

        botonRegistrar.setOnClickListener {

            datoMail = mail.text.toString()
            datoPassword = password.text.toString()

            auth = Firebase.auth
            auth.createUserWithEmailAndPassword(datoMail, datoPassword).addOnCompleteListener {  }
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(ContentValues.TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        findNavController().navigate(R.id.action_registrar2_to_inicio)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(context, "Su mail ya esta registrado o no existe", Toast.LENGTH_SHORT,).show()
                    } } }

        return view

    }

}