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
     * Variable id para indicar el id de registro de la BD
     */
    Vista v;
    Eventos e,e1;
    daoEvento dao;
    int id = 0; 
    
    
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
        e1 = new Eventos();
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
            e.setPrecioCubierto(Double.parseDouble(v.SpnPrecioCubierto.getValue().toString()));
            
            // El método create me devuelve un boolean
            if(!dao.create(e)){
                JOptionPane.showMessageDialog(this.v, "El evento NO ha sido registrado");
            }
            
            limpiarCampos(); // llámamos a este método para que limpie los campos de nuestra vista
           
            
        }
        if(a.getSource()== v.btnEliminar){ // Eliminar registro
            int x = JOptionPane.showConfirmDialog(this.v, "¿Seguro que quieres eliminar este registro"); // Mensaje de confirmación
            // Comprobamos que la respuesta es afirmativa y que id es mayor que 0
            if(x == 0 && id > 0){
                if(!dao.delete(id)){
                    JOptionPane.showMessageDialog(this.v, "No se ha eliminado el registro");
                }
            }
        }
        if(a.getSource()== v.btnGuardar){ // Editar registro
            e1.setNombre_espacio(v.txtNombre_espacio.getText()); // Con esto introduzco los datos de la vista en el setter de Eventos
            e1.setComensales(Integer.parseInt(v.spnComensales.getValue().toString())); // Parseamos el int a un String
            e1.setFecha(v.txtFecha.getText());
            e1.setPagado(Boolean.valueOf(v.cboPagado.getSelectedItem().toString()));  // Parseamos el boolean
            e1.setPrecioCubierto(Double.parseDouble(v.SpnPrecioCubierto.getValue().toString()));
            
            if(!dao.update(e1)){
                JOptionPane.showMessageDialog(this.v, "El registro No ha sido actualizado");
            }
        }
        if(a.getSource()== v.btnLimpiar){ // Limpiar campos
            limpiarCampos();
        }
        if(a.getSource()== v.btnPDF){ // Generar PDF
            
        }
        refrescarTabla(); // Para que se muestre el estado de la tabla después de cualquier evento
    }
    /**
     * Este método es para cuando hagamos click con el ratón
     * @param e objeto de tipo MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==v.tblDatos){ // Código para cuando hace clic sobre la tabla
            int fila = v.tblDatos.getSelectedRow();
            id = Integer.parseInt(v.tblDatos.getValueAt(fila, 0).toString()); // Parseamos el String a Int e indicamos el num de fila y la columna
            e1 = dao.read(id);
            v.lblId.setText(""+e1.getId()); // asignamos los datos a cada uno de los elementos de la vista
            v.txtNombre_espacio.setText(e1.getNombre_espacio());
            v.spnComensales.setValue(e1.getComensales());
            v.txtFecha.setText(e1.getFecha());
            v.cboPagado.setSelectedItem(e1.getPagado());
            v.SpnPrecioCubierto.setValue(e1.getPrecioCubierto());
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
        v.lblId.setText("");
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
