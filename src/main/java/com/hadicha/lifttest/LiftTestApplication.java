package com.hadicha.lifttest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LiftTestApplication {

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);

            System.out.println("Использование: \\\"lift\" +\n" +
                    "                    \" <numPeople> <movingTime> <numFloors> <eCapacity> <simNum>\\\"\\n\\n\" +\n" +
                    "                    \"numPeople: количество людей, которые должны войти в здание.\\n\" +\n" +
                    "                    \"movingTime: время ожидания перемещения лифта между этажами в секундах.\\n\" +\n" +
                    "                    \"numFloors: количество этажей в здании.\\n\" +\n" +
                    "                    \"eCapacity: грузоподъемность лифта.\\n\" +\n" +
                    "                    \"simNum: количество этажей, на которые едет лифт.\n");

        System.out.println("Количество людей?");
        int numPeople = sc.nextInt();
        int movingTime =5;
        int numFloors =20;
        System.out.println("кг?");
        int elevatorCapacity = sc.nextInt();
        System.out.println("этаж?");
        int simNum =sc.nextInt();

        Building building = new Building(numFloors, elevatorCapacity);
        Pprinter Pprinter = new Pprinter(building, movingTime);

        enterBuilding(building, numPeople);

        building.randomizeQueues("enforce");
        building.getElevator().getPassengersIn();
        Pprinter.displayEntireFloor("start");

        for (int i = 0; i <= simNum; i++) {
            building.getElevator().move();
            Pprinter.displayEntireFloor("standard");
            building.randomizeQueues("standard");
        }
    }

    private static void enterBuilding(Building building, int number) {

        List<String> names = readTxtFileInProject();

        for (int i = 0; i < number; i++) {
            building.enter(new Passenger(chooseRandom(names)));
        }
    }

    private static List<String> readTxtFileInProject() {

        InputStream resource = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("names.txt");

        assert resource != null;
        return new BufferedReader(new InputStreamReader(resource))
                .lines()
                .collect(Collectors.toList());
    }

    private static String chooseRandom(List<String> names) {
        return names.get(new Random().nextInt(names.size()));
    }

}

