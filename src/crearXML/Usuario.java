package crearXML;

public class Usuario {

    //Atributos
    private String documento;
    private String nombre;
    private String apellido;
    private String nTarjeta;
    private String tipoTarjeta;
    private int telefono;

    //Constructor
    public Usuario(String documento, String nombre, String apellido, String ntarjeta, String tipoTarjeta, int telefono) {
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nTarjeta = ntarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.telefono = telefono;
    }

    //Setters and Getters

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNtarjeta() {
        return nTarjeta ;
    }

    public void setNtarjeta(String ntarjeta) {
        nTarjeta = ntarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
