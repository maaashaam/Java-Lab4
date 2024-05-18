import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Elevator {

    private int curFloor;
    private int requiredFloor;
    private int id;
    private int direction;
    private int requiredDirection;
    private Map<Integer, List<Request>> newRequests = new HashMap<>();
    private Map<Integer, List<Request>> completeRequests = new HashMap<>();


    public Elevator(int id) {
        this.id = id;
        direction = 0;
        curFloor = 1;
    }

    public int getCurFloor() {
        return curFloor;
    }
    public int getRequiredFloor() { return requiredFloor; }
    public int getId() {
        return this.id;
    }
    public int getDirection() { return direction; }
    public int getRequiredDirection() { return requiredDirection; }
    public Map<Integer, List<Request>> getNewRequests() {
        return newRequests;
    }
    public Map<Integer, List<Request>> getComRequests() {
        return completeRequests;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    public void setCurFloor(int floor) {
        this.curFloor = floor;
    }
    public void setRequiredFloor(int floor) {
        this.requiredFloor = floor;
    }
    public void setRequiredDirection(int direction) { this.requiredDirection = direction; }

    public void addIn(int floor, Request request) {
        if (!newRequests.containsKey(floor)) {
            newRequests.put(floor, new ArrayList<>());
        }
        newRequests.get(floor).add(request);
    }

    public void addOut(int floor, Request request) {
        if (!completeRequests.containsKey(floor)) {
            completeRequests.put(floor, new ArrayList<>());
        }
        completeRequests.get(floor).add(request);
    }

    public List<Request> newRequestsFloor(int floor) {
        return newRequests.get(floor);
    }

    public List<Request> comRequestsFloor(int floor) {
        return completeRequests.get(floor);
    }

    public void clearFloorRequest(int floor) {
        newRequests.remove(floor);
    }

    public void clearComRequest(int floor) {
        completeRequests.remove(floor);
    }

}
