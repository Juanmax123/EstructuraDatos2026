package util;
import modelo.Materia;
import java.util.HashMap;
public class GestorMaterias {

 // Mapa rápido (HashMap) que asocia el código de la materia (Key) con el objeto Materia (Value)
  
private HashMap<String, Materia> mapaMaterias;

// Constructor que inicializa el HashMap vacío
public GestorMaterias() {

    this.mapaMaterias = new HashMap<>();
}

// Método para registrar y guardar una nueva materia en el sistema
public void registrarMateria(Materia materia) {

    mapaMaterias.put(materia.getCodigo(), materia);

}
// Método para buscar una materia rápidamente ingresando su código único
    public Materia buscarMateria(String codigo) {
        return mapaMaterias.get(codigo);
    }

    // Método para recorrer y mostrar en consola todas las materias registradas
    public void listarMaterias() {
        if (mapaMaterias.isEmpty()) {
            System.out.println("No hay materias registradas.");
            return;
        }
        // Recorre los valores (objetos Materia) dentro del HashMap
        for (Materia m : mapaMaterias.values()) {
            m.mostrarDetalles();
            System.out.println("--------------------------");
        }
    }


}