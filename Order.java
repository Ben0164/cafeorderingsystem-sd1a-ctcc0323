package Acosta_menu;

import java.util.List;

public class Order {
    private String customerName;
    private List<String> items;

    public Order(String customerName, List<String> items) {
        this.customerName = customerName;
        this.items = items;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<String> getItems() {
        return items;
    }
}
