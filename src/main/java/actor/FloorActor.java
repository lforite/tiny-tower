package actor;

import akka.actor.UntypedActor;
import entity.FloorStatus;
import message.EndConstructionMessage;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * User: louis.forite
 * Date: 15/02/15
 * Time: 18:25
 */
public abstract class FloorActor extends UntypedActor {

    protected String name;
    //protected String color;
    protected FloorStatus status;

    public FloorActor(String name, long constructionLength) {
        this.name = name;
        this.status = FloorStatus.CONSTRUCTING;

        getContext().system().scheduler().scheduleOnce(
                Duration.create(constructionLength, TimeUnit.SECONDS),
                getSelf(),
                new EndConstructionMessage(),
                getContext().system().dispatcher(), getSelf()
        );
    }

    protected boolean isUnderConstruction() {
        switch (status) {
            case CONSTRUCTING:
                return true;
            case AVAILABLE:
                //fall through
            default:
                return false;
        }
    }

    protected void endConstruction() {
        status = FloorStatus.AVAILABLE;
    }
}
