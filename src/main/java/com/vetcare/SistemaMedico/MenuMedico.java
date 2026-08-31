package com.vetcare.SistemaMedico;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuMedico {

    private ArrayList<AtencionMedica> atenciones;
    private Scanner sc;
    private int idContador;

    public MenuMedico() {
        atenciones = new ArrayList<>();
        sc = new Scanner(System.in);
        idContador = 1;
    }

    public void mostrarMenu() {
        int op = 0;

        while (op != 3) {
            System.out.println("\n Menu de consultas y diagnosticos ");
            System.out.println("1. Registrar consulta medica");
            System.out.println("2. Ver historial de atenciones");
            System.out.println("3. Salir");
            System.out.print("Ingrese una opcion: ");

            try {
                op = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                op = 0;
            }

            if (op == 1) {
                nuevaConsulta();
            } else if (op == 2) {
                verConsultas();
            } else if (op == 3) {
                System.out.println("Salida del modulo medico...");
            } else {
                System.out.println("Opcion invalida, intente de nuevo.");
            }
        }
    }

    private void nuevaConsulta() {
        System.out.println("\n Registro de Consulta");

        System.out.print("Nombre de la mascota: ");
        String mascota = sc.nextLine();

        System.out.print("Motivo y Descripcion: ");
        String desc = sc.nextLine();

        System.out.print("Diagnostico: ");
        String diag = sc.nextLine();

        double valor = 0;
        boolean error = true;

        while (error) {
            System.out.print("Costo del servicio: ");
            try {
                valor = Double.parseDouble(sc.nextLine());
                error = false;
            } catch (Exception e) {
                System.out.println("Ingrese un valor.");
            }
        }

        AtencionMedica nuevaAtencion = new AtencionMedica(idContador, desc, valor, mascota, diag);
        atenciones.add(nuevaAtencion);
        idContador++;

        System.out.println("Consulta registrada.");
    }

    private void verConsultas() {
        System.out.println("\n Historial de atenciones");

        if (atenciones.isEmpty()) {
            System.out.println("No hay consultas registradas.");
        } else {
            for (int i = 0; i < atenciones.size(); i++) {
                System.out.println(atenciones.get(i));
            }
        }
    }

    public static void main(String[] args) {
        MenuMedico m = new MenuMedico();
        m.mostrarMenu();
    }
}

