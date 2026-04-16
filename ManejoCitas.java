import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class ManejoCitas {
    //Creamos médicos: general, cardiólogo, radiólogo, traumatólogo, y especialista
    static Doctor doctor1 = new Doctor("Dr. Juan Pérez", "Médico General");
    static Doctor doctor2 = new Doctor("Dra. Ana Gómez", "Cardióloga");
    static Doctor doctor3 = new Doctor("Dr. Carlos Rodríguez", "Radiólogo");
    static Doctor doctor4 = new Doctor("Dra. Laura Martínez", "Traumatóloga");
    static Doctor doctor5 = new Doctor("Dr. Luis Fernández", "Especialista");

    static DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static LocalDate fechaActual = LocalDate.now();
    static LocalDate fechaLimite = fechaActual.plusMonths(6);
    static LocalTime apertura = LocalTime.of(8, 0);
    static LocalTime cierre = LocalTime.of(21, 0); 

    public static void registrarCita(ArrayList<Cita> citas) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Ingrese el nombre del paciente:");
            String nombrePaciente = sc.nextLine();
            if(nombrePaciente.length()<1) throw new IllegalArgumentException("El nombre del paciente no puede estar vacío.");
            for(int x=0;x<nombrePaciente.length();x++) {
                if(!(Character.isLetter(nombrePaciente.charAt(x)))) throw new IllegalArgumentException("El nombre del paciente debe contener solo letras.");
            }
            for(Cita cita : citas) {
                if(cita.getPacienteCita().getNombre().equalsIgnoreCase(nombrePaciente)) {
                    throw new IllegalArgumentException("El paciente ya tiene una cita registrada.");
                }
            }
            System.out.println("Ingrese la edad del paciente:");
            int edadPaciente = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer de entrada
            if(edadPaciente<0||edadPaciente>=110) throw new IllegalArgumentException("La edad del paciente debe ser un número entero entre 0 y 110.");
            Paciente paciente = new Paciente(nombrePaciente, edadPaciente);

            System.out.println("Seleccione la especialidad médica:");
            System.out.println("(1) Dr. Juan Pérez       ||  Médico General");
            System.out.println("(2) Dra. Ana Gómez       ||  Cardióloga");
            System.out.println("(3) Dr. Carlos Rodríguez ||  Radiólogo");
            System.out.println("(4) Dra. Laura Martínez  ||  Traumatóloga");
            System.out.println("(5) Dr. Luis Fernández   ||  Especialista");
            int especialidad;
            do {
                especialidad = sc.nextInt();
                if(especialidad<1 || especialidad>5) {
                    System.out.println("Opción no válida, por favor intenta de nuevo.");
                }
            } while (especialidad < 1 || especialidad > 5);
            
            Doctor doctorCita;
            switch (especialidad) {
                case 1:
                    doctorCita = doctor1;
                    break;
                case 2:
                    doctorCita = doctor2;
                    break;
                case 3:
                    doctorCita = doctor3;
                    break;
                case 4:
                    doctorCita = doctor4;
                    break;
                case 5:
                    doctorCita = doctor5;
                    break;
                default:
                    doctorCita = null; // Esto no debería ocurrir debido a la validación anterior
                    break;
            }

            System.out.println("Ingrese la fecha de la cita (dd/mm/yyyy):");
            String fechaCitaStr = sc.nextLine();
            LocalDate fechaCita = LocalDate.parse(fechaCitaStr, formatoFecha);

            System.out.println("Ingrese la hora de la cita (hh:mm):");
            String horaCitaStr = sc.nextLine();
            LocalTime horaCita = LocalTime.parse(horaCitaStr);


            Cita nuevaCita = new Cita(paciente, horaCita, fechaCita, doctorCita);
            citas.add(nuevaCita);
        }
        catch(IllegalArgumentException b) {
            System.out.println(b.getMessage());
        }
        catch(InputMismatchException c) {
            System.out.println("Entrada no válida, por favor ingresa un número entero.");
        }
    }
    public static void main(String[] args) { //Horario de atención: 8 - 21 hrs
        ArrayList<Cita> citas = new ArrayList<Cita>();
        

        System.out.println("Bienvenido al sistema de manejo de citas médicas para la clínica Medisoft.");
        Scanner sc = new Scanner(System.in);
        boolean salida=false;
        while(!salida) {
            try {
                System.out.println("¿Deseas agendar una cita?\n(1) Sí\n(2) No");
                int opcion;
                do {
                    opcion = sc.nextInt();
                    if(opcion<1||opcion>2) {
                        System.out.println("Opción no válida, por favor intenta de nuevo.");
                    }
                }while(opcion<1 || opcion>2);
                if(opcion==2){
                    System.out.println("Gracias por usar el sistema de manejo de citas médicas para la clínica Medisoft.");
                    salida=true;
                    break;
                }
                registrarCita(citas);
            }
            catch(InputMismatchException a) {
                System.out.println("Entrada no válida, por favor ingresa un número.");
                sc.nextLine(); // Limpiar el buffer de entrada
            }
        }
    }
}