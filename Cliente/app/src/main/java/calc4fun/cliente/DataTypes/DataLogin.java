package calc4fun.cliente.DataTypes;

/**
 * Created by tperaza on 1/10/2016.
 */
public class DataLogin {
    private String nick;
    private boolean existe_token;

    public DataLogin(){}

    public DataLogin(String nick, boolean existe_token) {
        this.nick = nick;
        this.existe_token = existe_token;
    }

    public void setExiste_token(boolean existe_token) {
        this.existe_token = existe_token;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public boolean isExiste_token() {
        return existe_token;
    }
}
