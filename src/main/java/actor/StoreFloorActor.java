package actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import entity.Store;
import entity.StoreCategory;
import entity.StoreFloorStatus;
import entity.template.StoreTemplateCollection;
import message.*;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * User: louis.forite
 * Date: 15/02/15
 * Time: 18:58
 */
public class StoreFloorActor extends FloorActor {

    private Store store;

    /**
     * The actor to report money updates to
     */
    private ActorRef moneyCollector;

    /**
     * Build a closed shop
     */
    public StoreFloorActor(StoreCategory category, long constructionLength, ActorRef moneyCollector) {
        super("", constructionLength);
        this.moneyCollector = moneyCollector;
        //decide which floor is going to be built
        store = new Store(StoreTemplateCollection.pickRandomStoreInformation(category));
        //store is closed by default
        store.setStatus(StoreFloorStatus.CLOSED);
        System.out.println("Store created : " + store);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        //status handling
        if (message instanceof EndConstructionMessage) {
            endConstruction();
        } else if (message instanceof EndRestockMessage) {
            EndRestockMessage endRestockMessage = (EndRestockMessage) message;
            store.restock(endRestockMessage.getGoodNumber());
            store.setStatus(StoreFloorStatus.OPEN);
            System.out.println("Restocked good " + endRestockMessage.getGoodNumber() + " at floor " + name);
        }

        //handling store actions
        else if (!isUnderConstruction()) {
            if (message instanceof RestockFloorMessage) {
                restock((RestockFloorMessage) message);
            } else if (message instanceof IncomingBitizenMessage) {
                handleIncomingBitizen((IncomingBitizenMessage) message);
            } else if (message instanceof BuyGoodMessage && !StoreFloorStatus.CLOSED.equals(store.getStatus())) {
                buyGoodMessage((BuyGoodMessage) message);
            } else if (message instanceof HireBitizenMessage && !StoreFloorStatus.RESTOCKING.equals(store.getStatus())) {
                hireBitizen((HireBitizenMessage) message);
            } else if (message instanceof FireBitizenMessage && !StoreFloorStatus.RESTOCKING.equals(store.getStatus())) {
                fireBitizen((FireBitizenMessage) message);
            }
        }
    }

    /**
     * Restock a good, it will take 1 second to restock a good
     */
    private void restock(RestockFloorMessage message) {
        if (!StoreFloorStatus.RESTOCKING.equals(store.getStatus())) {
            getContext().system().scheduler().scheduleOnce(
                    Duration.create(store.getRestockingTime(message.getGoodNumber()), TimeUnit.SECONDS),
                    getSelf(),
                    new EndRestockMessage(message.getGoodNumber()),
                    getContext().system().dispatcher(),
                    getSelf()
            );
            store.setStatus(StoreFloorStatus.RESTOCKING);
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
        if (store.getStockQuantity(message.getGoodNumber()) > 0) {
            store.decreaseStock(message.getGoodNumber());
            moneyCollector.tell(new EarnMoneyMessage(message.getGoodNumber() + 1), getSelf());
            if (store.hasEmptyStock()) {
                System.out.println("Shop " + name + " is closed");
                store.setStatus(StoreFloorStatus.CLOSED);
            }
        }
    }


    //TODO

    /**
     * @param message
     */
    private void hireBitizen(HireBitizenMessage message) {
        if (!StoreFloorStatus.RESTOCKING.equals(store.getStatus())) {

        }
        System.out.println("HIRING");
    }

    private void fireBitizen(FireBitizenMessage message) {
        if (!StoreFloorStatus.RESTOCKING.equals(store.getStatus())) {

        }
        System.out.println("FIRING");
    }
}
