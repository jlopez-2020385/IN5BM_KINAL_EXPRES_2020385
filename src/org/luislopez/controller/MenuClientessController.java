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
import org.luislopez.bean.Clientes;
import org.luislopez.db.Conexion;
import org.luislopez.system.Principal;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuClientessController implements Initializable {

    private Principal escenarioPrincipal;
    private ObservableList<Clientes> listaClientes;

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
    private TextField txtTelefonoC;
    @FXML
    private TextField txtDireccionC;
    @FXML
    private TextField txtCorreoC;
    @FXML
    private TableView tblClientes;
    @FXML
    private TableColumn colCodigoC;
    @FXML
    private TableColumn colNitC;
    @FXML
    private TableColumn colNombreC;
    @FXML
    private TableColumn colApellidoC;
    @FXML
    private TableColumn colDireccionC;
    @FXML
    private TableColumn colCorreoC;
    @FXML
    private TableColumn colTelefonoC;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;
    @FXML
    private ImageView imagAgregar;
    @FXML
    private ImageView imagEliminar;
    @FXML
    private ImageView imagEditar;
    @FXML
    private ImageView imagReporte;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        // TODO
    }

    public void cargarDatos() {
        tblClientes.setItems(getClientes());
        colCodigoC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("clienteID"));
        colNombreC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("nombreClientes"));
        colApellidoC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("apellidosClientes"));
        colDireccionC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("direccionClientes"));
        colNitC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("NIT"));
        colTelefonoC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("telefonoClientes"));
        colCorreoC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("correoClientes"));

    }

    public void selecionarElementos() {
        txtCodigoC.setText(String.valueOf(((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getClienteID()));
        txtNombreC.setText((((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNombreClientes()));
        txtApellidosC.setText((((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getApellidosClientes()));
        txtDireccionC.setText((((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getDireccionClientes()));
        txtNit.setText((((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNIT()));
        txtTelefonoC.setText((((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getTelefonoClientes()));
        txtCorreoC.setText((((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getCorreoClientes()));
    }

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_MostrarClientes}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("clienteID"),
                        resultado.getString("nombreClientes"),
                        resultado.getString("apellidosClientes"),
                        resultado.getString("direccionClientes"),
                        resultado.getString("NIT"),
                        resultado.getString("telefonoClientes"),
                        resultado.getString("correoClientes")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaClientes = FXCollections.observableList(lista);
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
        Clientes registro = new Clientes();
        registro.setNombreClientes(txtNombreC.getText());
        registro.setApellidosClientes(txtApellidosC.getText());
        registro.setDireccionClientes(txtDireccionC.getText());
        registro.setNIT(txtNit.getText());        
        registro.setTelefonoClientes(txtTelefonoC.getText());        
        registro.setCorreoClientes(txtCorreoC.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarClientes( ?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getNombreClientes());
            procedimiento.setString(2, registro.getApellidosClientes());
            procedimiento.setString(3, registro.getDireccionClientes());
            procedimiento.setString(4, registro.getNIT());
            procedimiento.setString(5, registro.getTelefonoClientes());
            procedimiento.setString(6, registro.getCorreoClientes());
            procedimiento.execute();
            listaClientes.add(registro);

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
                imagAgregar.setImage(new Image("/org/luislopez/imagenes/Agregar.png"));
                imagEliminar.setImage(new Image("/org/luislopez/imagenes/basura.png"));
                tipoDeoperaciones = operaciones.NINGUNO;                
                
                break;
            default:
                if(tblClientes.getSelectionModel().getSelectedItem()!=null){
                    int respuesta = JOptionPane.showConfirmDialog(null,"Confirmar la eliminacion del registro","Eliminar Clientes",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_NO_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarClientes(?)}");
                            procedimiento.setInt(1, ((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getClienteID());
                            procedimiento.execute();
                            listaClientes.remove(tblClientes.getSelectionModel().getSelectedItem());
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
                if(tblClientes.getSelectionModel().getSelectedItem()!=null){
                    
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarClientes(?,?,?,?,?,?,?)}");
            Clientes registro=(Clientes)tblClientes.getSelectionModel().getSelectedItem();
            
            registro.setNombreClientes(txtNombreC.getText());
            registro.setApellidosClientes(txtApellidosC.getText());
            registro.setDireccionClientes(txtDireccionC.getText());
            registro.setNIT(txtNit.getText());
            registro.setTelefonoClientes(txtTelefonoC.getText());
            registro.setCorreoClientes(txtCorreoC.getText());  
            procedimiento.setInt(1, registro.getClienteID());
            procedimiento.setString(2, registro.getNombreClientes());
            procedimiento.setString(3, registro.getApellidosClientes());
            procedimiento.setString(4, registro.getDireccionClientes());
            procedimiento.setString(5, registro.getNIT());
            procedimiento.setString(6, registro.getTelefonoClientes());
            procedimiento.setString(7, registro.getCorreoClientes());
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
        txtNombreC.setEditable(false);
        txtApellidosC.setEditable(false);
        txtNit.setEditable(false);
        txtTelefonoC.setEditable(false);
        txtDireccionC.setEditable(false);
        txtCorreoC.setEditable(false);
    }

    public void activarControles() {
        txtCodigoC.setEditable(true);
        txtNombreC.setEditable(true);
        txtApellidosC.setEditable(true);
        txtNit.setEditable(true);
        txtTelefonoC.setEditable(true);
        txtDireccionC.setEditable(true);
        txtCorreoC.setEditable(true);

    }

    public void limpiarControlers() {
        txtCodigoC.clear();
        txtNombreC.clear();
        txtApellidosC.clear();
        txtNit.clear();
        txtTelefonoC.clear();
        txtDireccionC.clear();
        txtCorreoC.clear();
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
