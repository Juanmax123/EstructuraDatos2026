

package util;


import modelo.Estudiante;
import excepciones.EstudianteNoEncontrado;
import java.util.HashMap;
import java.util.Map;

public class GestorEstudiantes {
    private HashMap<String, Estudiante> mapaEstudiantes;

    public GestorEstudiantes() {
        this.mapaEstudiantes = new HashMap<>();
    }

    public void registrarEstudiante(Estudiante estudiante) {
        mapaEstudiantes.put(estudiante.getId(), estudiante);
    }

    public Estudiante buscarEstudiante(String id) throws EstudianteNoEncontrado {
        Estudiante est = mapaEstudiantes.get(id);
        if (est == null) {
            throw new EstudianteNoEncontrado("No existe estudiante con ID: " + id);
        }
        return est;
    }

    public void eliminarEstudiante(String id) throws EstudianteNoEncontrado {
        if (!mapaEstudiantes.containsKey(id)) {
            throw new EstudianteNoEncontrado("No se puede eliminar. Estudiante no encontrado: " + id);
        }
        mapaEstudiantes.remove(id);
    }

    public void listarEstudiantes() {
        if (mapaEstudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        for (Map.Entry<String, Estudiante> entry : mapaEstudiantes.entrySet()) {
            entry.getValue().mostrarInformacion();
            System.out.println("--------------------------");
        }
    }
}