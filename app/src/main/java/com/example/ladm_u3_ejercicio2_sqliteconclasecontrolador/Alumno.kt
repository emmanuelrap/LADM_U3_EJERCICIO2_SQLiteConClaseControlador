package com.example.ladm_u3_ejercicio2_sqliteconclasecontrolador

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AlertDialog

class Alumno(act: Context) {
    var noControl=""
    var nombre=""
    var carrera=""
    private var act=act
    private var err=""

    fun insertar():Boolean{
        var baseDatos = BaseDatos(act,"escuela",null,1)
        err=""

        try{
            val tabla = baseDatos.writableDatabase
            var datos = ContentValues()

            datos.put("NOCONTROL",noControl)
            datos.put("NOMBRE",nombre)
            datos.put("CARRERA",carrera)

            var resultado = tabla.insert("ALUMNO",null,datos)
            if(resultado==-1L){
                return false //No se pudo insertar
            }
        }catch(err:SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true //si se insertó correctamente
    }

    fun mostrarTodos():ArrayList<Alumno>{ //Regresa una lista de Alumno
        val baseDatos = BaseDatos(act,"escuela",null,1)
        var arreglo = ArrayList<Alumno>()
        err=""

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
            this.err=err.message!!
         }finally {
            baseDatos.close()
         }

        return arreglo
    }

    fun mostrarAlumno(noControlBuscar:String) :Alumno{ //Regresa los datos de 1 Alumno
        var baseDatos = BaseDatos(act,"escuela",null,1)
        val alumno = Alumno(act)
        err=""

        try{
            var tabla = baseDatos.readableDatabase
            var SQL_SELECT ="SELECT * FROM ALUMNO WHERE NOCONTROL=?"
            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(noControlBuscar))

            if (cursor.moveToFirst()){

                    alumno.noControl=cursor.getString(0)
                    alumno.nombre=cursor.getString(1)
                    alumno.carrera=cursor.getString(2)

            }

        }catch(err:SQLiteException){
            this.err=err.message!!
        }finally {
            baseDatos.close()
        }
        return alumno
    }

    fun actualizar(): Boolean{
        var baseDatos = BaseDatos(act,"escuela",null,1)
        err=""
        try{
            var tabla=baseDatos.writableDatabase
            val datosActualizados=ContentValues()

            datosActualizados.put("NOMBRE",nombre)
            datosActualizados.put("CARRERA",carrera)

            val resultado = tabla.update("ALUMNO",datosActualizados,
                                    "NOCONTROL=?", arrayOf(noControl))
            if(resultado==0){
                return false
            }

        }catch (err:SQLiteException){
            this.err=err.message!!
            return false
        }finally{
            baseDatos.close()
        }
        return true
    }

    fun eliminar(noControlEliminar: String):Boolean{
        var baseDatos = BaseDatos(act,"escuela",null,1)
        err=""
        try{
            var tabla=baseDatos.writableDatabase
            val respuesta=tabla.delete("ALUMNO","NOCONTROL=?", arrayOf(noControlEliminar)) //regresa 0 o 1

            if(respuesta==0){
                return false
            }
        }catch (err:SQLiteException){
            this.err=err.message!!
            return false
        }finally{
            baseDatos.close()
        }
        return true
    }

//2da forma de eliminar
    fun eliminar():Boolean{
        var baseDatos = BaseDatos(act,"escuela",null,1)
        err=""
        try{
            var tabla=baseDatos.writableDatabase
            val respuesta=tabla.delete("ALUMNO","NOCONTROL=?", arrayOf(noControl)) //regresa 0 o 1

            if(respuesta==0){
                return false
            }
        }catch (err:SQLiteException){
            this.err=err.message!!
            return false
        }finally{
            baseDatos.close()
        }
        return true
    }
}


/*
var baseDatos = BaseDatos(act,"escuela",null,1)
 err=""
       try{
       }catch (err:SQLiteException){
            this.err=err.message!!
       }finally{
        baseDatos.close()
       }


* */