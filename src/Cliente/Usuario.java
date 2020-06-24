package Cliente;

public class Usuario {

    //Atributos
    private int idCliente;
    private String documento;
    private String nombre;
    private String apellido;
    private String nTarjeta;
    private String tipoTarjeta;
    private int telefono;

    //Constructor
    public Usuario(int idCliente, String documento, String nombre, String apellido, String ntarjeta, String tipoTarjeta, int telefono) {
        this.idCliente = idCliente;
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nTarjeta = ntarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.telefono = telefono;
    }

    public Usuario(){}

    /*
    //POJO que utilizaremos para leer la cedena JSON de un archivo, lo asignaremos a esta clase Usuario
    public String toString(){
        //StringBuilder sirve para almacenar cadenas de caracteres
        //--->*StringBuilder sb = new StringBuilder();* Construye un StringBuilder vacío y con una capacidad por defecto de 16 carácteres.
        StringBuilder sb = new StringBuilder();
        //--->*append(..)Añade al final del StringBuilder a la que se aplica, un String o la representación en forma de String de un dato asociado a una variable primitiva
        sb.append("************************************");
        sb.append("\ndocumento: ").append(documento);
        sb.append("\nprimer-nombre: ").append(nombre);
        sb.append("\napellido: ").append(apellido);
        sb.append("\ncredit-card: ").append(nTarjeta);
        sb.append("\ntipo: ").append(tipoTarjeta);
        sb.append("\ntelefono: ").append(telefono);
        sb.append("\n************************************");
        return sb.toString();
    }*/

    //Setters and Getters


    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

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

    public String getTelefono() {
        return String.valueOf(telefono);
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
