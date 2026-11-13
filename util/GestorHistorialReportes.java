package util;

import java.util.Stack;

public class GestorHistorialReportes {
    // Pila (Stack) obligatoria para almacenar el historial de reportes visitados (LIFO)
    private Stack<String> pilaHistorial;
    private Stack<String> pilaAdelante; // Opcional para hacer "Rehacer"

    public GestorHistorialReportes() {
        this.pilaHistorial = new Stack<>();
        this.pilaAdelante = new Stack<>();
    }

    // Registrar que el usuario visitó o generó un nuevo reporte
    public void visitarReporte(String nombreReporte) {
        pilaHistorial.push(nombreReporte);
        pilaAdelante.clear(); // Al hacer una nueva acción, se limpia el "rehacer"
        System.out.println("-> [Historial] Reporte generado y guardado: " + nombreReporte);
    }

    // Volver al reporte anterior (Función Deshacer / Atrás)
    public void retroceder() {
        if (pilaHistorial.size() > 1) {
            String actual = pilaHistorial.pop(); // Saca el actual
            pilaAdelante.push(actual); // Lo manda a la pila de adelante
            String anterior = pilaHistorial.peek(); // Mira cuál era el anterior
            System.out.println("<- [Historial] Retrocediendo. Reporte actual: " + anterior);
        } else {
            System.out.println("<- [Historial] No hay más reportes anteriores en el historial.");
        }
    }

    // Mostrar el reporte actual en el que está el usuario
    public void mostrarReporteActual() {
        if (!pilaHistorial.isEmpty()) {
            System.out.println("📄 [Pantalla Activa] Visualizando reporte: " + pilaHistorial.peek());
        } else {
            System.out.println("📄 [Pantalla Activa] No hay reportes en visualización.");
        }
    }
}