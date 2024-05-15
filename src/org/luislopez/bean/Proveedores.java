
package org.luislopez.bean;

/**
 *
 * @author HP
 */
public class Proveedores {
   
    private int codigoProveedor ;
    private String NITProveedor ;
    private String nombresProveedor ;
    private String apellidosProveedor ;
    private String direccionProveedor ;
    private String rasonSocial ;
    private String contactoPrincipal ;
    private String paginaWeb ;
    
    public Proveedores(){
        
    }

    public Proveedores(int codigoProveedor, String NITProveedor, String nombresProveedor, String apellidosProveedor, String direccionProveedor, String rasonSocial, String contactoPrincipal, String paginaWeb) {
        this.codigoProveedor = codigoProveedor;
        this.NITProveedor = NITProveedor;
        this.nombresProveedor = nombresProveedor;
        this.apellidosProveedor = apellidosProveedor;
        this.direccionProveedor = direccionProveedor;
        this.rasonSocial = rasonSocial;
        this.contactoPrincipal = contactoPrincipal;
        this.paginaWeb = paginaWeb;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getNITProveedor() {
        return NITProveedor;
    }

    public void setNITProveedor(String NITProveedor) {
        this.NITProveedor = NITProveedor;
    }

    public String getNombresProveedor() {
        return nombresProveedor;
    }

    public void setNombresProveedor(String nombresProveedor) {
        this.nombresProveedor = nombresProveedor;
    }

    public String getApellidosProveedor() {
        return apellidosProveedor;
    }

    public void setApellidosProveedor(String apellidosProveedor) {
        this.apellidosProveedor = apellidosProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public String getRasonSocial() {
        return rasonSocial;
    }

    public void setRasonSocial(String rasonSocial) {
        this.rasonSocial = rasonSocial;
    }

    public String getContactoPrincipal() {
        return contactoPrincipal;
    }

    public void setContactoPrincipal(String contactoPrincipal) {
        this.contactoPrincipal = contactoPrincipal;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }
    
    
    
    
    
    
    
    
    
}
