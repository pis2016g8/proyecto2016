package calc4fun.cliente.BussinesLayer.Controladores;

/**
 * Interfaz que debe implementar la clase que
 * quiera suscribirse al evento de problema pasado
 */

public interface ProblemChangedListener {
    public void OnProblemPassed(int problemId);
}
