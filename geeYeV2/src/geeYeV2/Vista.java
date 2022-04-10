/*
 * Proyecto GEEyE_V2 - Archivo Vista.java - Compañía DAW
 * Licencia Creative Commons BY-NC-SA 4.0
 * https://creativecommons.org/licenses/by-nc-sa/4.0/
 */

package geeYeV2;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.netbeans.lib.awtextra.*;
/**
 *
 * @author Cristina Carrascosa <cctausias@gmail.com>, Ian Molina , Rai Vilar
 * @version 1.0
 * @date 6 abr. 2022 20:16:37
 * 
 * Esta clase se va a ser nuestro formulario y hereda de la clase JFrame
 */
public class Vista extends JFrame{
    
    JLabel lblIdEvento ,lblNombre_espacio,lblComensales,lblFecha,lblPagado,lblPrecioCubierto,lblId;
    JTextField txtNombre_espacio,txtFecha;
    JSpinner spnComensales, spnPrecioCubierto; // ¡MIRAR SI ESTA VARIABLE NOS SIRVE
    JComboBox cboPagado,cboPrecioCubierto;
    JScrollPane scroll;
    DefaultTableModel model;
    JTable tblDatos;
    JButton btnAgregar,btnEliminar,btnGuardar,btnLimpiar,btnPDF;
    
    /**
     * Creamos constructor
     */
    public Vista(){
        this.setTitle("GEEyE_v2 CRUD");
        this.setSize(550, 600); // le damos el tamaño deseado
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); // con esto cuando cerremos la ventana se cerrará la aplicación
        this.setLocationRelativeTo(null); // para que aparezca centrado en la pantalla
        this.setLayout(new AbsoluteLayout()); // importamos la libreria Absolute Layout
        
        // Le agregamos los elementos
        lblIdEvento = new JLabel("Id Evento");
        this.getContentPane().add(lblIdEvento, new AbsoluteConstraints(10,10,100,20));//damos las coordenadas iniciales (x,y)
        
        lblNombre_espacio = new JLabel("Espacio");
        this.getContentPane().add(lblNombre_espacio, new AbsoluteConstraints(10,40,100,20));
        
        lblComensales = new JLabel("Comensales");
        this.getContentPane().add(lblComensales, new AbsoluteConstraints(10,70,100,20));
        
        lblFecha = new JLabel("Fecha Evento");
        this.getContentPane().add(lblFecha, new AbsoluteConstraints(10,100,100,20));
        
        lblPagado = new JLabel("Pagado");
        this.getContentPane().add(lblPagado, new AbsoluteConstraints(10,130,100,20));
        
        lblPrecioCubierto = new JLabel("Precio Cubierto");
        this.getContentPane().add(lblPrecioCubierto, new AbsoluteConstraints(10,160,100,20));
        
        lblId = new JLabel("Id");
        this.getContentPane().add(lblId, new AbsoluteConstraints(120,10,100,20));
        
        
        // Introducimos campos de texto
        txtNombre_espacio=new JTextField();
        this.getContentPane().add(txtNombre_espacio,new AbsoluteConstraints(120,40,100,20));
        
        spnComensales=new JSpinner();
        this.getContentPane().add(spnComensales,new AbsoluteConstraints(120,70,100,20));
        
        txtFecha=new JTextField();
        this.getContentPane().add(txtFecha,new AbsoluteConstraints(120,100,100,20));
        
        Object items[]=new Object[2]; // de este array recogerá los datos el spinner
        items[0]="true";
        items[1]="false";
        cboPagado=new JComboBox(items);
        this.getContentPane().add(cboPagado,new AbsoluteConstraints(120, 130, 100, 20));
        
//        Object precios[]=new Object[6]; // de este array recogerá los datos el spinner
//        items[0]=50;
//        items[1]=100;
//        items[2]=150;
//        items[3]=200;
//        items[4]=250;
//        items[5]=300;
//        cboPrecioCubierto=new JComboBox(precios);
//        this.getContentPane().add(cboPrecioCubierto,new AbsoluteConstraints(120, 160, 100, 20));
        
        spnPrecioCubierto=new JSpinner(); // con spinner hacemos un desplegable al que le pasaremos los datos a través de un array
        this.getContentPane().add(spnPrecioCubierto,new AbsoluteConstraints(120,160,100,20));
        
        /**
         * Agregamos los botones
         */
        btnAgregar=new JButton("Agregar");
        this.getContentPane().add(btnAgregar, new AbsoluteConstraints(300, 10, 100, 20));
        btnEliminar=new JButton("Eliminar");
        this.getContentPane().add(btnEliminar, new AbsoluteConstraints(300, 40, 100, 20));
        btnGuardar=new JButton("Guardar");
        this.getContentPane().add(btnGuardar, new AbsoluteConstraints(300, 70, 100, 20));
        btnLimpiar=new JButton("Limpiar");
        this.getContentPane().add(btnLimpiar, new AbsoluteConstraints(300, 100, 100, 20));
        btnPDF=new JButton("Ver PDF");
        this.getContentPane().add(btnPDF, new AbsoluteConstraints(300, 130, 100, 20));
        
        /**
         * 
         */
        tblDatos=new JTable();
        scroll=new JScrollPane();
        model=new DefaultTableModel();
        model.addColumn("Id Evento");
        model.addColumn("Espacio");
        model.addColumn("Comensales");
        model.addColumn("Fecha");
        model.addColumn("Pagado");
        model.addColumn("Precio Cubierto");
        tblDatos.setModel(model);
        scroll.setViewportView(tblDatos);
        this.getContentPane().add(scroll, new AbsoluteConstraints(10, 200, 500, 300));
        
        this.setVisible(true);
    }

//    public static void main(String[] args) {
//        
//        Vista v = new Vista();
//        v.setVisible(true);
//    }
}
