package com.example.semana09dpafirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = FirebaseFirestore.getInstance()
        val tvCurso: TextView = findViewById(R.id.tvCurso)
        val tvNota: TextView = findViewById(R.id.tvNota)

        db.collection("courses")
            .addSnapshotListener{snapshots, e ->
                if(e!=null){
                    Snackbar
                        .make(findViewById(android.R.id.content),
                            "Error al conectarse a firestore",
                            Snackbar.LENGTH_LONG).show()
                    return@addSnapshotListener
                }

                for(dc in snapshots!!.documentChanges){
                    when(dc.type){
                        DocumentChange.Type.ADDED, DocumentChange.Type.MODIFIED -> {
                            Snackbar
                                .make(findViewById(android.R.id.content),
                                    "Consultando...",
                                    Snackbar.LENGTH_LONG).show()
                            tvCurso.text = dc.document.data["description"].toString()
                            tvNota.text = dc.document.data["score"].toString()
                        }

                        DocumentChange.Type.REMOVED -> {
                            Snackbar
                                .make(findViewById(android.R.id.content),
                                    "El documento fue eliminado",
                                    Snackbar.LENGTH_LONG).show()
                        }
                        else -> {
                            Snackbar
                                .make(findViewById(android.R.id.content),
                                    "Error al consultar la colección",
                                    Snackbar.LENGTH_LONG).show()
                        }
                    }

                }

            }
    }
}