package calc4fun.cliente.DataAccessLayer.Manejador;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Clase que se encarga de la interacción con SQLLite del móvil.
 * Se persiste el nombre de usuario registrado y el token de facebook
 */
public class ClientHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CLIENTE_PIS";

    public static String getTableName() {
        return TABLE_NAME;
    }

    protected static final String TABLE_NAME = "ESTADO_JUGADOR";

    public static final String CREATE_TABLE = "create table if not exists "
            + TABLE_NAME
            + " ( fb_token TEXT primary key, NOMBRE  TEXT NOT NULL, NICKNAME TEXT NOT NULL);";

    public ClientHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }

}
