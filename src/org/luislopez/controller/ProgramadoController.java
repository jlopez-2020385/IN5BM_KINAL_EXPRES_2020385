/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.luislopez.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.luislopez.system.Principal;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class ProgramadoController implements Initializable {
        private Principal escenarioPrincipal;
        @FXML private Button btnRegresar2;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }     
    
    @FXML 
        public void handleButtonAction(ActionEvent event){
        if (event.getSource() == btnRegresar2)
           escenarioPrincipal.menuPrincipalView();
        }    

    public void setEscenarioPrincipal(Principal aThis) {
        this.escenarioPrincipal = aThis;
    }
    
}
