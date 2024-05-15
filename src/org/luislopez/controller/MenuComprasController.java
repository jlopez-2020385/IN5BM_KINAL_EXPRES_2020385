/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.luislopez.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.luislopez.bean.Compras;
import org.luislopez.db.Conexion;
import org.luislopez.system.Principal;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class MenuComprasController implements Initializable {

    private Principal escenarioPrincipal;
    private ObservableList<Compras> listaCompras;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeoperaciones = operaciones.NINGUNO;

    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtNumeroC;
    @FXML
    private TextField txtFechaC;
    @FXML
    private TextField txtDescripcionC;
    @FXML
    private TextField txtTotalC;
    @FXML
    private TableView tblCompras;
    @FXML
    private TableColumn colNumeroC;
    @FXML
    private TableColumn colFechaC;
    @FXML
    private TableColumn colDescripcionC;
    @FXML
    private TableColumn colTotalC;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;
    @FXML

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        // TODO
    }

    public void cargarDatos() {
        tblCompras.setItems(getClientes());
        colNumeroC.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("numeroDocumento"));
        colFechaC.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("fechaDocumento"));
        colDescripcionC.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("descripcion"));
        colTotalC.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("totalDocumento"));

    }

    public void selecionarElementos() {
        txtNumeroC.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
        txtFechaC.setText((((Compras) tblCompras.getSelectionModel().getSelectedItem()).getFechaDocumento()));
        txtDescripcionC.setText((((Compras) tblCompras.getSelectionModel().getSelectedItem()).getDescripcion()));
        txtTotalC.setText((((Compras) tblCompras.getSelectionModel().getSelectedItem()).getTotalDocumento()));
    }

    public ObservableList<Compras> getClientes() {
        ArrayList<Compras> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarCompras}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Compras(resultado.getInt("numeroDocumento"),
                        resultado.getString("fechaDocumento"),
                        resultado.getString("descripcion"),
                        resultado.getString("totalDocumento")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaCompras = FXCollections.observableList(lista);
    }

    public void agregar() {
        switch (tipoDeoperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                activarControles();
                tipoDeoperaciones = operaciones.ACTUALIZAR;
                break;

            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControlers();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                desactivarControles();
                limpiarControlers();                
                tipoDeoperaciones = operaciones.NINGUNO;
                break;
                
        }
    }

    public void guardar() {
        Compras registro = new Compras();
        registro.setFechaDocumento(txtFechaC.getText());
        registro.setDescripcion(txtDescripcionC.getText());
        registro.setTotalDocumento(txtTotalC.getText());        
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCompras( ?, ?, ?)}");
            procedimiento.setString(1, registro.getFechaDocumento());
            procedimiento.setString(2, registro.getDescripcion());
            procedimiento.setString(3, registro.getTotalDocumento());
            procedimiento.execute();
            listaCompras.add(registro);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void eliminar() {
        
        switch(tipoDeoperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControlers();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeoperaciones = operaciones.NINGUNO;                
                
                break;
            default:
                if(tblCompras.getSelectionModel().getSelectedItem()!=null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"Confirmar la eliminacion del registro","Eliminar Clientes",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCompras(?)}");
                            procedimiento.setInt(1, ((Compras)tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento());
                            procedimiento.execute();
                            listaCompras.remove(tblCompras.getSelectionModel().getSelectedItem());
                            limpiarControlers();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else
                    JOptionPane.showMessageDialog(null,"Debe de seleccionar un cliente para eliminar");
        }
        
    }
    
    public void editar(){
        switch(tipoDeoperaciones){
            case NINGUNO:
                if(tblCompras.getSelectionModel().getSelectedItem()!=null){
                    
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                btnAgregar.setDisable(true);
                btnEliminar.setDisable(true);
                activarControles();
                txtNumeroC.setEditable(false);
                tipoDeoperaciones = operaciones.ACTUALIZAR;                
                    
                }else
                    JOptionPane.showMessageDialog(null,"Debe de seleccionar un cliente para editar");
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                desactivarControles();
                limpiarControlers();
                tipoDeoperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
        
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarCompras(?,?,?,?)}");
            Compras registro=(Compras)tblCompras.getSelectionModel().getSelectedItem();
            
            registro.setFechaDocumento(txtFechaC.getText());
            registro.setDescripcion(txtDescripcionC.getText());
            registro.setTotalDocumento(txtTotalC.getText());    
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setString(4, registro.getTotalDocumento());
            procedimiento.execute();            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void reportes(){
        switch(tipoDeoperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControlers();                
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");        
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false); 
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");                 
                tipoDeoperaciones = operaciones.NINGUNO;
                break;  
        }
    }    
           
    
    
    
    
    public void desactivarControles() {
        txtNumeroC.setEditable(true);
        txtFechaC.setEditable(false);
        txtDescripcionC.setEditable(false);
        txtTotalC.setEditable(false);

    }

    public void activarControles() {
        txtNumeroC.setEditable(true);
        txtFechaC.setEditable(true);
        txtDescripcionC.setEditable(true);
        txtTotalC.setEditable(true);

    }

    public void limpiarControlers() {
        txtNumeroC.clear();
        txtFechaC.clear();
        txtDescripcionC.clear();
        txtTotalC.clear();

    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

}

