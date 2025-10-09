package co.com.juan.mantilla.r2dbc.helper;

public class PruebaExcepcion extends RuntimeException {

    private final String mensaje;
    private final Throwable causa;

    public PruebaExcepcion(String mensaje, Throwable causa) {
        super(mensaje, causa);
        this.mensaje = mensaje;
        this.causa = causa;
    }
}
