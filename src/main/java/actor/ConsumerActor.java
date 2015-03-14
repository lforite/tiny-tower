package actor;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import entity.Bitizen;
import message.BuyGoodMessage;
import scala.concurrent.duration.Duration;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * User: louis.forite
 * Date: 01/03/15
 * Time: 23:07
 */
public class ConsumerActor extends UntypedActor {

    private Bitizen bitizen;
    private ActorRef store;

    public ConsumerActor(Bitizen bitizen, final ActorRef store) {
        this.bitizen = bitizen;
        this.store = store;

        getContext().system().scheduler().schedule(
                Duration.Zero(),
                Duration.create(5, TimeUnit.SECONDS),
                new Runnable() {
                    Random random = new Random();
                    @Override
                    public void run() {
                        //random name
                        store.tell(new BuyGoodMessage(random.nextInt(3)), getSelf());
                    }
                }, getContext().system().dispatcher()
        );
    }

    @Override
    public void onReceive(Object message) throws Exception {

    }
}
