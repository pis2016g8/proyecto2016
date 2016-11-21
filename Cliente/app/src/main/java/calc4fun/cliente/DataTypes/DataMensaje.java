package calc4fun.cliente.DataTypes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by tperaza on 30/9/2016.
 */
public class DataMensaje implements Parcelable {
    private int id;
    private String asunto;
    private String contenido;
    private Date fecha;
    private String remitente;

    @SuppressWarnings("unused")
    public DataMensaje(){
    }

    @SuppressWarnings("unused")
    public DataMensaje(int id, String asunto, String contenido, Date fecha, String remitente) {
        super();
        this.id = id;
        this.asunto = asunto;
        this.contenido = contenido;
        this.fecha = fecha;
        this.remitente = remitente;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAsunto() {
        return asunto;
    }
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }
    public String getContenido() {
        return contenido;
    }

    public String getRemitente(){
        return remitente;
    }
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


    protected DataMensaje(Parcel in) {
        id = in.readInt();
        asunto = in.readString();
        contenido = in.readString();
        long tmpFecha = in.readLong();
        fecha = tmpFecha != -1 ? new Date(tmpFecha) : null;
        remitente = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(asunto);
        dest.writeString(contenido);
        dest.writeLong(fecha != null ? fecha.getTime() : -1L);
        dest.writeString(remitente);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DataMensaje> CREATOR = new Parcelable.Creator<DataMensaje>() {
        @Override
        public DataMensaje createFromParcel(Parcel in) {
            return new DataMensaje(in);
        }

        @Override
        public DataMensaje[] newArray(int size) {
            return new DataMensaje[size];
        }
    };
}