import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class ElevatorSystem implements Runnable{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    private Elevator firstElevator;
    private Elevator secondElevator;
    private List<Request> queue = new ArrayList<>();

    public ElevatorSystem(Elevator first, Elevator second) {
        firstElevator = first;
        secondElevator = second;
    }

    public void addRequest(Request request) {
        queue.add(request);
        System.out.println(ANSI_RED + request.getId() + ". Новая заявка c " + request.getCurFloor() + " на " + request.getReqFloor() + " этаж" + ANSI_RESET);
    }

    public void move(Elevator elevator) {

        if (elevator.getId() == 1) {
            System.out.println(ANSI_GREEN + "Лифт " + elevator.getId() + " сейчас на " + elevator.getCurFloor() + " этаже" + ANSI_RESET);
        } else if (elevator.getId() == 2) {
            System.out.println(ANSI_YELLOW + "Лифт " + elevator.getId() + " сейчас на " + elevator.getCurFloor() + " этаже" + ANSI_RESET);
        }

        completeRequest(elevator);
        passingRequests(elevator);
        acceptRequest(elevator);

        if (elevator.getDirection() == 0) {
            for (Request request : queue) {
                if (!request.isAccepted()) {
                    System.out.println("Лифт " + elevator.getId() + " начал выполнять запрос " + request.getId());
                    elevator.addIn(request.getCurFloor(), request);
                    elevator.setRequiredFloor(request.getReqFloor());
                    if (request.getCurFloor() > elevator.getCurFloor()) {
                        elevator.setDirection(1);
                    } else {
                        elevator.setDirection(-1);
                    }
                    elevator.setRequiredDirection(request.getDirection());

                    request.accept();
                    acceptRequest(elevator);
                    passingRequests(elevator);
                    break;
                }
            }
        }

        if (elevator.getDirection() == 0) return;

        if (elevator.getDirection() == 1 && elevator.getCurFloor() < 10) {
            elevator.setCurFloor(elevator.getCurFloor() + 1);
        }

        if (elevator.getDirection() == -1 && elevator.getCurFloor() > 1) {
            elevator.setCurFloor(elevator.getCurFloor() - 1);
        }
    }

    public void acceptRequest(Elevator elevator) {

        if (elevator.getDirection() == 0) return;

        if (elevator.newRequestsFloor(elevator.getCurFloor()) != null) {

            for (Request req : elevator.newRequestsFloor(elevator.getCurFloor())) {

                System.out.println("Лифт " + elevator.getId() + " принял запрос " + req.getId());

                elevator.setDirection(req.getDirection());
                elevator.addOut(req.getReqFloor(), req);

                if (elevator.getDirection() == 1) {
                    if (elevator.getRequiredFloor() < req.getReqFloor()) {
                        elevator.setRequiredFloor(req.getReqFloor());
                    }
                } else if (elevator.getDirection() == -1) {
                    if (elevator.getRequiredFloor() > req.getReqFloor()) {
                        elevator.setRequiredFloor(req.getReqFloor());
                    }
                }

            }
            elevator.clearFloorRequest(elevator.getCurFloor());
        }

    }

    public void completeRequest(Elevator elevator) {

        if (elevator.getDirection() == 0) return;

        if (elevator.comRequestsFloor(elevator.getCurFloor()) != null) {

            for (Request req : elevator.comRequestsFloor(elevator.getCurFloor())) {

                System.out.println("Лифт " + elevator.getId() + " выполнил запрос " + req.getId());

            }

            elevator.clearComRequest(elevator.getCurFloor());
        }

        if (elevator.getComRequests().isEmpty() && elevator.getNewRequests().isEmpty()) {
            elevator.setDirection(0);
        }

    }

    public void passingRequests(Elevator elevator) {

        if (elevator.getDirection() == 0) return;

        if (elevator.getDirection() == elevator.getRequiredDirection()) {

            for (Request request : queue) {
                if (!request.isAccepted()) {
                    if (elevator.getDirection() == 1) {
                        if (request.getCurFloor() >= elevator.getCurFloor() && request.getReqFloor() <= elevator.getRequiredFloor() && request.getDirection() == elevator.getDirection()) {
                            System.out.println("Лифт " + elevator.getId() + " начал выполнять запрос " + request.getId());
                            elevator.addIn(request.getCurFloor(), request);
                            request.accept();
                        }
                    } else {
                        if (request.getCurFloor() <= elevator.getCurFloor() && request.getReqFloor() >= elevator.getRequiredFloor() && request.getDirection() == elevator.getDirection()) {
                            System.out.println("Лифт " + elevator.getId() + " начал выполнять запрос " + request.getId());
                            elevator.addIn(request.getCurFloor(), request);
                            request.accept();
                        }
                    }
                }
            }

        }

    }


    public void run() {
        while (true) {
            if (firstElevator.getDirection() == 0 && secondElevator.getDirection() == 0 && !queue.isEmpty()) {
                if (Math.abs(firstElevator.getCurFloor() - queue.get(0).getCurFloor()) > Math.abs(secondElevator.getCurFloor() - queue.get(0).getCurFloor())) {
                    move(firstElevator);
                    move(secondElevator);
                } else {
                    move(secondElevator);
                    move(firstElevator);
                }
            } else {
                move(firstElevator);
                move(secondElevator);
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
