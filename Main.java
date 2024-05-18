public class Main {

    public static void main(String[] args) {

        System.out.println("Количество этажей в доме: 10");
        System.out.println("Количество лифтов: 2");
        System.out.println();

        Elevator firstElevator = new Elevator(1);
        Elevator secondElevator = new Elevator(2);

        ElevatorSystem system = new ElevatorSystem(firstElevator, secondElevator);
        GenerateRequest requests = new GenerateRequest(system);

        Thread thread1 = new Thread(requests);
        Thread thread2 = new Thread(system);
        thread1.start();
        thread2.start();

    }

}
