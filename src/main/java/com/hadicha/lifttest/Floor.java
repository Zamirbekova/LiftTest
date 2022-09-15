package com.hadicha.lifttest;

import java.util.*;

public class Floor {

    private final List<Passenger> residents = new ArrayList<>();
    private final Deque<Passenger> upwardBound = new ArrayDeque<>();
    private final Deque<Passenger> downwardBound = new ArrayDeque<>();

    Floor(Building building) {
    }

    private void waitForElevator(Passenger passenger, int destinationFloor) {

        passenger.waitForElevator(destinationFloor);

        if (destinationFloor > passenger.getCurrentFloor()) upwardBound.add(passenger);
        else downwardBound.add(passenger);

        residents.remove(passenger);
    }

    void enterGroundFloor(Passenger passenger) {
        residents.add(passenger);
    }

    void decrementQueue(String direction) {

        if (direction.equals("up")) upwardBound.removeFirst();
        else downwardBound.removeFirst();
    }

    Passenger getFirstPassenger(String direction) {

        return direction.equals("up")
                ? upwardBound.getFirst()
                : downwardBound.getFirst();
    }

    Boolean queueIsEmpty(String direction) {

        return direction.equals("up")
                ? upwardBound.isEmpty()
                : downwardBound.isEmpty();
    }

    void randomizeDestinations() {
        for (Map.Entry<Passenger, Integer> entry :
                getResidentsReadyToBoard().entrySet()) {

            waitForElevator(entry.getKey(), entry.getValue());
        }
    }

    private Map<Passenger, Integer> getResidentsReadyToBoard() {

        Map<Passenger, Integer> toQueues = new HashMap<>();
        for (Passenger user : residents) {

            int rand = new Random().nextInt(Building.FLOORS);

            if (rand != 0 && rand != user.getCurrentFloor()) {
                toQueues.put(user, rand);
            }
        }
        return toQueues;
    }

    List<Passenger> getListOf(String flag) {

        if (flag.equals("residents")) return residents;
        else if (flag.equals("up")) return new ArrayList<>(upwardBound);
        else return new ArrayList<>(downwardBound);
    }

}
