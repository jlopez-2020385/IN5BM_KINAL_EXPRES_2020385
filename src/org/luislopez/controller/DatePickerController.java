/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.luislopez.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import org.luislopez.system.Principal;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class DatePickerController implements Initializable {
    private Principal escenarioPrincipal;
    
    @FXML
    private DatePicker  DATE;   
    @FXML
    private Label  myLabel;   
    @FXML
    private Button btnRegresar;    
    
    
    @FXML
    public void getDate(ActionEvent event) {
        LocalDate myDate = DATE.getValue();
        System.out.println(myDate.toString());
        
        
    }     
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
