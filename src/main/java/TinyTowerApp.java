import actor.TowerActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import gui.GUI;
import gui.GUIController;

import java.awt.event.ActionListener;

/**
 * User: louis.forite
 * Date: 15/02/15
 * Time: 18:16
 */
public class TinyTowerApp {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("TinyTowerSystem");
        ActorRef tower = system.actorOf(Props.create(TowerActor.class, "tower"), "tower");
        system.actorOf(Props.create(GUIController.class, new GUI(), tower), "controller");
    }
}
