package util;

import modelo.Aula;
import java.util.TreeMap;

public class GestorHorarios {
    // Estructura obligatoria: TreeMap para mantener las aulas ordenadas por nombre
    private TreeMap<String, Aula> mapaAulas;

    public GestorHorarios() {
        this.mapaAulas = new TreeMap<>();
    }

    public void registrarAula(Aula aula) {
        mapaAulas.put(aula.getNombre(), aula);
    }

    public Aula buscarAula(String nombre) {
        return mapaAulas.get(nombre);
    }

    public void listarAulasOrdenadas() {
        if (mapaAulas.isEmpty()) {
            System.out.println("No hay aulas registradas.");
            return;
        }
        // El TreeMap las recorre automáticamente en orden alfabético por nombre
        for (Aula aula : mapaAulas.values()) {
            System.out.println("Aula: " + aula.getNombre() + " | Capacidad: " + aula.getCapacidad());
        }
    }
}