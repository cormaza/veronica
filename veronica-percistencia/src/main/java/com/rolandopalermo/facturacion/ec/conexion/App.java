package com.rolandopalermo.facturacion.ec.conexion;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;


/**
 * Hello world!
 *
 */
public class App 
{
  public String conectar() {
	  MongoClient mongo = crearConexion();
	  
      if (mongo != null) {
    	  return"Lista de bases de datos: ";
        //  printDatabases(mongo);

      } else {
          System.out.println("Error: Conexión no establecida");
      }
	  return "Hola estoy conectado a este modulo";  
  }

  private static MongoClient crearConexion() {
      MongoClient mongo = null;
      mongo = MongoClients.create("mongodb://localhost:27017");
      return mongo;
    
  }
}
