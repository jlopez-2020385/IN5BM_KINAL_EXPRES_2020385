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
import org.luislopez.bean.Proveedores;
import org.luislopez.db.Conexion;
import org.luislopez.system.Principal;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class MenuProveedorController implements Initializable {
    private Principal escenarioPrincipal;
    private ObservableList<Proveedores> listaProveedores;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeoperaciones = operaciones.NINGUNO;

    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtCodigoC;
    @FXML
    private TextField txtNombreC;
    @FXML
    private TextField txtApellidosC;
    @FXML
    private TextField txtNit;
    @FXML
    private TextField txtRasonSocialC;
    @FXML
    private TextField txtDireccionC;
    @FXML
    private TextField txtContactoC;
    @FXML
    private TextField txtPaginaWebC;    
    @FXML
    private TableView tblProveedores;
    @FXML
    private TableColumn colCodigoC;
    @FXML
    private TableColumn colNitC;
    @FXML
    private TableColumn colNombreC;
    @FXML
    private TableColumn colApellidoC;
    @FXML
    private TableColumn colRasonSocialC;    
    @FXML
    private TableColumn colDireccionC;
    @FXML
    private TableColumn colContactoC;
    @FXML
    private TableColumn colPaginaWebC;
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
        tblProveedores.setItems(getProveedores());
        colCodigoC.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNitC.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("NITProveedor"));        
        colNombreC.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("nombresProveedor"));
        colApellidoC.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("apellidosProveedor"));        
        colDireccionC.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("direccionProveedor"));
        colRasonSocialC.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("rasonSocial"));
        colContactoC.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("contactoPrincipal"));
        colPaginaWebC.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("paginaWeb"));

    }

    public void selecionarElementos() {
        txtCodigoC.setText(String.valueOf(((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
        txtNit.setText((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getNITProveedor()));
        txtNombreC.setText((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getNombresProveedor()));
        txtApellidosC.setText((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getApellidosProveedor()));
        txtDireccionC.setText((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor()));
        txtRasonSocialC.setText((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getRasonSocial()));
        txtContactoC.setText((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getContactoPrincipal())); 
        txtPaginaWebC.setText((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getPaginaWeb()));
    }

    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarProveedores}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("NITProveedor"),
                        resultado.getString("nombresProveedor"),
                        resultado.getString("apellidosProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("rasonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")
                        
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaProveedores = FXCollections.observableList(lista);
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
        Proveedores registro = new Proveedores();
        registro.setDireccionProveedor(txtNit.getText());                
        registro.setNITProveedor(txtNombreC.getText());
        registro.setNombresProveedor(txtApellidosC.getText());
        registro.setApellidosProveedor(txtDireccionC.getText());
        registro.setRasonSocial(txtRasonSocialC.getText());        
        registro.setContactoPrincipal(txtContactoC.getText());
        registro.setPaginaWeb(txtPaginaWebC.getText());
        
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProveedores( ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getNITProveedor());
            procedimiento.setString(2, registro.getNombresProveedor());
            procedimiento.setString(3, registro.getApellidosProveedor());
            procedimiento.setString(4, registro.getDireccionProveedor());
            procedimiento.setString(5, registro.getRasonSocial());
            procedimiento.setString(6, registro.getContactoPrincipal());
            procedimiento.setString(7, registro.getPaginaWeb());
            
            procedimiento.execute();
            listaProveedores.add(registro);

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
                if(tblProveedores.getSelectionModel().getSelectedItem()!=null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"Confirmar la eliminacion del registro","Eliminar Clientes",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProveedores(?)}");
                            procedimiento.setInt(1, ((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            listaProveedores.remove(tblProveedores.getSelectionModel().getSelectedItem());
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
                if(tblProveedores.getSelectionModel().getSelectedItem()!=null){
                    
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarProveedores(?,?,?,?,?,?,?,?)}");
            Proveedores registro=(Proveedores)tblProveedores.getSelectionModel().getSelectedItem();
            
            registro.setNITProveedor(txtNit.getText());
            registro.setNombresProveedor(txtNombreC.getText());
            registro.setApellidosProveedor(txtApellidosC.getText());
            registro.setDireccionProveedor(txtDireccionC.getText());                            
            registro.setRasonSocial(txtRasonSocialC.getText());        
            registro.setContactoPrincipal(txtContactoC.getText());
            registro.setPaginaWeb(txtPaginaWebC.getText()); 
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITProveedor());
            procedimiento.setString(3, registro.getNombresProveedor());
            procedimiento.setString(4, registro.getApellidosProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRasonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
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
        txtNit.setEditable(false);
        txtNombreC.setEditable(false);
        txtApellidosC.setEditable(false);
        txtDireccionC.setEditable(false);
        txtRasonSocialC.setEditable(false);
        txtContactoC.setEditable(false);
        txtPaginaWebC.setEditable(false);
        
    }

    public void activarControles() {
        txtCodigoC.setEditable(true);
        txtNit.setEditable(true);
        txtNombreC.setEditable(true);
        txtApellidosC.setEditable(true);
        txtDireccionC.setEditable(true);
        txtRasonSocialC.setEditable(true);
        txtContactoC.setEditable(true);
        txtPaginaWebC.setEditable(true);

    }

    public void limpiarControlers() {
        txtCodigoC.clear();
        txtNit.clear();
        txtNombreC.clear();
        txtApellidosC.clear();
        txtDireccionC.clear();
        txtRasonSocialC.clear();
        txtContactoC.clear();
        txtPaginaWebC.clear();
        
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
