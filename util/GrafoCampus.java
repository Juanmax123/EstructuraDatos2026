package util;

import java.util.Arrays;

public class GrafoCampus {
    private String[] edificios;
    // Matriz nativa obligatoria para distancias entre edificios
    private int[][] matrizAdyacencia;
    private int numEdificios;
    private static final int INF = Integer.MAX_VALUE;

    public GrafoCampus(String[] nombresEdificios) {
        this.edificios = nombresEdificios;
        this.numEdificios = nombresEdificios.length;
        this.matrizAdyacencia = new int[numEdificios][numEdificios];

        // Inicializar la matriz: 0 en la diagonal, INF en el resto
        for (int i = 0; i < numEdificios; i++) {
            Arrays.fill(matrizAdyacencia[i], INF);
            matrizAdyacencia[i][i] = 0;
        }
    }

    // Agrega una ruta bidireccional (grafo no dirigido) con distancia en metros
    public void agregarConexion(int origen, int destino, int metros) {
        if (origen >= 0 && origen < numEdificios && destino >= 0 && destino < numEdificios) {
            matrizAdyacencia[origen][destino] = metros;
            matrizAdyacencia[destino][origen] = metros;
        }
    }

    public void mostrarEdificios() {
        for (int i = 0; i < numEdificios; i++) {
            System.out.println(i + ": " + edificios[i]);
        }
    }

    // Algoritmo de Dijkstra para calcular y mostrar el camino más corto
    public void calcularRutaMasCorta(int inicio, int fin) {
        int[] distancias = new int[numEdificios];
        boolean[] visitados = new boolean[numEdificios];
        int[] predecesores = new int[numEdificios];

        Arrays.fill(distancias, INF);
        Arrays.fill(predecesores, -1);
        distancias[inicio] = 0;

        for (int i = 0; i < numEdificios - 1; i++) {
            int u = obtenerMinimaDistancia(distancias, visitados);
            if (u == -1) break;
            visitados[u] = true;

            for (int v = 0; v < numEdificios; v++) {
                if (!visitados[v] && matrizAdyacencia[u][v] != INF && 
                    distancias[u] != INF && distancias[u] + matrizAdyacencia[u][v] < distancias[v]) {
                    distancias[v] = distancias[u] + matrizAdyacencia[u][v];
                    predecesores[v] = u;
                }
            }
        }

        imprimirCamino(inicio, fin, distancias, predecesores);
    }

    private int obtenerMinimaDistancia(int[] distancias, boolean[] visitados) {
        int min = INF;
        int minIndice = -1;
        for (int v = 0; v < numEdificios; v++) {
            if (!visitados[v] && distancias[v] <= min) {
                min = distancias[v];
                minIndice = v;
            }
        }
        return minIndice;
    }

    private void imprimirCamino(int inicio, int fin, int[] distancias, int[] predecesores) {
        if (distancias[fin] == INF) {
            System.out.println("No existe camino entre " + edificios[inicio] + " y " + edificios[fin]);
            return;
        }

        // Reconstruir el camino desde el destino hacia el origen
        String camino = edificios[fin];
        int actual = fin;
        while (predecesores[actual] != -1) {
            int anterior = predecesores[actual];
            int tramo = matrizAdyacencia[anterior][actual];
            camino = edificios[anterior] + " -> (" + tramo + "m) " + camino;
            actual = anterior;
        }

        System.out.println("Ruta más corta:");
        System.out.println(camino);
        System.out.println("Distancia TOTAL: " + distancias[fin] + " metros");
    }
}