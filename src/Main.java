import models.Business;
import models.HashTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HashTable hashTable1 = new HashTable(1000);
        HashTable hashTable2 = new HashTable(1000);
        Scanner scanner = new Scanner(System.in);
        String line = "";
        String splitBy = ",";
        boolean useHashFunction1 = true;

        long totalInsertionTimeHash1 = 0;
        long totalInsertionTimeHash2 = 0;

        int choice;
        do {
            System.out.println("1. Cargar datos desde archivo");
            System.out.println("2. Insertar un nuevo negocio");
            System.out.println("3. Ver todos los negocios");
            System.out.println("4. Buscar un negocio por ID");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    try {
                        BufferedReader br = new BufferedReader(new FileReader("bussines.csv"));
                        while ((line = br.readLine()) != null) {
                            String[] businessData = line.split(splitBy);
                            Business business = new Business(businessData[0], businessData[1], businessData[2], businessData[3], businessData[4]);

                            long startTime = System.nanoTime();
                            hashTable1.push(business.getId(), business, true);
                            long endTime = System.nanoTime();
                            totalInsertionTimeHash1 += (endTime - startTime);

                            startTime = System.nanoTime();
                            hashTable2.push(business.getId(), business, false);
                            endTime = System.nanoTime();
                            totalInsertionTimeHash2 += (endTime - startTime);
                        }
                        System.out.println("Datos cargados exitosamente.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.print("ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Nombre: ");
                    String name = scanner.nextLine();
                    System.out.print("Dirección: ");
                    String address = scanner.nextLine();
                    System.out.print("Ciudad: ");
                    String city = scanner.nextLine();
                    System.out.print("Estado: ");
                    String state = scanner.nextLine();
                    Business business = new Business(id, name, address, city, state);

                    long startTime = System.nanoTime();
                    hashTable1.push(id, business, true);
                    long endTime = System.nanoTime();
                    System.out.println("Tiempo funcion hash 1: " + (endTime - startTime) + " ns");

                    startTime = System.nanoTime();
                    hashTable2.push(id, business, false);
                    endTime = System.nanoTime();
                    System.out.println("Tiempo funcion hash 2: " + (endTime - startTime) + " ns");
                    break;
                case 3:
                    System.out.println("HashTable con función hash 1:");
                    for (Business b : hashTable1.getAll()) {
                        System.out.println(b);
                    }
                    System.out.println("HashTable con función hash 2:");
                    for (Business b : hashTable2.getAll()) {
                        System.out.println(b);
                    }
                    break;
                case 4:
                    System.out.print("ID: ");
                    String searchId = scanner.nextLine();

                    long startTimeGet = System.nanoTime();
                    Business found1 = hashTable1.get(searchId, true);
                    long endTimeGet = System.nanoTime();
                    System.out.println("Tiempo funcion hash 1: " + (endTimeGet - startTimeGet) + " ns");

                    if (found1 != null) {
                        System.out.println("Funcion hash 1: " + found1);
                    } else {
                        System.out.println("Funcion hash 1: Negocio no encontrado");
                    }

                    startTimeGet = System.nanoTime();
                    Business found2 = hashTable2.get(searchId, false);
                    endTimeGet = System.nanoTime();
                    System.out.println("Tiempo funcion hash 2: " + (endTimeGet - startTimeGet) + " ns");

                    if (found2 != null) {
                        System.out.println("Funcion hash 2: " + found2);
                    } else {
                        System.out.println("Funcion hash 2: Negocio no encontrado");
                    }
                    break;
                case 5:
                    System.out.println("Hasta la proxima...");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (choice != 5);

        scanner.close();
    }
}
