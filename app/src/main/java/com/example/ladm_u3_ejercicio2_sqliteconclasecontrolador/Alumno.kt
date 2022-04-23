package com.example.ladm_u3_ejercicio2_sqliteconclasecontrolador

import android.content.ContentValues
import android.database.sqlite.SQLiteException

class Alumno(act:MainActivity) {
    var noControl=""
    var nombre=""
    var carrera=""
    var act=act
    var err=""

    fun insertar():Boolean{
        var baseDatos = BaseDatos(act,"escuela",null,1)
        try{
            val tabla = baseDatos.writableDatabase
            var datos = ContentValues()

            datos.put("NO_CONTROL",noControl)
            datos.put("NOMBRE",nombre)
            datos.put("CARRERA",carrera)

            var resultado = tabla.insert("ALUMNO",null,datos)
            if(resultado==-1L){
                return false
            }
        }catch(err:SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }

    fun mostrarTodos() :ArrayList<Alumno>{
        var baseDatos = BaseDatos(act,"escuela",null,1)
        var arreglo = ArrayList<Alumno>()
        try{
            var tabla = baseDatos.readableDatabase
            var SQL_SELECT ="SELECT * FROM ALUMNO"
            var cursor = tabla.rawQuery(SQL_SELECT,null)

            if (cursor.moveToFirst()){
                do{
                    val alumno = Alumno(act)
                    alumno.noControl=cursor.getString(0)
                    alumno.nombre=cursor.getString(1)
                    alumno.carrera=cursor.getString(2)
                    arreglo.add(alumno)
                }while(cursor.moveToNext())
            }

         }catch(err:SQLiteException){

         }finally {
//aquiiii
         }
    }
}


/*
 var baseDatos = BaseDatos(act,"escuela",null,1)
        try{

            }
        }catch(err:SQLiteException){}
* */