package actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import entity.Bitizen;
import message.EndConstructionMessage;
import message.EvictBitizenMessage;
import message.IncomingBitizenMessage;
import message.MoveInBitizenMessage;

import java.util.Iterator;

/**
 * User: louis.forite
 * Date: 15/02/15
 * Time: 19:04
 */
public class ApartmentFloorActor extends FloorActor {

    public ApartmentFloorActor(String name, long constructionLength) {
        super(name, constructionLength);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof EndConstructionMessage) {
            endConstruction();
        }

        if (!isUnderConstruction()) {
            if (message instanceof MoveInBitizenMessage) {
                moveInBitizen((MoveInBitizenMessage) message);
            } else if (message instanceof EvictBitizenMessage) {
                evictBitizen((EvictBitizenMessage) message);
            } else if (message instanceof IncomingBitizenMessage) {
                IncomingBitizenMessage incomingBitizenMessage = (IncomingBitizenMessage) message;
                //TODO: SAVE IF EMPTY SLOT
            }
        }
    }

    private void moveInBitizen(MoveInBitizenMessage message) {
        getContext().actorOf(Props.create(Bitizen.class));
    }

    private void evictBitizen(EvictBitizenMessage message) {
        Iterator<ActorRef> childrenIterator = getContext().getChildren().iterator();
        while (childrenIterator.hasNext()) {
            ActorRef child = childrenIterator.next();
        }
    }
}
