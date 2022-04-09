package geeYeV2;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;


/*
 * Proyecto GEEyE_V2 - Archivo Eventos.java - Compañía DAW
 * Licencia Creative Commons BY-NC-SA 4.0
 * https://creativecommons.org/licenses/by-nc-sa/4.0/
 */

/**
 *
 * @author Cristina Carrascosa <cctausias@gmail.com>, Ian Molina , Rai Vilar
 * @version 1.0
 * @date 6 abr. 2022 18:44:29
 * 
 * Clase Modelo o Clase Entidad de nuestra BD
 * Creamos las mismas variables que campos tiene nuestra tabla con el mismo tipo de datos
 */
public class Eventos {

    private int id;
    private String nombre_espacio;
    private int comensales;
    private String fecha;
            
    private boolean pagado;
    private double precioCubierto;
    
    /**
     * Hacemos una sobrecarga de constructures
     */
    public Eventos(){
        System.out.println("El evento ha sido creado");
    }
    
    public Eventos(int id, String nombre_espacio, int comensales, String fecha, boolean pagado, double precioCubierto) {
        this.id = id;
        this.nombre_espacio = nombre_espacio;
        this.comensales = comensales;
        this.fecha = fecha;
        this.pagado = pagado;
        this.precioCubierto = precioCubierto;
    }
    
    /**
     * En este constructor omitimos la variable "id"
     * @param nombre_espacio
     * @param comensales
     * @param fecha
     * @param pagado
     * @param precioCubierto 
     */
    public Eventos(String nombre_espacio, int comensales, String fecha, boolean pagado, double precioCubierto) {
        this.nombre_espacio = nombre_espacio;
        this.comensales = comensales;
        this.fecha = fecha;
        this.pagado = pagado;
        this.precioCubierto = precioCubierto;
    }
    
    /**
     * 
     * Getters 
     */
    public int getId() {
        return id;
    }

    public String getNombre_espacio() {
        return nombre_espacio;
    }

    public int getComensales() {
        return comensales;
    }

    public String getFecha() {
        return fecha;
    }

    public boolean getPagado() {
        return pagado;
    }

    public double getPrecioCubierto() {
        return precioCubierto;
    }

    /**
     * Setters
     * 
     */
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre_espacio(String nombre_espacio) {
        this.nombre_espacio = nombre_espacio;
    }

    public void setComensales(int comensales) {
        this.comensales = comensales;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public void setPrecioCubierto(double precioCubierto) {
        this.precioCubierto = precioCubierto;
    }

    /**
     * Método para imprimir
     * @return nos devuelve un String
     */
    @Override
    public String toString() {
        
        return "Eventos{" + "id=" + id + ", nombre_espacio=" + nombre_espacio + ", comensales=" + comensales + ", fecha=" + fecha + ", pagado=" + pagado + ", precioCubierto=" + precioCubierto + '}';
    }
    
    
    
   public static void main(String[] args) {
        Conexion c = new Conexion("catering");
        
        c.conectar();
        
        
        Eventos v = new Eventos();
        v.setComensales(120);
        v.setFecha("2022-05-21");
        
        Eventos v2 = new Eventos("San Rafael", 50, "2022-06-30", true, 50);
        
        Eventos v3 = new Eventos(9, "San Pedro",150, "2024", true, 100);
        
       System.out.println(v.toString()); 
       System.out.println(v2.toString());
                
                
        v3.toString();
        c.desconectar();
    }
}

