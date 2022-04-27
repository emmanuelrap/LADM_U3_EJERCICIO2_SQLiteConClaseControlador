package com.example.ladm_u3_ejercicio2_sqliteconclasecontrolador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ladm_u3_ejercicio2_sqliteconclasecontrolador.databinding.ActivityMainBinding

//Esta clase es VISTA, osea Interfaz Grafica
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var listaIds=ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mostrarDatosEnListView()
        binding.etNoControl.setText("")
        binding.etNombre.setText("")
        binding.etCarrera.setText("")

        binding.btInsertar.setOnClickListener {
            var alumno = Alumno(this) // Alumno es clase Cotrolador = Administracion de datos
            alumno.noControl=binding.etNoControl.text.toString()
            alumno.nombre=binding.etNombre.text.toString()
            alumno.carrera=binding.etCarrera.text.toString()

            val resultado=alumno.insertar() //para el Mainactivity la insercion es abstracta
            if(resultado){
                Toast.makeText(this,"Se inserto correctamente",Toast.LENGTH_SHORT).show()
                mostrarDatosEnListView()
                binding.etNoControl.setText("")
                binding.etNombre.setText("")
                binding.etCarrera.setText("")
            }else{
                AlertDialog.Builder(this).setTitle("Error").setMessage("No se pudo Insertar").show()
            }
        }
    }

    fun mostrarDatosEnListView(){
        var listaAlumnos = Alumno(this).mostrarTodos()
        var nombreAlumnos = ArrayList<String>()

        listaIds.clear()


        (0..listaAlumnos.size-1).forEach{
            val al=listaAlumnos.get(it)
            nombreAlumnos.add(al.nombre)
            listaIds.add(al.noControl)
        }
        binding.lv.adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,nombreAlumnos)
        binding.lv.setOnItemClickListener { adapterView, view, indice, l ->
            val noControlLista=listaIds.get(indice) // numero control del clic que dì
            val alumno=Alumno(this).mostrarAlumno(noControlLista)

            AlertDialog.Builder(this).setTitle("Atencion").setMessage("Que deseas hacer con ${alumno.nombre},  de la carrera de:${alumno.carrera}?")
                .setNegativeButton("Eliminar"){d,i,-> alumno.eliminar()
                mostrarDatosEnListView()
                }
                .setPositiveButton("Actualizar"){d,i,->
                  var otraVentana= Intent(this,MainActivity2::class.java)
                    otraVentana.putExtra("noControl",alumno.noControl)
                    startActivity(otraVentana)
                }
                .setNeutralButton("Cerrar"){d,i,->}
                .show()

        }
    }

    override fun onRestart() {
        super.onRestart()
        mostrarDatosEnListView()
    }
}