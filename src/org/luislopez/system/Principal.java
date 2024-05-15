
package org.luislopez.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.luislopez.controller.DatePickerController;
import org.luislopez.controller.MenuCargoEmpleadoController;
import org.luislopez.controller.MenuClientessController;
import org.luislopez.controller.MenuComprasController;

import org.luislopez.controller.MenuPrincipalController;
import org.luislopez.controller.MenuProductoController;
import org.luislopez.controller.MenuTipoProductoController;
import org.luislopez.controller.MenuProveedorController;
import org.luislopez.controller.ProgramadoController;

/**
 *Documentacion
 *carnet 2020385
 * IN5BM
 * fecha de Creacion : 10/04/2024
 * fecha de modificaciones:
 */
public class Principal extends Application {
    private Stage escenarioPrincipal;
    private Scene escena;
    
    
    public static void main(String[] args) {
        launch(args);
        
    }    
    
    @Override
    public void start(Stage escenarioPrincipal) throws Exception{
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Express");
        menuPrincipalView();
        escenarioPrincipal.getIcons().add(new Image("/org/luislopez/imagenes/cc.png"));
       // Parent root=FXMLLoader.load(getClass().getResource("/org/luislopez/view/KINALexpres.fxml"));
       // Scene escena=new Scene(root);
       // escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
        
    }
    
    public Initializable cambiarEscena(String fxmlname, int width,int heigth) throws Exception{
        Initializable resultado;
        FXMLLoader loader= new FXMLLoader();
        InputStream file = Principal.class.getResourceAsStream("/org/luislopez/view/" + fxmlname);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Principal.class.getResource("/org/luislopez/view/" + fxmlname));
        
        escena=new Scene ((AnchorPane)loader.load(file),width,heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        
        resultado = (Initializable)loader.getController();
        
        return resultado;
        
    }
    
    public void menuPrincipalView(){
        try{
            MenuPrincipalController menuPrincipalView=(MenuPrincipalController)cambiarEscena("KINALexpres.fxml",1120,620);
            menuPrincipalView.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    
    public void menuClientessView(){
        try{
            MenuClientessController menuClientessView=(MenuClientessController)cambiarEscena("MenuClientes.fxml",1120,620);
            menuClientessView.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void menuTipoProductoView(){
        try{
            MenuTipoProductoController menuTipoProductoController=(MenuTipoProductoController)cambiarEscena("MenuTipoProducto.fxml",1120,620);
            menuTipoProductoController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage()); 
        }
    }  

    public void menuProveedorView(){
        try{
            MenuProveedorController menuProveedorController=(MenuProveedorController)cambiarEscena("MenuProveedor.fxml",1120,620);
            menuProveedorController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage()); 
        }
    }   
    
    public void menuProductoView(){
        try{
            MenuProductoController menuProductoController=(MenuProductoController)cambiarEscena("MenuProducto.fxml",1120,620);
            menuProductoController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage()); 
        }
    }   
        
    
    public void menuComprasView(){
        try{
            MenuComprasController menuComprasController=(MenuComprasController)cambiarEscena("MenuCompras.fxml",1120,620);
            menuComprasController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage()); 
        }
    }       
    
    public void menuCargoEmpleadoView(){
        try{
            MenuCargoEmpleadoController menuCargoEmpleadoController=(MenuCargoEmpleadoController)cambiarEscena("MenuCargoEmpleado.fxml",1120,620);
            menuCargoEmpleadoController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage()); 
        }
    }       
    
    
    
    public void datePickerView(){
        try{
            DatePickerController datePickerController=(DatePickerController)cambiarEscena("DatePicker.fxml",1120,620);
            datePickerController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage()); 
        }
    }          
    
    
    public void programadorView(){
        try{
            ProgramadoController programadoController=(ProgramadoController)cambiarEscena("Programado.fxml",1120,620);
            programadoController.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage()); 
        }
    }    
     
}
