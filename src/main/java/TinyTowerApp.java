import actor.TowerActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import gui.GUI;
import gui.GUIController;

/**
 * User: louis.forite
 * Date: 15/02/15
 * Time: 18:16
 */
public class TinyTowerApp {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("TinyTowerSystem");
        ActorRef tower = system.actorOf(Props.create(TowerActor.class), "tower");
        system.actorOf(Props.create(GUIController.class, new GUI(), tower), "controller");
    }
}
