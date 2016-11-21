package calc4fun.cliente.DataTypes;

/**
 * Created by User on 19/10/2016.
 */

public class DataStringWrapper extends DataCardList {
    private final String data;

    public DataStringWrapper(String data){
        this.data = data;
    }

    public DataStringWrapper(int data){
        this.data = String.valueOf(data);
    }

    public String getData() {
        return data;
    }
}
