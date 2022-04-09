package geeYeV2;

import java.awt.event.*;
import java.util.*;
import java.sql.*;
import javax.swing.JOptionPane;
/*
 * Proyecto GEEyE_V2 - Archivo Controlador.java - Compañía DAW
 * Licencia Creative Commons BY-NC-SA 4.0
 * https://creativecommons.org/licenses/by-nc-sa/4.0/
 */

/**
 *
 * @author Cristina Carrascosa <cctausias@gmail.com>, Ian Molina , Rai Vilar
 * @version 1.0
 * @date 6 abr. 2022 18:47:08
 */
public class Controlador implements ActionListener,MouseListener {
    /**
     * Creamos objeto Vista, Evento y daoEvento
     */
    Vista v;
    Eventos e;
    daoEvento dao;
    
    
    /**
     * Main
     * Crea un objeto de tipo Vista con el constructor del Controlador
     */
    public static void main(String[] args) {
        Controlador c = new Controlador();
    }

    /**
     * Constructor
     * Creamos un objeto Vista para que se visualice la vista
     * Agregamos los botones y eventos de ratón
     */
    public Controlador(){
        v = new Vista();
        dao = new daoEvento();  // Al crear aquí un objeto de tipo daoEvento, se crea una conexión, ya que está así configurado el constructor de daoEvento
        v.btnAgregar.addActionListener(this);
        v.btnEliminar.addActionListener(this);
        v.btnGuardar.addActionListener(this);
        v.btnLimpiar.addActionListener(this);
        v.btnPDF.addActionListener(this);
        v.tblDatos.addMouseListener(this); // cuando hagamos clic sobre un elemento de la tabla
        refrescarTabla(); 
    }
     /**
     * Implementamos los métodos abstractos
     */
    
    /**
     * Método para eventos de botones (método comodín)
     * @param a objeto de tipo ActionEvent que representa la acción del usuario ceon la interfaz
     */
    @Override
    public void actionPerformed(ActionEvent a) {
        /**
         * Con condicionales comprobamos si se ha pulsado los diferentes botones
         * Con getSource obtenmos el objeto 
         */
        if(a.getSource()== v.btnAgregar){ // Agregar registro
            e = new Eventos(); // Al objeto Eventos le asignamos los valores
            e.setNombre_espacio(v.txtNombre_espacio.getText()); // Con esto introduzco los datos de la vista en el setter de Eventos
            e.setComensales(Integer.parseInt(v.spnComensales.getValue().toString())); // Parseamos el int a un String
            e.setFecha(v.txtFecha.getText());
            e.setPagado(Boolean.valueOf(v.cboPagado.getSelectedItem().toString()));  // Parseamos el boolean
            e.setPrecioCubierto(Integer.parseInt(v.SpnPrecioCubierto.getValue().toString()));
            
            // El método create me devuelve un boolean
            if(!dao.create(e)){
                JOptionPane.showMessageDialog(this.v, "El evento NO ha sido registrado");
            }
            refrescarTabla();
            limpiarCampos(); // llámamos a este método para que limpie los campos de nuestra vista
           
            
        }
        if(a.getSource()== v.btnEliminar){ // Eliminar registro
            
        }
        if(a.getSource()== v.btnGuardar){ // Editar registro
            
        }
        if(a.getSource()== v.btnLimpiar){ // Limpiar campos
            
        }
        if(a.getSource()== v.btnPDF){ // Generar PDF
            
        }
    }
    /**
     * Este método es para cuando hagamos click con el ratón
     * @param e 
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==v.tblDatos){ // Código para cuando hace clic sobre la tabla
            
        }
        
    }
    
    /**
     * Método que actualizará los registros de la tabla
     */
    public void refrescarTabla(){
        while(v.model.getRowCount() > 0){ // getRowCount cuenta los registros de nuestra BD
           v.model.removeRow(0); // elimina todos los registros
        }
        // Agregamos los nuevos registros
        ArrayList<Eventos> lista=dao.read();
        for (Eventos e: lista){
            Object item[]=new Object[6];
            item[0]=e.getId();
            item[1]=e.getNombre_espacio();
            item[2]=e.getComensales();
            item[3]=e.getFecha();
            item[4]=e.getPagado();
            item[5]=e.getPrecioCubierto();
            v.model.addRow(item); //recorre el array lista, obtiene los datos y se lo agrega a nuestro modelo
            
        }
        v.tblDatos.setModel(v.model); // a la tabla le asignamos el modelo
    }
    
    /**
     * Método para limpiar las casillas de nuestra Vista
     */
    public void limpiarCampos(){
        v.txtNombre_espacio.setText("");
        v.spnComensales.setValue(0);
        v.txtFecha.setText("");
        v.cboPagado.setSelectedIndex(0);
        v.SpnPrecioCubierto.setValue(0);
    }
    /**
     * Método para cuando tenemos presionado el botón del ratón
     * @param e 
     */
    @Override
    public void mousePressed(MouseEvent e) {
        
    }
    /**
     * Método para cuando soltamos el botón del ratón
     * @param e 
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    /**
     * Método para cuando el ratón está dentro de una zona
     * @param e 
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        
    }
    /**
     * Metodo para cuando el cursor del ratón está fuera de esa zona
     * @param e 
     */
    @Override
    public void mouseExited(MouseEvent e) {
        
    }

   
}
