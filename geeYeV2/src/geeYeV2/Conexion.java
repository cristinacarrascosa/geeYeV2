package geeYeV2;



import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Proyecto GEEyE_V2 - Archivo Conexion.java - Compañía DAW
 * Licencia Creative Commons BY-NC-SA 4.0
 * https://creativecommons.org/licenses/by-nc-sa/4.0/
 */

/**
 *
 * @author Cristina Carrascosa <cctausias@gmail.com>, Ian Molina , Rai Vilar
 * @version 1.0
 * @date 6 abr. 2022 18:44:15
 * 
 * Esta clase nos permite conectar y desconectar de nuestra BD
 */
public class Conexion {

     // Creamos una variable
    String bd="catering";
    String url="jdbc:mysql://localhost:3306/"; // variable que indica el servidor y el puerto
    String user="cris"; // el usuario por defecto
    String password="sobrina5"; // por defecto sin contraseña
    String driver="com.mysql.cj.jdbc.Driver";
    Connection cx; // Variable global de la clase Connection, importamos libreria

    
    
    /**
     * Agregamos el constructor de la clase al que le indicamos a la bd que queremos que se conecte
     * @param bd : BD de la que nos conectaremos y desconectaremos
     */
    public Conexion(String bd) {
        this.bd = bd;
    }
    
    /**
     * Agregamos constructor por defecto
     */
    public Conexion(){
        
    }
    
    
    /**
     * Creamos método para conectar con un objeto de tipo Connection
     * @return retorna la conexion
     */
    public Connection conectar(){
        try {
            Class.forName(driver);
            cx = DriverManager.getConnection(url+bd, user, password);// se conoce como cadena de conexión
            System.out.println("SE CONECTO A BD "+bd);
        } catch (ClassNotFoundException | SQLException ex) {// ponemos los dos errores en una condición
            System.out.println("NO SE CONECTO A BD "+bd);
            // Comentamos para que no nos salgan todos los errores
           // Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return cx;
    }

    
    /**
     * metodo para desconectar
     */
    public void desconectar(){
        try {
            cx.close();
            System.out.println("BASE DE DATOS "+bd+" DESCONECTADA");
        } catch (SQLException ex) {
            System.err.println("No se pudo cerrar la conexión");
            // Comentamos para que no salgan todos lo errores
            //Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
//    public static void main(String[] args) {
//        
//        // Creamos un objeto de tipo conexion
//        Conexion conexion = new Conexion("catering");
//        
//        // Dentro de conexion llamamos el método conectar
//        conexion.conectar();
//        conexion.desconectar();
//    }
}
