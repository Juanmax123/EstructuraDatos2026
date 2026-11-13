import modelo.Aula;
import modelo.Estudiante;
import modelo.Materia;
import util.GestorEstudiantes;
import util.GestorHistorialReportes;
import util.GestorHorarios;
import util.GestorMaterias;
import util.GrafoCampus;
import excepciones.EstudianteNoEncontrado;
import excepciones.HorarioConflictivoException;

import java.util.Scanner;

public class Ejecutar {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Instanciar todos los gestores del sistema
        GestorEstudiantes gestorEstudiantes = new GestorEstudiantes();
        GestorMaterias gestorMaterias = new GestorMaterias();
        GestorHorarios gestorHorarios = new GestorHorarios();
        GestorHistorialReportes historial = new GestorHistorialReportes();

        // Configuración inicial del Grafo del Campus (Dijkstra)
        String[] nombresEdificios = {"Ingenieria", "Biblioteca", "Cafeteria", "Rectoria", "Laboratorios"};
        GrafoCampus campus = new GrafoCampus(nombresEdificios);
        campus.agregarConexion(0, 1, 100);
        campus.agregarConexion(0, 2, 150);
        campus.agregarConexion(1, 3, 250);
        campus.agregarConexion(2, 3, 180);
        campus.agregarConexion(2, 4, 120);

        int opcion = 0;

        do {
            System.out.println("\n==========================================");
            System.out.println("   SISTEMA DE GESTIÓN UNIVERSITARIA (U.S.)");
            System.out.println("==========================================");
            System.out.println("1. Registrar Estudiante (Fase 1)");
            System.out.println("2. Buscar Estudiante por ID (Fase 1)");
            System.out.println("3. Registrar Materia y Pre-requisitos (Fase 2)");
            System.out.println("4. Inscribir Estudiante a Materia / Cola de Espera (Fase 2)");
            System.out.println("5. Reservar Aula (Matriz Horaria) (Fase 3)");
            System.out.println("6. Calcular Ruta más corta en el Campus (Dijkstra) (Fase 3)");
            System.out.println("7. Visualizar Historial de Reportes (Pila / Stack) (Fase 4)");
            System.out.println("8. Retroceder en el Historial (Deshacer Pila) (Fase 4)");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
            } else {
                System.out.println(" Por favor, ingrese un número válido.");
                scanner.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.println("\n--- REGISTRAR ESTUDIANTE ---");
                    System.out.print("Ingrese Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese ID (Ej: 2024001): ");
                    String id = scanner.nextLine();
                    System.out.print("Ingrese Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Ingrese Semestre Actual: ");
                    int semestre = scanner.nextInt();
                    scanner.nextLine();

                    Estudiante nuevoEst = new Estudiante(nombre, id, email, semestre);
                    gestorEstudiantes.registrarEstudiante(nuevoEst);
                    System.out.println(" ¡Estudiante registrado con éxito!");
                    break;

                case 2:
                    System.out.println("\n--- BUSCAR ESTUDIANTE ---");
                    System.out.print("Ingrese ID del estudiante a buscar: ");
                    String idBusqueda = scanner.nextLine();
                    try {
                        Estudiante encontrado = gestorEstudiantes.buscarEstudiante(idBusqueda);
                        System.out.println("¡Estudiante encontrado!");
                        encontrado.mostrarInformacion();
                    } catch (EstudianteNoEncontrado e) {
                        System.out.println(" Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("\n--- REGISTRAR MATERIA ---");
                    System.out.print("Código de la materia (Ej: CALC101): ");
                    String codMat = scanner.nextLine();
                    System.out.print("Nombre de la materia: ");
                    String nomMat = scanner.nextLine();
                    System.out.print("Cupos máximos: ");
                    int cupos = scanner.nextInt();
                    System.out.print("Créditos: ");
                    int cred = scanner.nextInt();
                    scanner.nextLine();

                    Materia nuevaMateria = new Materia(codMat, nomMat, cupos, cred);
                    gestorMaterias.registrarMateria(nuevaMateria);
                    System.out.println(" ¡Materia registrada con éxito!");
                    break;

                case 4:
                    System.out.println("\n--- INSCRIBIR ESTUDIANTE A MATERIA ---");
                    System.out.print("Ingrese ID del estudiante: ");
                    String idEstInsc = scanner.nextLine();
                    System.out.print("Ingrese Código de la materia: ");
                    String codMatInsc = scanner.nextLine();

                    try {
                        Estudiante estInsc = gestorEstudiantes.buscarEstudiante(idEstInsc);
                        Materia matInsc = gestorMaterias.buscarMateria(codMatInsc);

                        if (matInsc != null) {
                            if (matInsc.hayCupo()) {
                                matInsc.inscribirEstudiante(estInsc);
                                System.out.println(" "+ estInsc.getNombre() + " inscrito directamente (Cupo disponible).");
                            } else {
                                matInsc.agregarAColaEspera(estInsc);
                                System.out.println("⏳Materia llena. " + estInsc.getNombre() + " agregado a la COLA DE ESPERA.");
                            }
                        } else {
                            System.out.println(" Materia no encontrada.");
                        }
                    } catch (EstudianteNoEncontrado e) {
                        System.out.println(" " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("\n--- RESERVAR AULA ---");
                    System.out.print("Nombre del Aula (Ej: 101): ");
                    String nomAula = scanner.nextLine();
                    System.out.print("Capacidad del aula: ");
                    int capAula = scanner.nextInt();
                    Aula aula = new Aula(nomAula, capAula);
                    gestorHorarios.registrarAula(aula);

                    System.out.print("Día de la semana (0=Domingo, 1=Lunes ... 6=Sábado): ");
                    int dia = scanner.nextInt();
                    System.out.print("Hora de inicio (0 a 23): ");
                    int hora = scanner.nextInt();
                    System.out.print("Duración en horas: ");
                    int duracion = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        aula.reservar(dia, hora, duracion);
                        System.out.println(" ¡Aula reservada con éxito!");
                    } catch (HorarioConflictivoException e) {
                        System.out.println(" Conflicto de horario: " + e.getMessage());
                    }
                    break;

                case 6:
                    System.out.println("\n--- RUTAS EN EL CAMPUS (DIJKSTRA) ---");
                    campus.mostrarEdificios();
                    System.out.print("Elija índice de edificio de ORIGEN (0-4): ");
                    int origen = scanner.nextInt();
                    System.out.print("Elija índice de edificio de DESTINO (0-4): ");
                    int destino = scanner.nextInt();
                    scanner.nextLine();

                    campus.calcularRutaMasCorta(origen, destino);
                    break;

                case 7:
                    System.out.println("\n--- HISTORIAL DE REPORTES ---");
                    historial.visitarReporte("Reporte General del Sistema v1.0");
                    historial.mostrarReporteActual();
                    break;

                case 8:
                    System.out.println("\n--- RETROCEDER EN HISTORIAL (DESHACER) ---");
                    historial.retroceder();
                    historial.mostrarReporteActual();
                    break;

                case 9:
                    System.out.println("\n Saliendo del sistema. ¡Mucho éxito con tu proyecto universitario, Juan!");
                    break;

                default:
                    System.out.println(" Opción inválida. Intente de nuevo.");
            }

        } while (opcion != 9);

        scanner.close();
    }
}