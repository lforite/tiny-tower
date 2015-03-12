package actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import entity.StoreFloorStatus;
import message.*;
import scala.concurrent.duration.Duration;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * User: louis.forite
 * Date: 15/02/15
 * Time: 18:58
 */
public class StoreFloorActor extends FloorActor {

    private StoreFloorStatus storeStatus;

    /**
     * Map of good - quantity
     */
    private Map<Integer, Integer> mapGoodToQuantity;

    /**
     * The actor to whom reports money updates
     */
    private ActorRef moneyCollector;

    /**
     * Build a closed shop
     */
    public StoreFloorActor(String name, long constructionLength, ActorRef moneyCollector) {
        super(name, constructionLength);
        storeStatus = StoreFloorStatus.CLOSED;
        mapGoodToQuantity = new TreeMap<>();
        mapGoodToQuantity.put(0, 0);
        mapGoodToQuantity.put(1, 0);
        mapGoodToQuantity.put(2, 0);
        this.moneyCollector = moneyCollector;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        //status handling
        if (message instanceof EndConstructionMessage) {
            endConstruction();
        } else if (message instanceof EndRestockMessage) {
            EndRestockMessage endRestockMessage = (EndRestockMessage) message;
            mapGoodToQuantity.put(endRestockMessage.getGoodNumber(), 500);
            storeStatus = StoreFloorStatus.OPEN;
            System.out.println("Restocked good " + endRestockMessage.getGoodNumber() + " at floor " + name);
        }

        //handling store actions
        else if (!isUnderConstruction()) {
            if (message instanceof RestockFloorMessage) {
                restock((RestockFloorMessage) message);
            } else if (message instanceof IncomingBitizenMessage) {
                handleIncomingBitizen((IncomingBitizenMessage) message);
            } else if (message instanceof BuyGoodMessage && !StoreFloorStatus.CLOSED.equals(storeStatus)) {
                buyGoodMessage((BuyGoodMessage) message);
            } else if (message instanceof HireBitizenMessage && !StoreFloorStatus.RESTOCKING.equals(storeStatus)) {
                hireBitizen((HireBitizenMessage) message);
            } else if (message instanceof FireBitizenMessage && !StoreFloorStatus.RESTOCKING.equals(storeStatus)) {
                fireBitizen((FireBitizenMessage) message);
            }
        }
    }

    /**
     * Restock a good, it will take 1 second to restock a good
     */
    private void restock(RestockFloorMessage message) {
        if (!StoreFloorStatus.RESTOCKING.equals(storeStatus)) {
            getContext().system().scheduler().scheduleOnce(
                    Duration.create(1, TimeUnit.SECONDS),
                    getSelf(),
                    new EndRestockMessage(message.getGoodNumber()),
                    getContext().system().dispatcher(),
                    getSelf()
            );
            storeStatus = StoreFloorStatus.RESTOCKING;
        }
    }

    /**
     * Create a child of the floor which is going to consume goods in the future
     *
     * @param incomingBitizenMessage the incoming bitizen
     */
    private void handleIncomingBitizen(IncomingBitizenMessage incomingBitizenMessage) {
        getContext().actorOf(Props.create(ConsumerActor.class, incomingBitizenMessage.getBitizen(), getSelf()));
    }

    /**
     * Buy a good by checking if the good is not out of stock and then reporting it to the money collector
     *
     * @param message the message which contains the good to buy
     */
    private void buyGoodMessage(BuyGoodMessage message) {
        if (mapGoodToQuantity.containsKey(message.getGoodNumber()) && mapGoodToQuantity.get(message.getGoodNumber()) > 0) {
            mapGoodToQuantity.put(message.getGoodNumber(), mapGoodToQuantity.get(message.getGoodNumber()) - 1);
            moneyCollector.tell(new EarnMoneyMessage(message.getGoodNumber() + 1), getSelf());
            if (stockEmpty()) {
                System.out.println("Shop " + name + " is closed");
                storeStatus = StoreFloorStatus.CLOSED;
            }
        }
    }

    //TODO

    /**
     * @param message
     */
    private void hireBitizen(HireBitizenMessage message) {
        if (!StoreFloorStatus.RESTOCKING.equals(storeStatus)) {

        }
        System.out.println("HIRING");
    }

    private void fireBitizen(FireBitizenMessage message) {
        if (!StoreFloorStatus.RESTOCKING.equals(storeStatus)) {

        }
        System.out.println("FIRING");
    }

    /**
     * Check if all the stock has been consumed
     *
     * @return true if all there is no more stock
     */
    private boolean stockEmpty() {
        for (Map.Entry<Integer, Integer> goodAndQuantity : mapGoodToQuantity.entrySet()) {
            if (goodAndQuantity.getValue() > 0) {
                return false;
            }
        }
        return true;
    }
}
