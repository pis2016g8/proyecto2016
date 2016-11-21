package calc4fun.cliente.DataTypes;

import java.util.List;
/**
 * Created by tperaza on 30/9/2016.
 */
public class DataListaDataProblema {
    private List<DataProblema> problemas_nivel;

    public DataListaDataProblema(){
    }

    public DataListaDataProblema(List<DataProblema> problemas_nivel_act) {
        this.problemas_nivel = problemas_nivel_act;
    }

    public List<DataProblema> getProblemas_nivel() {
        return problemas_nivel;
    }

    public void setProblemas_nivel( List<DataProblema> problemas_nivel){
        this.problemas_nivel = problemas_nivel;
    }
}
