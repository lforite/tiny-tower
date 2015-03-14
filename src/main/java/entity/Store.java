package entity;

import entity.template.GoodTemplate;
import entity.template.StoreTemplate;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * User: louis.forite
 * Date: 14/03/15
 * Time: 10:36
 */
@Data
public class Store {
    private StoreCategory category;
    private StoreFloorStatus status;
    private String defaultName;

    private List<Stock> stocks;

    /**
     * Create a store based on a template
     *
     * @param storeTemplate the template to be based on
     */
    public Store(StoreTemplate storeTemplate) {
        category = storeTemplate.getCategory();
        stocks = new ArrayList<>(3);
        for (GoodTemplate goodTemplate : storeTemplate.getGoods()) {
            Stock stock = new Stock();
            stock.setGood(goodTemplate.getName());
            stock.setMaxQuantity(goodTemplate.getMaxQuantity());
            stock.setRestockingTime(goodTemplate.getRestockingTime());
            stock.setQuantity(0);
            stocks.add(stock);
        }
    }

    /**
     * Get the restocking time for a given good
     *
     * @param goodNumber the good to get restocking time from
     * @return a restocking time, in seconds
     */
    public Long getRestockingTime(Integer goodNumber) {
        return stocks.get(goodNumber).getRestockingTime();
    }

    /**
     * Get the current state of the stock for a given good
     *
     * @param goodNumber the good to get stock information on
     * @return the stock quantity
     */
    public Integer getStockQuantity(Integer goodNumber) {
        return stocks.get(goodNumber).getQuantity();
    }

    /**
     * Decrease by 1 the stock of a given good
     *
     * @param goodNumber the good on which to decrease stock
     */
    public void decreaseStock(Integer goodNumber) {
        Stock stock = stocks.get(goodNumber);
        stock.setQuantity(stock.getQuantity() - 1);
        System.out.println(stock);
    }

    /**
     * Restock a good
     *
     * @param goodNumber the good to restock
     */
    public void restock(Integer goodNumber) {
        Stock stock = stocks.get(goodNumber);
        stock.setQuantity(stock.getMaxQuantity());
    }

    /**
     * Check if there is a good in stock
     *
     * @return return false if there is at least one good in stock, true otherwise
     */
    public boolean hasEmptyStock() {
        for (Stock stock : stocks) {
            if (stock.getQuantity() > 0) {
                return false;
            }
        }
        return true;
    }

    @Data
    private static class Stock {
        private String good;
        private Integer quantity;
        private Integer maxQuantity;
        private Long restockingTime;

        public Integer getRestockingCost() {
            return new Double(maxQuantity * 0.6).intValue();
        }
    }
}
