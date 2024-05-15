/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.luislopez.bean;

/**
 *
 * @author HP
 */
public class TipoProductos {
    
   private int codigoTipoProducto;
   private String descripcionProducto;
   
   
   public TipoProductos(){
       
   }

    public TipoProductos(int codigoTipoProducto, String descripcionProducto) {
        this.codigoTipoProducto = codigoTipoProducto;
        this.descripcionProducto = descripcionProducto;
    }

    public int getCodigoTipoProducto() {
        return codigoTipoProducto;
    }

    public void setCodigoTipoProducto(int codigoTipoProducto) {
        this.codigoTipoProducto = codigoTipoProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }


   
   
   
   
    
    
}
