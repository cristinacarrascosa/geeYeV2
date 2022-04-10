/*
 * Proyecto geeYeV2 - Archivo daoEvento.java - Compañía DAW
 * Licencia Creative Commons BY-NC-SA 4.0
 * https://creativecommons.org/licenses/by-nc-sa/4.0/
 */
package geeYeV2;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristina Carrascosa <cctausias@gmail.com>, Ian Molina , Rai Vilar
 * @version 1.0
 * @date 9 abr. 2022 18:41:06
 *
 * Clase en la que crearemos los métodos CRUD que irán vinculados a la clase
 * Controlador
 */

public class daoEvento {

    Conexion c;

    /**
     * Constructor
     */
    public daoEvento() {
        c = new Conexion(); // PARA PODER CREAR UNA CONEXION SIN PARÁMETROS HEMOS AGREGADO UN CONSTRUCTOR SIN PARÁMETROS
    }

    /**
     * Método para insertar valores en la tabla
     *
     * @param e objeto de tipo Eventos
     * @return true o false dependiendo de si el registro ha sido insertado o no
     */
    public boolean create(Eventos e) {
        try {
            String sql = "INSERT INTO eventos(nombre_espacio,comensales,fecha,pagado,precioCubierto) VALUES (?,?,?,?,?)";
            PreparedStatement ps = c.conectar().prepareStatement(sql); // Utilizamos el método conectar de la clase Conexion

            // Con preparedstatement no hace falta especificar los datos que le vamos a pasar al into
            ps.setString(1, e.getNombre_espacio()); // Especificamos la posición y el tipo de parámetro que vamos a insertar en la BD 
            ps.setInt(2, e.getComensales());        // utilizando los getters del objeto Eventos, serán los datos que van en los ? del String sql
            ps.setString(3, e.getFecha());
            ps.setBoolean(4, e.getPagado());
            ps.setDouble(5, e.getPrecioCubierto());
            ps.execute(); // Ejecutamos y nos devolverá un boolean
            ps.close(); // Cerramos el ps
            ps = null;
            c.desconectar(); // Utilizamos el método desconectar de la clase Conexión
            return true; // Si se llega hasta aquí devuelve un true como que se ha insertado el registro
        } catch (SQLException ex) {
            System.err.println("El registro no ha sido insertado");
            return false; // retorna false si el registro no ha sido insertado
        }
    }

    /**
     * Método para mostrar los registros de la tabla eventos
     *
     * @return devuelve un ArrayList con los objetos Eventos de nuestra BD
     */
    public ArrayList<Eventos> read() {
        ArrayList<Eventos> lista = new ArrayList<Eventos>();
        try {        
            String sql = "SELECT * FROM eventos";
            PreparedStatement ps = c.conectar().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();// ResultSet es el resultado de la ejecución de la consulta
            // Ahora recorremos el resultado y agregamos el resultado al ArrayList
            while(rs.next()){
                Eventos e= new Eventos();
                e.setId(rs.getInt("id")); // los entrecomillados tienen que ser exactamente igual a los de la BD
                e.setNombre_espacio(rs.getString("nombre_espacio"));
                e.setComensales(rs.getInt("comensales"));
                e.setFecha(rs.getString("fecha"));
                e.setPagado(rs.getBoolean("pagado"));
                e.setPrecioCubierto(rs.getDouble("precioCubierto"));
                lista.add(e);
            }
            ps.close();
            ps=null;
            c.desconectar();
        } catch (SQLException ex) {
            System.err.println("ERROR! NO SE HAN PODIDO MOSTRAR LOS REGISTROS");
        }
        
        return lista;
    }
    
    /**
     * Metrodo para borrar un registro
     * @param id int para indicar el id del evento a borrar
     * @return true si lo borra, false si no lo hace
     */
    public boolean delete(int id){
        try {
            String sql="DELETE FROM eventos WHERE id=?";
            PreparedStatement ps=c.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            ps.close();
            ps=null;
            c.desconectar();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    /**
     * Métod para modificar registro
     * @param e Objeto de tipo Eventos
     * @return true si se ha modificado, false si no lo ha hecho
     */
    public boolean update(Eventos e){
        try {
            String sql = "UPDATE eventos SET nombre_espacio=?,comensales=?,fecha=?,pagado=?,precioCubierto=? WHERE id=?";
            PreparedStatement ps=c.conectar().prepareStatement(sql);
            ps.setString(1, e.getNombre_espacio());
            ps.setInt(2, e.getComensales());
            ps.setString(3, e.getFecha());
            ps.setBoolean(4, e.getPagado());
            ps.setDouble(5, e.getPrecioCubierto());
            ps.setInt(6, e.getId());
            ps.execute();
            ps.close();
            ps=null;
            c.desconectar();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    
    /**
     * 
     * @param id
     * @return devuelve un objeto de tipo Eventos 
     */
    public Eventos read(int id) {
        Eventos e = new Eventos();
        try {        
            String sql = "SELECT * FROM eventos WHERE id=?";
            PreparedStatement ps = c.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();// ResultSet es el resultado de la ejecución de la consulta
            // Ahora recorremos el resultado y nos devuelve un registro
            while(rs.next()){
                e.setId(rs.getInt("id")); // los entrecomillados tienen que ser exactamente igual a los de la BD
                e.setNombre_espacio(rs.getString("nombre_espacio"));
                e.setComensales(rs.getInt("comensales"));
                e.setFecha(rs.getString("fecha"));
                e.setPagado(rs.getBoolean("pagado"));
                e.setPrecioCubierto(rs.getDouble("precioCubierto"));
            }
            ps.close();
            ps = null;
            c.desconectar();
        } catch (SQLException ex) {
            System.err.println("ERROR! FALLO EN EL MÉTODO READ EVENTOS");
        }
        
        return e;
    }
}
