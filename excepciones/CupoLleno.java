package excepciones;

public class CupoLleno extends Exception {
    public CupoLleno(String mensaje) {
        super(mensaje);
    }
}
