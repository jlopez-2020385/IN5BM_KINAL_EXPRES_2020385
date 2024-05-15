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
import javax.swing.JOptionPane;
import org.luislopez.bean.CargoEmpleado;
import org.luislopez.db.Conexion;
import org.luislopez.system.Principal;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class MenuCargoEmpleadoController implements Initializable {
    private Principal escenarioPrincipal;
    private ObservableList<CargoEmpleado> listaCargoEmpleado;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeoperaciones = operaciones.NINGUNO;

    @FXML
    private TextField txtCodigoC;
    @FXML
    private TextField txtNombreEmpleadoC;    
    @FXML
    private TextField txtDescripcionC;        
    @FXML
    private TableView tblCargoEmpleado;    
    @FXML
    private TableColumn colCodigoC;
    @FXML
    private TableColumn colNombreEmpleadoC;     
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
        tblCargoEmpleado.setItems(getCargoEmpleado());
        colCodigoC.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, Integer>("codigoCargoEmpleado"));
        colNombreEmpleadoC.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, Integer>("nombreCargo"));
        colDescripcionC.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, Integer>("descripcionCargo"));
        
    }

    public void selecionarElementos() {
        txtCodigoC.setText(String.valueOf(((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
        txtNombreEmpleadoC.setText((((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getNombreCargo()));
        txtDescripcionC.setText((((CargoEmpleado) tblCargoEmpleado.getSelectionModel().getSelectedItem()).getDescripcionCargo()));
        

    }

    public ObservableList<CargoEmpleado> getCargoEmpleado() {
        ArrayList<CargoEmpleado> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarCargoEmpleado}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new CargoEmpleado(resultado.getInt("codigoCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")
                        
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaCargoEmpleado = FXCollections.observableList(lista);
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
        CargoEmpleado registro = new CargoEmpleado();
        registro.setNombreCargo(txtNombreEmpleadoC.getText());        
        registro.setDescripcionCargo(txtDescripcionC.getText());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCargoEmpleado(?,?)}");
            procedimiento.setString(1, registro.getNombreCargo());
            procedimiento.setString(2, registro.getDescripcionCargo());
            procedimiento.execute();
            listaCargoEmpleado.add(registro);

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
                if(tblCargoEmpleado.getSelectionModel().getSelectedItem()!=null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"Confirmar la eliminacion del registro","Eliminar Clientes",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCargoEmpleado(?)}");
                            procedimiento.setInt(1, ((CargoEmpleado)tblCargoEmpleado.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
                            procedimiento.execute();
                            listaCargoEmpleado.remove(tblCargoEmpleado.getSelectionModel().getSelectedItem());
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
                if(tblCargoEmpleado.getSelectionModel().getSelectedItem()!=null){
                    
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarCargoEmpleado(?,?,?)}");
            CargoEmpleado registro=(CargoEmpleado)tblCargoEmpleado.getSelectionModel().getSelectedItem();
            
            registro.setNombreCargo(txtNombreEmpleadoC.getText());        
            registro.setDescripcionCargo(txtDescripcionC.getText());
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
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
        txtNombreEmpleadoC.setEditable(false);
        txtDescripcionC.setEditable(false);
        

    }

    public void activarControles() {
        txtCodigoC.setEditable(true);
        txtNombreEmpleadoC.setEditable(true);
        txtDescripcionC.setEditable(true);

    }

    public void limpiarControlers() {
        txtCodigoC.clear();
        txtNombreEmpleadoC.clear();
        txtDescripcionC.clear();
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
