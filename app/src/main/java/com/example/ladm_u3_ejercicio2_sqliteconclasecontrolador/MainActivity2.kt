package com.example.ladm_u3_ejercicio2_sqliteconclasecontrolador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ladm_u3_ejercicio2_sqliteconclasecontrolador.databinding.ActivityMain2Binding
import com.example.ladm_u3_ejercicio2_sqliteconclasecontrolador.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    var noControl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        noControl= this.intent.extras!!.getString("noControl")!!
        val alumno=Alumno(this).mostrarAlumno(noControl)

        binding.etNombre.setText(alumno.nombre)
        binding.etCarrera.setText(alumno.carrera)

        binding.btActualizar.setOnClickListener {
            var alumno=Alumno(this)

            alumno.noControl = noControl
            alumno.nombre=binding.etNombre.text.toString()
            alumno.carrera=binding.etCarrera.text.toString()

            val respuesta = alumno.actualizar()

            if(respuesta){
                Toast.makeText(this,"Se actualizo Correctamente",Toast.LENGTH_SHORT).show()
                binding.etNombre.setText("")
                binding.etCarrera.setText("")
                finish()
            }else{
                AlertDialog.Builder(this)
                    .setTitle("Atencion")
                    .setMessage("Error no se Actualizo")
                    .show( )
            }
        }

        binding.btRegresar.setOnClickListener {
            finish()
        }

    }
}