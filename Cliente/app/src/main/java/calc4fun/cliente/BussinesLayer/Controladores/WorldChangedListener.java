package calc4fun.cliente.BussinesLayer.Controladores;

/**
 * Interfaz que debe implementar la clase que
 * quiera suscribirse al evento de mundo pasado
 * para un mundo en particular
 */

public interface WorldChangedListener {
    public void worldPassed();
    public int getWorldId();
}
