package actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import message.AskReportMoneyMessage;
import message.BuildFloorMessage;
import message.IncomingBitizenMessage;
import message.RestockFloorMessage;

/**
 * User: louis.forite
 * Date: 15/02/15
 * Time: 18:26
 */
public class TowerActor extends UntypedActor {

    private int numberOfFloors = 2;

    private ActorRef lobby;
    private ActorRef moneyCollector;

    /**
     * Instantiate a tower with a lobby and a money collector
     */
    public TowerActor() {
        lobby = getContext().actorOf(Props.create(LobbyFloorActor.class, "lobby", getSelf()), "lobby");
        moneyCollector = getContext().actorOf(Props.create(MoneyCollectorActor.class, 600), "moneyCollector");
        //TODO: once money is handled properly, remove nice apartment
        getContext().actorOf(Props.create(ApartmentFloorActor.class, "Nice apartment", 0L), "2");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof BuildFloorMessage) {
            buildFloor((BuildFloorMessage) message);
        } else if (message instanceof RestockFloorMessage) {
            getContext().getChild(String.valueOf(((RestockFloorMessage) message).getFloorNumber())).tell(message, getSelf());
        } else if (message instanceof IncomingBitizenMessage) {
            IncomingBitizenMessage incomingBitizenMessage = (IncomingBitizenMessage) message;
            getContext().getChild(String.valueOf(incomingBitizenMessage.getFloorNumber())).tell(message, getSelf());
        } else if (message instanceof AskReportMoneyMessage) {
            moneyCollector.forward(message, getContext());
        }
    }

    /**
     * Build a new floor in the tower which means new child of the tower actor
     *
     * @param buildFloorMessage the message containing what kind of floor to create
     */
    private void buildFloor(BuildFloorMessage buildFloorMessage) {
        String floorNumber = String.valueOf(numberOfFloors + 1);
        switch (buildFloorMessage.getFloorType()) {
            case APARTMENT:
                getContext().actorOf(Props.create(ApartmentFloorActor.class, 1L), floorNumber);
                break;
            case STORE:
                getContext().actorOf(Props.create(StoreFloorActor.class, buildFloorMessage.getStoreCategory(), 1L, moneyCollector), floorNumber);
                break;
        }

        numberOfFloors++;

        //warn the lobby there is a new floor so bitizens can go up to the new floor
        lobby.tell(buildFloorMessage, getSelf());
    }
}
