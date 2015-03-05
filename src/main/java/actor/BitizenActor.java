package actor;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.UntypedActor;
import entity.Bitizen;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * User: louis.forite
 * Date: 15/02/15
 * Time: 18:54
 */
public class BitizenActor extends UntypedActor {

    private Bitizen bitizen;

    private Cancellable buyAction;

    public BitizenActor(ActorRef store) {
        buyAction = getContext().system().scheduler().schedule(
                Duration.Zero(),
                Duration.create(1, TimeUnit.MILLISECONDS),
                store,
                "Tick",
                getContext().system().dispatcher(), getSelf()
        );
    }

    @Override
    public void onReceive(Object message) throws Exception {


    }
}
