package calc4fun.cliente.DataTypes;

import java.util.List;
/**
 * Created by tperaza on 30/9/2016.
 */
public class DataMundo {
    private int id_mundo;
    private String nombre;
    private String imagen;
    private String descripcion;
    private boolean mundo_completado;
    private boolean mundo_disponible;
    private List<Integer> mundos_siguientes;

    public DataMundo(){}

    public DataMundo(int id_mundo, String nombre, String imagen, String descripcion, boolean mundo_completado,
                     boolean mundo_disponible, List<Integer> mundos_siguientes) {
        this.id_mundo = id_mundo;
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.mundo_completado = mundo_completado;
        this.mundo_disponible = mundo_disponible;
        this.mundos_siguientes = mundos_siguientes;
    }

    public int getId_mundo() {
        return id_mundo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isMundo_completado() {
        return mundo_completado;
    }
    public boolean isMundo_disponible() {
        return mundo_disponible;
    }

    public List<Integer> getMundos_siguientes() {
        return mundos_siguientes;
    }

    public void setId_mundo(int id_mundo) {
        this.id_mundo = id_mundo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setMundo_completado(boolean mundo_completado) {
        this.mundo_completado = mundo_completado;
    }

    public void setMundo_disponible(boolean mundo_disponible) {
        this.mundo_disponible = mundo_disponible;
    }

    public void setMundos_siguientes(List<Integer> mundos_siguientes) {
        this.mundos_siguientes = mundos_siguientes;
    }
}