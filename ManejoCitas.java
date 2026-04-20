import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    //Definimos el formato de fecha, la fecha actual, la fecha límite para agendar citas, y el horario de atención
    static DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static LocalDate fechaActual = LocalDate.now();
    static LocalDate fechaLimite = fechaActual.plusMonths(6);
    static LocalTime apertura = LocalTime.of(8, 0);
    static LocalTime cierre = LocalTime.of(21, 0);

    //REGISTRAR CITA
    public static void registrarCita(ArrayList<Cita> citas) throws NombreInvalidoException, EdadInvalidaException, FechaInvalidaException, HoraInvalidaException, SimultaneaException, DobleCitaException {
        try {
            Scanner sc = new Scanner(System.in);
            //Nombre del paciente: solo puede contener letras y espacios, y no puede estar vacío
            System.out.println("Ingrese el nombre del paciente:");
            String nombrePaciente = sc.nextLine();
            if(nombrePaciente.length()<1) throw new NombreInvalidoException("El nombre del paciente no puede estar vacío.");
            for(int x=0;x<nombrePaciente.length();x++) {
                if(!(Character.isLetter(nombrePaciente.charAt(x))||nombrePaciente.charAt(x)==' ')) throw new NombreInvalidoException("El nombre del paciente debe contener solo letras.");
            }
            for(Cita cita : citas) {
                if(cita.getPacienteCita().getNombre().equalsIgnoreCase(nombrePaciente)) {
                    throw new DobleCitaException();
                }
            }
            //Edad del paciente: debe ser un número entero entre 0 y 110 (excluyendo 110)
            System.out.println("Ingrese la edad del paciente:");
            int edadPaciente = sc.nextInt();
            sc.nextLine(); // Limpiar el buffer de entrada
            if(edadPaciente<0||edadPaciente>=110) throw new EdadInvalidaException();
            Paciente paciente = new Paciente(nombrePaciente, edadPaciente); //Creamos el objeto paciente con el nombre y edad ingresados

            //Especialidad médica: el usuario debe seleccionar una de las 5 especialidades disponibles
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
            sc.nextLine();

            //Asignamos el doctor correspondiente según la especialidad seleccionada
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

            //Fecha de la cita: debe ser una fecha válida en formato dd/mm/yyyy, y debe estar entre el día siguiente y 6 meses a partir de la fecha actual
            System.out.println("Ingrese la fecha de la cita (dd/mm/yyyy):");
            String fechaCitaStr = sc.nextLine();
            LocalDate fechaCita = LocalDate.parse(fechaCitaStr, formatoFecha);
            if(fechaCita.isBefore(fechaActual.plusDays(1)) || fechaCita.isAfter(fechaLimite)) {
                throw new FechaInvalidaException(fechaActual, fechaLimite, formatoFecha);
            }

            //Hora de la cita: debe ser una hora válida en formato hh:mm, y debe estar dentro del horario de atención (8:00 a 21:00)
            System.out.println("Ingrese la hora de la cita (hh:mm):");
            String horaCitaStr = sc.nextLine();
            LocalTime horaCita = LocalTime.parse(horaCitaStr);
            if(horaCita.isBefore(apertura) || horaCita.isAfter(cierre)) {
                throw new HoraInvalidaException();
            }

            //Verificamos que el doctor seleccionado no tenga otra cita agendada para la misma fecha y hora
            for(int x=0;x<citas.size();x++) {
                if(citas.get(x).getFecha().equals(fechaCita) && citas.get(x).getHora().equals(horaCita) && citas.get(x).getDoctorCita().equals(doctorCita)) {
                    throw new SimultaneaException();
                }
            }

            //Si pasa todas las validaciones, creamos la cita y la agregamos a la lista
            Cita nuevaCita = new Cita(paciente, horaCita, fechaCita, doctorCita);
            citas.add(nuevaCita);
            System.out.println("Cita registrada con éxito para " + paciente.getNombre() + " con " + doctorCita.getNombre() + " el " + fechaCita.format(formatoFecha) + " a las " + horaCita.toString() + " horas.");
        }
        catch(InputMismatchException c) {
            System.out.println("Entrada no válida, por favor ingresa un número entero.");
        }
        catch(DateTimeParseException d) { //Excepción para formato de fecha u hora no válido
            System.out.println("Formato de fecha u hora no válido, por favor ingresa la fecha en formato dd/mm/yyyy y la hora en formato hh:mm.");
        }
    }

    //REAGENDAR CITA
    public static String reagendarCita(ArrayList<Cita> citas) throws FechaInvalidaException, HoraInvalidaException, SimultaneaException {
        Scanner sc = new Scanner(System.in);
        try{
            System.out.println("Ingrese el nombre del paciente cuya cita desea reagendar:");
            String pacienteAReagendar = sc.nextLine();
            for(Cita cita : citas) {
                if(cita.getPacienteCita().getNombre().equalsIgnoreCase(pacienteAReagendar)) {
                    System.out.println("Ingrese la nueva fecha de la cita (dd/mm/yyyy):");
                    String nuevaFechaStr = sc.nextLine();
                    LocalDate nuevaFecha = LocalDate.parse(nuevaFechaStr, formatoFecha);
                    if(nuevaFecha.isBefore(fechaActual.plusDays(1)) || nuevaFecha.isAfter(fechaLimite)) {
                        throw new FechaInvalidaException(fechaActual, fechaLimite, formatoFecha);
                    }
                    System.out.println("Ingrese la nueva hora de la cita (hh:mm):");
                    String nuevaHoraStr = sc.nextLine();
                    LocalTime nuevaHora = LocalTime.parse(nuevaHoraStr);
                    if(nuevaHora.isBefore(apertura) || nuevaHora.isAfter(cierre)) {
                        throw new HoraInvalidaException();
                    }
                    for(int x=0;x<citas.size();x++) {
                        if(citas.get(x).getFecha().equals(nuevaFecha) && citas.get(x).getHora().equals(nuevaHora) && citas.get(x).getDoctorCita().equals(cita.getDoctorCita())) {
                            throw new SimultaneaException();
                        }
                    }
                    cita.setFecha(nuevaFecha);
                    cita.setHora(nuevaHora);
                    return("Cita reagendada con éxito para " + cita.getPacienteCita().getNombre() + " con " + cita.getDoctorCita().getNombre() + " el " + nuevaFecha.format(formatoFecha) + " a las " + nuevaHora.toString() + " horas.");
                }
            }
            return("No se encontró una cita para el paciente " + pacienteAReagendar + ".");
        }
        catch(DateTimeParseException d) {
            System.out.println("Formato de fecha u hora no válido, por favor ingresa la fecha en formato dd/mm/yyyy y la hora en formato hh:mm.");
            return "";
        }   
    }

    //CANCELAR CITA
    public static String cancelarCita(ArrayList<Cita> citas) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el nombre del paciente cuya cita desea cancelar:");
        String pacienteACancelar = sc.nextLine();
        for(int x=0;x<citas.size();x++) {
            if(citas.get(x).getPacienteCita().getNombre().equalsIgnoreCase(pacienteACancelar)) {
                citas.remove(x);
                return("Cita cancelada con éxito para " + pacienteACancelar + ".");
            }
        }
        return("No se encontró una cita para el paciente " + pacienteACancelar + ".");
    }
    
    //MAIN
    public static void main(String[] args) { //Horario de atención: 8 - 21 hrs
        ArrayList<Cita> citas = new ArrayList<Cita>();
        

        System.out.println("Bienvenido al sistema de manejo de citas médicas para la clínica Medisoft.");
        Scanner sc = new Scanner(System.in);
        boolean salida=false;
        //Bucle principal del programa, se repite hasta que el usuario decide salir
        while(!salida) {
            try {
                System.out.println("\n¿Qué acción deseas realizar?\n(1) Agendar una cita\n(2) Reagendar cita\n(3) Cancelar cita\n(4) Salir");
                int opcion;
                do {
                    opcion = sc.nextInt();
                    if(opcion<1||opcion>4) {
                        System.out.println("Opción no válida, por favor intenta de nuevo.");
                    }
                }while(opcion<1 || opcion>4);
                switch(opcion) {
                    case 1:
                        registrarCita(citas);
                        break;
                    case 2:
                        System.out.println(reagendarCita(citas));
                        break;
                    case 3:
                        System.out.println(cancelarCita(citas));
                        break;
                    case 4:
                        System.out.println("Gracias por usar el sistema de manejo de citas médicas para la clínica Medisoft.");
                        salida=true;
                        break;
                    default:
                        break;
                }
            }
            //Catch para todas las excepciones personalizadas y de formato
            catch(NombreInvalidoException n) {
                System.out.println(n.getMessage());
            }
            catch(EdadInvalidaException e) {
                System.out.println(e.getMessage());
            }
            catch(FechaInvalidaException f) {
                System.out.println(f.getMessage());
            }
            catch(HoraInvalidaException h) {
                System.out.println(h.getMessage());
            }
            catch(SimultaneaException s) {
                System.out.println(s.getMessage());
            }
            catch(DobleCitaException d) {
                System.out.println(d.getMessage());
            }
            catch(InputMismatchException a) {
                System.out.println("Entrada no válida, por favor ingresa un número.");
                sc.nextLine(); // Limpiar el buffer de entrada
            }
        }
    }
}
