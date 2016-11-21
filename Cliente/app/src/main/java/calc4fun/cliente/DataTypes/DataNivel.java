package calc4fun.cliente.DataTypes;

/**
 * Created by cristina on 3/10/2016.
 */

public class DataNivel {
        private int id_nivel;
        private int num_nivel;
        private boolean nivel_completo;
        private boolean nivel_disponible;

        public DataNivel(){}

        public DataNivel(int id_nivel, int num_nivel, boolean nivel_completo, boolean nivel_disponible) {
            this.id_nivel = id_nivel;
            this.num_nivel = num_nivel;
            this.nivel_completo = nivel_completo;
            this.nivel_disponible = nivel_disponible;
        }

        public int getId_nivel() {
            return id_nivel;
        }

        public int getNum_nivel() {
            return num_nivel;
        }

        public boolean isNivel_completo() {
            return nivel_completo;
        }

        public boolean isNivel_disponible() {
            return nivel_disponible;
        }

    public void setId_nivel(int id_nivel) {
        this.id_nivel = id_nivel;
    }

    public void setNum_nivel(int num_nivel) {
        this.num_nivel = num_nivel;
    }

    public void setNivel_completo(boolean nivel_completo) {
        this.nivel_completo = nivel_completo;
    }

    public void setNivel_disponible(boolean nivel_disponible) {
        this.nivel_disponible = nivel_disponible;
    }
}


