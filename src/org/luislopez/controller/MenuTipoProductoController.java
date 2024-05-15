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
import org.luislopez.bean.TipoProductos;
import org.luislopez.db.Conexion;
import org.luislopez.system.Principal;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class MenuTipoProductoController implements Initializable {
    private Principal escenarioPrincipal;
    private ObservableList<TipoProductos> listaProductos;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeoperaciones = operaciones.NINGUNO;

    @FXML
    private TextField txtCodigoC;
    @FXML
    private TextField txtDescripcion;    
    @FXML
    private TableView tblProducto;    
    @FXML
    private TableColumn colCodigoC;
    @FXML
    private TableColumn colDescripcionC;    
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        // TODO
    }

    public void cargarDatos() {
        tblProducto.setItems(getProductos());
        colCodigoC.setCellValueFactory(new PropertyValueFactory<TipoProductos, Integer>("codigoTipoProducto"));
        colDescripcionC.setCellValueFactory(new PropertyValueFactory<TipoProductos, Integer>("descripcionProducto"));

    }

    public void selecionarElementos() {
        txtCodigoC.setText(String.valueOf(((TipoProductos) tblProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
        txtDescripcion.setText((((TipoProductos) tblProducto.getSelectionModel().getSelectedItem()).getDescripcionProducto()));

    }

    public ObservableList<TipoProductos> getProductos() {
        ArrayList<TipoProductos> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarTipoProducto}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoProductos(resultado.getInt("codigoTipoProducto"),
                        resultado.getString("descripcionProducto")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProductos = FXCollections.observableList(lista);
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
        TipoProductos registro = new TipoProductos();
        registro.setDescripcionProducto(txtDescripcion.getText());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoProducto(?)}");
            procedimiento.setString(1, registro.getDescripcionProducto());
            procedimiento.execute();
            listaProductos.add(registro);

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
                if(tblProducto.getSelectionModel().getSelectedItem()!=null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"Confirmar la eliminacion del registro","Eliminar Clientes",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarTipoProducto(?)}");
                            procedimiento.setInt(1, ((TipoProductos)tblProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto());
                            procedimiento.execute();
                            listaProductos.remove(tblProducto.getSelectionModel().getSelectedItem());
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
                if(tblProducto.getSelectionModel().getSelectedItem()!=null){
                    
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                btnAgregar.setDisable(true);
                btnEliminar.setDisable(true);
                activarControles();
                txtCodigoC.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarTipoProducto(?,?)}");
            TipoProductos registro=(TipoProductos)tblProducto.getSelectionModel().getSelectedItem();
            
            registro.setDescripcionProducto(txtDescripcion.getText());
            procedimiento.setInt(1, registro.getCodigoTipoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
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
        txtCodigoC.setEditable(true);
        txtDescripcion.setEditable(false);

    }

    public void activarControles() {
        txtCodigoC.setEditable(true);
        txtDescripcion.setEditable(true);


    }

    public void limpiarControlers() {
        txtCodigoC.clear();
        txtDescripcion.clear();

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

