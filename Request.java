public class Request {

    private int id;
    private int curFloor;
    private int reqFloor;
    private int direction;
    private boolean accepted = false;

    public Request(int curFloor, int reqFloor, int id) {
        this.curFloor = curFloor;
        this.reqFloor = reqFloor;
        this.id = id;

        if (curFloor < reqFloor) {
            direction = 1;
        } else {
            direction = -1;
        }
    }

    public int getCurFloor() {
        return this.curFloor;
    }

    public int getReqFloor() {
        return this.reqFloor;
    }

    public int getDirection() {
        return this.direction;
    }

    public int getId() {
        return this.id;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void accept() {
        this.accepted = true;
    }

}
