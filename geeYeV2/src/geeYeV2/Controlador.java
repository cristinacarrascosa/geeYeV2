package geeYeV2;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
//import com.itextpdf.text.log.Level;
//import com.itextpdf.text.log.Logger;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
//import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.sql.*;
import javax.swing.*;

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
public class Controlador implements ActionListener, MouseListener {

    /**
     * Creamos objeto Vista, Evento y daoEvento 
     * Variable id para indicar el id de registro de la BD
     * ArrayList de objetos Eventos
     */
    Vista v;
    Eventos e, e1;
    daoEvento dao;
    int id = 0;
    ArrayList<Eventos> lista = null;

    /**
     * Main Crea un objeto de tipo Vista con el constructor del Controlador
     */
    public static void main(String[] args) {
        Controlador c = new Controlador();
    }

    /**
     * Constructor Creamos un objeto Vista para que se visualice la vista
     * Agregamos los botones y eventos de ratón
     */
    public Controlador() {
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
     *
     * @param a objeto de tipo ActionEvent que representa la acción del usuario
     * ceon la interfaz
     */
    @Override
    public void actionPerformed(ActionEvent a) {
        /**
         * Con condicionales comprobamos si se ha pulsado los diferentes botones
         * Con getSource obtenmos el objeto
         */
        if (a.getSource() == v.btnAgregar) { // Agregar registro
            e = new Eventos(); // Al objeto Eventos le asignamos los valores
            e.setNombre_espacio(v.txtNombre_espacio.getText()); // Con esto introduzco los datos de la vista en el setter de Eventos
            e.setComensales(Integer.parseInt(v.spnComensales.getValue().toString())); // Parseamos el int a un String
            e.setFecha(v.txtFecha.getText());
            e.setPagado(Boolean.valueOf(v.cboPagado.getSelectedItem().toString()));  // Parseamos el boolean
            e.setPrecioCubierto(Double.parseDouble(v.spnPrecioCubierto.getValue().toString()));

            // El método create me devuelve un boolean
            if (!dao.create(e)) {
                JOptionPane.showMessageDialog(this.v, "El evento NO ha sido registrado");
            }

            limpiarCampos(); // llámamos a este método para que limpie los campos de nuestra vista

        }

        if (a.getSource() == v.btnEliminar) { // Eliminar registro
            int x = JOptionPane.showConfirmDialog(this.v, "¿Seguro que quieres eliminar este registro"); // Mensaje de confirmación
            // Comprobamos que la respuesta es afirmativa y que id es mayor que 0
            if (x == 0 && id > 0) {
                if (!dao.delete(id)) {
                    JOptionPane.showMessageDialog(this.v, "No se ha eliminado el registro");
                }
            }
        }

        if (a.getSource() == v.btnGuardar) { // Editar registro
            e1.setNombre_espacio(v.txtNombre_espacio.getText()); // Con esto introduzco los datos de la vista en el setter de Eventos
            e1.setComensales(Integer.parseInt(v.spnComensales.getValue().toString())); // Parseamos el int a un String
            e1.setFecha(v.txtFecha.getText());
            e1.setPagado(Boolean.valueOf(v.cboPagado.getSelectedItem().toString()));  // Parseamos el boolean
            e1.setPrecioCubierto(Double.parseDouble(v.spnPrecioCubierto.getValue().toString()));

            if (!dao.update(e1)) {
                JOptionPane.showMessageDialog(this.v, "El registro No ha sido actualizado");
            }
        }

        if (a.getSource() == v.btnLimpiar) { // Limpiar campos
            limpiarCampos();
        }

        if (a.getSource() == v.btnPDF) { // Generar PDF
            try {
                FileOutputStream archivo;   // Creamos un objeto FileOutputStream
                File file = new File("D:\\NetBeansProjects\\geeYeV2\\geeYeV2\\src\\pdf\\reporte.pdf"); // ruta donde está el pdf
                archivo = new FileOutputStream(file);

                Document doc = new Document(); // Creamos objeto Document e importamos la libreria "com.itextpdf.text.Document"
                PdfWriter.getInstance(doc, archivo); // Creamos una instancia para escribir sobre el pdf
                doc.open(); // abrimos el documento

                Image img = Image.getInstance("D:\\NetBeansProjects\\geeYeV2\\geeYeV2\\src\\img\\img CRUD.png");// agregamos la imagen
                img.setAlignment(Element.ALIGN_CENTER); // Centramos la imagen
                img.scaleToFit(100, 100); // Establecemos el ancho y el alto de nuestra imagen
                doc.add(img); // Le pasamos la imagen al archivo

                Paragraph p = new Paragraph(10); // Con el objeto Paragraph le agregamos un título
                Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);// Agregamos el style de la Fuente
                p.add(Chunk.NEWLINE); // Agregamos un salto de línea con el objeto Chuck
                p.add("EVENTOS PROGRAMADOS");
                p.setAlignment(Element.ALIGN_CENTER); // Establecemos la alineación
                p.add(Chunk.NEWLINE);
                p.add(Chunk.NEWLINE);
                doc.add(p);

                // Ahora agregamos los datos, para ello agregaremos una tabla
                PdfPTable tabla = new PdfPTable(6); // Creamos un nuevo objeto al que le pasamos el número de columnas

                tabla.setWidthPercentage(100); // Establecemos el tamaño de la tabla
                PdfPCell c1 = new PdfPCell(new Phrase("ID EVENTO", negrita));// Agregamos los nombres de las columnas y el formato de la fuente
                PdfPCell c2 = new PdfPCell(new Phrase("ESPACIO", negrita));
                PdfPCell c3 = new PdfPCell(new Phrase("COMENSALES", negrita));
                PdfPCell c4 = new PdfPCell(new Phrase("FECHA", negrita));
                PdfPCell c5 = new PdfPCell(new Phrase("PAGADO", negrita));
                PdfPCell c6 = new PdfPCell(new Phrase("PRECIO CUBIERTO", negrita));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);// Alineamos las celdas  
                c2.setHorizontalAlignment(Element.ALIGN_CENTER);
                c3.setHorizontalAlignment(Element.ALIGN_CENTER);
                c4.setHorizontalAlignment(Element.ALIGN_CENTER);
                c5.setHorizontalAlignment(Element.ALIGN_CENTER);
                c6.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBackgroundColor(BaseColor.CYAN);// Ponemos un color de fondo a los encabezados de las celdas      
                c2.setBackgroundColor(BaseColor.CYAN);
                c3.setBackgroundColor(BaseColor.CYAN);
                c4.setBackgroundColor(BaseColor.CYAN);
                c5.setBackgroundColor(BaseColor.CYAN);
                c6.setBackgroundColor(BaseColor.CYAN);
                tabla.addCell(c1);// Agregamos las celdas a la tabla
                tabla.addCell(c2);
                tabla.addCell(c3);
                tabla.addCell(c4);
                tabla.addCell(c5);
                tabla.addCell(c6);
                
               ArrayList<Eventos> lista = dao.read();
                // Agregamos los registros
                for (Eventos e4 : lista) { // Recorre los registros y los agrega a nuestra tabla    
                    tabla.addCell("" + e4.getId());
                    tabla.addCell(e4.getNombre_espacio());
                    tabla.addCell("" + e4.getComensales());
                    tabla.addCell(e4.getFecha());
                    tabla.addCell("" + (e4.getPagado()));
                    tabla.addCell("" + (e4.getPrecioCubierto()));   
                }

                doc.add(tabla);// Agregamos la tabla al documento

                doc.close(); // Cerramos el doc
                archivo.close(); // Cerramos el archivo
                Desktop.getDesktop().open(file);// El objeto tipo Desktop nos sirve para abrir el archivo que se está generando
                
            } catch (FileNotFoundException ex) {
               Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                 Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        refrescarTabla(); // Para que se muestre el estado de la tabla después de cualquier evento

    }

    /**
     * Este método es para cuando hagamos click con el ratón
     *
     * @param e objeto de tipo MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == v.tblDatos) { // Código para cuando hace clic sobre la tabla
            int fila = v.tblDatos.getSelectedRow();
            id = Integer.parseInt(v.tblDatos.getValueAt(fila, 0).toString()); // Parseamos el String a Int e indicamos el num de fila y la columna
            e1 = dao.read(id); // lee el elemento
            v.lblId.setText("" + e1.getId()); // asignamos los datos a cada uno de los elementos de la vista
            v.txtNombre_espacio.setText(e1.getNombre_espacio());
            v.spnComensales.setValue(e1.getComensales());
            v.txtFecha.setText(e1.getFecha());
            v.cboPagado.setSelectedItem(e1.getPagado());
            v.spnPrecioCubierto.setValue(e1.getPrecioCubierto());
        }

    }

    /**
     * Método que actualizará los registros de la tabla
     */
    public void refrescarTabla() {
        while (v.model.getRowCount() > 0) { // getRowCount cuenta los registros de nuestra BD
            v.model.removeRow(0); // elimina todos los registros
        }
        // Agregamos los nuevos registros
        ArrayList<Eventos> lista = dao.read();
        for (Eventos e : lista) {
            Object item[] = new Object[6];
            item[0] = e.getId();
            item[1] = e.getNombre_espacio();
            item[2] = e.getComensales();
            item[3] = e.getFecha();
            item[4] = e.getPagado();
            item[5] = e.getPrecioCubierto();
            v.model.addRow(item); //recorre el array lista, obtiene los datos y se lo agrega a nuestro modelo

        }
        v.tblDatos.setModel(v.model); // a la tabla le asignamos el modelo
    }

    /**
     * Método para limpiar las casillas de nuestra Vista
     */
    public void limpiarCampos() {
        v.txtNombre_espacio.setText("");
        v.spnComensales.setValue(0);
        v.txtFecha.setText("");
        v.cboPagado.setSelectedIndex(0);
        v.spnPrecioCubierto.setValue(0);
        v.lblId.setText("");
    }

    /**
     * Método para cuando tenemos presionado el botón del ratón
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Método para cuando soltamos el botón del ratón
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Método para cuando el ratón está dentro de una zona
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Metodo para cuando el cursor del ratón está fuera de esa zona
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

}
