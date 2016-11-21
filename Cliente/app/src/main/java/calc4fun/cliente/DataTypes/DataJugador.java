package calc4fun.cliente.DataTypes;

import java.util.List;

public class DataJugador {

    private String nick;
    private String imagen;
    private List<DataMundoNivel> mundos_niveles;
    private Integer experiencia;
    private List<DataLogro> logros;

    public DataJugador() {
    }

    public DataJugador(String nick, String imagen, List<DataMundoNivel> mundos_niveles, Integer experiencia, List<DataLogro> logros) {
        this.nick = nick;
        this.imagen = imagen;
        this.mundos_niveles = mundos_niveles;
        this.experiencia = experiencia;
        this.logros = logros;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<DataMundoNivel> getMundosNiveles() {
        return mundos_niveles;
    }

    public void setMundosNiveles(List<DataMundoNivel> mundos_niveles) {
        this.mundos_niveles = mundos_niveles;
    }

    public Integer getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public List<DataLogro> getLogros() {
        return logros;
    }

    public void setLogros(List<DataLogro> logros) {
        this.logros = logros;
    }


}
