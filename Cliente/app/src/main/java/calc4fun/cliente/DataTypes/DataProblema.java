package calc4fun.cliente.DataTypes;


import android.os.Parcel;
import android.os.Parcelable;

public class DataProblema implements Parcelable {

    private Integer id_problema;
    private String descripcion;
    private String respuesta;
    private int puntos_exp;
    private String ayuda;
    private String contenido;
    private String id_autor;
    private boolean resuelto;
    private boolean tut_activo;

    public DataProblema(){}

    public DataProblema(Integer id_problema, String descripcion, String respuesta, int puntos_exp, String ayuda,
                        String contenido, String id_autor, boolean resuelto) {
        this.setId_problema(id_problema);
        this.setDescripcion(descripcion);
        this.setRespuesta(respuesta);
        this.setPuntos_exp(puntos_exp);
        this.setAyuda(ayuda);
        this.setContenido(contenido);
        this.setId_autor(id_autor);
        this.setResuelto(resuelto);
    }

    protected DataProblema(Parcel in) {
        id_problema = in.readByte() == 0x00 ? null : in.readInt();
        descripcion = in.readString();
        respuesta = in.readString();
        puntos_exp = in.readInt();
        ayuda = in.readString();
        contenido = in.readString();
        id_autor = in.readString();
        resuelto = in.readByte() != 0x00;
        resuelto = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id_problema == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id_problema);
        }
        dest.writeString(descripcion);
        dest.writeString(respuesta);
        dest.writeInt(puntos_exp);
        dest.writeString(ayuda);
        dest.writeString(contenido);
        dest.writeString(id_autor);
        dest.writeByte((byte) (resuelto ? 0x01 : 0x00));
        dest.writeByte((byte) (tut_activo ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DataProblema> CREATOR = new Parcelable.Creator<DataProblema>() {
        @Override
        public DataProblema createFromParcel(Parcel in) {
            return new DataProblema(in);
        }

        @Override
        public DataProblema[] newArray(int size) {
            return new DataProblema[size];
        }
    };


    public Integer getId_problema() {
        return id_problema;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public int getPuntos_exp() {
        return puntos_exp;
    }

    public String getAyuda() {
        return ayuda;
    }

    public String getContenido() {
        return contenido;
    }

    public String getId_autor() {
        return id_autor;
    }

    public boolean getResuleto() {
        return resuelto;
    }


    public void setId_problema(Integer id_problema) {
        this.id_problema = id_problema;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public void setPuntos_exp(int puntos_exp) {
        this.puntos_exp = puntos_exp;
    }

    public void setAyuda(String ayuda) {
        this.ayuda = ayuda;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public void setId_autor(String id_autor) {
        this.id_autor = id_autor;
    }

    public void setResuelto(boolean resuelto) {
        this.resuelto = resuelto;
    }

    public boolean isTut_activo() {
        return tut_activo;
    }

    public void setTut_activo(boolean tut_activo) {
        this.tut_activo = tut_activo;
    }
}
