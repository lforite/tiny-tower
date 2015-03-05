package actor;

import akka.actor.Props;
import akka.actor.UntypedActor;
import message.BuildFloorMessage;
import message.IncomingBitizenMessage;
import message.RestockFloorMessage;

/**
 * User: louis.forite
 * Date: 15/02/15
 * Time: 18:26
 */
public class TowerActor extends UntypedActor {

    private String towerName;

    private int numberOfFloors = 2;

    public TowerActor(String towerName) {
        this.towerName = towerName;
        getContext().actorOf(Props.create(LobbyFloorActor.class, "lobby", getSelf()), "lobby");
        getContext().actorOf(Props.create(ApartmentFloorActor.class, "Nice apartment", 0L), "2");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof BuildFloorMessage) {
            buildFloor((BuildFloorMessage) message);
            //warn the lobby there is a new floor so bitizens can go up to the new floor
            getContext().getChild("lobby").tell(message, getSelf());
        } else if (message instanceof RestockFloorMessage) {
            getContext().getChild(String.valueOf(((RestockFloorMessage) message).getFloorNumber())).tell(message, getSelf());
        } else if (message instanceof IncomingBitizenMessage) {
            IncomingBitizenMessage incomingBitizenMessage = (IncomingBitizenMessage) message;
            //System.out.println(getContext().getChildren());
            getContext().getChild(String.valueOf(incomingBitizenMessage.getFloorNumber())).tell(message, getSelf());
        }
    }

    /**
     * Build a new floor in the tower which means new child of the tower actor
     *
     * @param buildFloorMessage the message containing what kind of floor to create
     */
    private void buildFloor(BuildFloorMessage buildFloorMessage) {
        String floorNumber = String.valueOf(numberOfFloors + 1);
        System.out.println("Created floor " + floorNumber);
        switch (buildFloorMessage.getFloorType()) {
            case APARTMENT:
                getContext().actorOf(Props.create(ApartmentFloorActor.class, 1L), floorNumber);
                break;
            case STORE:
                getContext().actorOf(Props.create(StoreFloorActor.class, "test", 1L), floorNumber);
                break;
        }

        numberOfFloors++;
    }
}
