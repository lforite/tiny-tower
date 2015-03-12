package actor;

import akka.actor.UntypedActor;
import message.AskReportMoneyMessage;
import message.EarnMoneyMessage;
import message.ReportMoneyMessage;

/**
 * User: louis.forite
 * Date: 12/03/15
 * Time: 20:49
 */
public class MoneyCollectorActor extends UntypedActor {

    private Integer balance;

    /**
     * Instantiate a MoneyCollectorActor with a default balance amount
     *
     * @param initialAmount the initial balance amount
     */
    public MoneyCollectorActor(Integer initialAmount) {
        this.balance = initialAmount;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof EarnMoneyMessage) {
            earnMoney((EarnMoneyMessage) message);
        } else if (message instanceof AskReportMoneyMessage) {
            reportMoney();
        }
    }

    /**
     * Add the amount of money from the message to the current balance
     *
     * @param message the message which contains the amount to add
     */
    private void earnMoney(EarnMoneyMessage message) {
        this.balance += message.getAmount();
        System.out.println("New balance is now : " + balance);
    }

    /**
     * Reply to the sender and report the current balance
     */
    private void reportMoney() {
        getSender().tell(new ReportMoneyMessage(balance), getSelf());
    }
}
