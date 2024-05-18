public class GenerateRequest implements Runnable {

    private int k = 0;

    private int minFloor = 1;
    private int maxFloor = 10;
    private int requestCount = 1;
    private ElevatorSystem elevators;

    public GenerateRequest(ElevatorSystem elevators) {
        this.elevators = elevators;
    }

    @Override
    public void run() {

        while (true) {

            if (k == 6) break;

            int generatedCurFloor = minFloor + (int) (Math.random() * (maxFloor - minFloor + 1));
            int generatedReqFloor = minFloor + (int) (Math.random() * (maxFloor - minFloor + 1));

            if (generatedCurFloor == generatedReqFloor) continue;

            elevators.addRequest(new Request(generatedCurFloor, generatedReqFloor, requestCount++));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            k++;

        }
    }

}
