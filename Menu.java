package Acosta_menu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Menu extends JFrame {

    private JPanel contentPane;
    private JTextField textFieldQuantity;
    private DefaultListModel<String> receiptModel;
    private JList<String> receiptList;
    private int customerCount = 0;
    private JLabel customerCountLabel;
    private JTextField textFieldCustomerName;
    private List<JPanel> selectedDrinkPanels = new ArrayList<>();
    private JTabbedPane tabbedPane;

    private static class Drink {
        private final String name;
        private final ImageIcon image;
        private double price;
        private final String category;

        public Drink(String name, ImageIcon image, double price, String category) {
            this.name = name;
            this.image = image;
            this.price = price;
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public ImageIcon getImage() {
            return image;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getCategory() {
            return category;
        }

        @Override
        public String toString() {
            return name + " (P" + price + ")";
        }
    }

    private List<Drink> drinkList = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();

    public Menu() {
    	
        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 600);
        
     // Create the content pane with a yellow border
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
               
                g.drawRect(0, 0, getWidth() - 1, getHeight() - 1); // Draw a border around the panel
            }
        };
        
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());


        JPanel mainPanel = new JPanel(new BorderLayout());
        contentPane.add(mainPanel, BorderLayout.CENTER);

        JPanel tabbedPanePanel = new JPanel(new BorderLayout());
        mainPanel.add(tabbedPanePanel, BorderLayout.CENTER);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPanePanel.add(tabbedPane, BorderLayout.CENTER);

        JPanel hotDrinksPanel = createDrinksPanel("Hot Coffee");
        tabbedPane.addTab("Hot Coffee", hotDrinksPanel);

        JPanel coldDrinksPanel = createDrinksPanel("Cold Drinks");
        tabbedPane.addTab("Cold Drinks", coldDrinksPanel);

        JPanel milkDrinksPanel = createDrinksPanel("Milk Drinks");
        tabbedPane.addTab("Milk Drinks", milkDrinksPanel);

        JPanel rightPanel = new JPanel(new BorderLayout());
        mainPanel.add(rightPanel, BorderLayout.EAST);

        receiptModel = new DefaultListModel<>();
        receiptList = new JList<>(receiptModel);
        receiptList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPaneReceipt = new JScrollPane(receiptList);
        rightPanel.add(scrollPaneReceipt, BorderLayout.CENTER);

        JPanel receiptButtonsPanel = new JPanel(new GridLayout(0, 1));
        rightPanel.add(receiptButtonsPanel, BorderLayout.SOUTH);

        JButton btnDelete = new JButton("Delete");
        receiptButtonsPanel.add(btnDelete);

        JButton btnEdit = new JButton("Edit");
        receiptButtonsPanel.add(btnEdit);

        JButton btnFinish = new JButton("Finish");
        receiptButtonsPanel.add(btnFinish);

        customerCountLabel = new JLabel("Customers served: 0");
        customerCountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        customerCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(customerCountLabel, BorderLayout.NORTH);

        JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblQuantity = new JLabel("Quantity:");
        lblQuantity.setFont(new Font("Arial", Font.PLAIN, 16));
        quantityPanel.add(lblQuantity);

        textFieldQuantity = new JTextField("1");
        textFieldQuantity.setPreferredSize(new Dimension(50, 30));
        quantityPanel.add(textFieldQuantity);

        JButton btnAddToReceipt = new JButton("Add to Receipt");
        quantityPanel.add(btnAddToReceipt);

        mainPanel.add(quantityPanel, BorderLayout.SOUTH);

        JPanel customerNamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblCustomerName = new JLabel("Customer Name:");
        lblCustomerName.setFont(new Font("Arial", Font.PLAIN, 16));
        customerNamePanel.add(lblCustomerName);

        textFieldCustomerName = new JTextField(20);
        customerNamePanel.add(textFieldCustomerName);

        mainPanel.add(customerNamePanel, BorderLayout.NORTH);

        btnAddToReceipt.addActionListener(e -> {
            for (JPanel selectedPanel : selectedDrinkPanels) {
                Drink selectedDrink = (Drink) selectedPanel.getClientProperty("drink");
                String quantityText = textFieldQuantity.getText();
                try {
                    int quantity = Integer.parseInt(quantityText);
                    if (quantity > 0) {
                        double total = quantity * selectedDrink.getPrice();
                        receiptModel.addElement(selectedDrink.getName() + " x " + quantity + " = P" + String.format("%.2f", total));
                    } else {
                        JOptionPane.showMessageDialog(contentPane, "Invalid quantity", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(contentPane, "Invalid quantity format", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            clearSelection();
        });

        btnDelete.addActionListener(e -> {
            int selectedIndex = receiptList.getSelectedIndex();
            if (selectedIndex >= 0) {
                receiptModel.remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(contentPane, "No item selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEdit.addActionListener(e -> {
            int selectedIndex = receiptList.getSelectedIndex();
            if (selectedIndex >= 0) {
                String selectedItem = receiptModel.getElementAt(selectedIndex);
                String newQuantityText = JOptionPane.showInputDialog(contentPane, "Enter new quantity:", "Edit Quantity", JOptionPane.PLAIN_MESSAGE);
                if (newQuantityText != null) {
                    try {
                        int newQuantity = Integer.parseInt(newQuantityText);
                        if (newQuantity > 0) {
                            String[] parts = selectedItem.split(" x ");
                            String drinkName = parts[0];
                            double price = 0;
                            for (Drink drink : drinkList) {
                                if (drink.getName().equals(drinkName)) {
                                    price = drink.getPrice();
                                    break;
                                }
                            }
                            double newTotal = newQuantity * price;
                            receiptModel.setElementAt(drinkName + " x " + newQuantity + " = P" + String.format("%.2f", newTotal), selectedIndex);
                        } else {
                            JOptionPane.showMessageDialog(contentPane, "Invalid quantity", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(contentPane, "Invalid quantity format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(contentPane, "No item selected", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnFinish.addActionListener(e -> {
            if (!receiptModel.isEmpty()) {
                StringBuilder receiptContent = new StringBuilder();
                double totalPrice = 0;
                receiptContent.append("Receipt for ").append(textFieldCustomerName.getText().trim()).append(":\n");

                List<String> items = new ArrayList<>();
                for (int i = 0; i < receiptModel.size(); i++) {
                    items.add(receiptModel.getElementAt(i));
                    String item = receiptModel.getElementAt(i);
                    receiptContent.append(item).append("\n");
                    String[] parts = item.split(" = P");
                    double price = Double.parseDouble(parts[1]);
                    totalPrice += price;
                }
                receiptContent.append("\nTotal: P").append(String.format("%.2f", totalPrice));

                JTextArea receiptArea = new JTextArea(receiptContent.toString());
                receiptArea.setEditable(false);
                JOptionPane.showMessageDialog(contentPane, new JScrollPane(receiptArea), "Receipt", JOptionPane.INFORMATION_MESSAGE);

                addOrder(new Order(textFieldCustomerName.getText().trim(), items.toArray(new String[0])));

                customerCount++;
                customerCountLabel.setText("Customers served: " + customerCount);
                receiptModel.clear();
                textFieldCustomerName.setText("");
            } else {
                JOptionPane.showMessageDialog(contentPane, "No items in the receipt", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu managerMenu = new JMenu("Menu's Info");
        menuBar.add(managerMenu);

        JMenuItem addDrinkMenuItem = new JMenuItem("Add Drink");
        addDrinkMenuItem.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(contentPane, "Enter the name of the new drink:", "Add Drink", JOptionPane.PLAIN_MESSAGE);
            if (name != null && !name.trim().isEmpty()) {
                String category = JOptionPane.showInputDialog(contentPane, "Enter the category of the new drink:", "Add Drink", JOptionPane.PLAIN_MESSAGE);
                if (category != null && !category.trim().isEmpty()) {
                    String priceText = JOptionPane.showInputDialog(contentPane, "Enter the price of the new drink:", "Add Drink", JOptionPane.PLAIN_MESSAGE);
                    if (priceText != null && !priceText.trim().isEmpty()) {
                        try {
                            double price = Double.parseDouble(priceText);
                            String imagePath = JOptionPane.showInputDialog(contentPane, "Enter the image path of the new drink:", "Add Drink", JOptionPane.PLAIN_MESSAGE);
                            if (imagePath != null && !imagePath.trim().isEmpty()) {
                                // You should handle image loading and error checking here
                                ImageIcon imageIcon = new ImageIcon(imagePath); // Load image from path
                                addDrink(new Drink(name, imageIcon, price, category));
                            } else {
                                JOptionPane.showMessageDialog(contentPane, "Image path cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(contentPane, "Invalid price format", "Error", JOptionPane.ERROR_MESSAGE);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(contentPane, "Error loading image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        managerMenu.add(addDrinkMenuItem);

        JMenuItem removeDrinkMenuItem = new JMenuItem("Remove Drink");
        removeDrinkMenuItem.addActionListener(e -> {
            String drinkName = JOptionPane.showInputDialog(contentPane, "Enter the name of the drink to remove:", "Remove Drink", JOptionPane.PLAIN_MESSAGE);
            if (drinkName != null && !drinkName.trim().isEmpty()) {
                removeDrink(drinkName);
            }
        });
        managerMenu.add(removeDrinkMenuItem);

        JMenuItem updatePriceMenuItem = new JMenuItem("Update Price");
        updatePriceMenuItem.addActionListener(e -> {
            String drinkName = JOptionPane.showInputDialog(contentPane, "Enter the name of the drink to update the price:", "Update Price", JOptionPane.PLAIN_MESSAGE);
            if (drinkName != null && !drinkName.trim().isEmpty()) {
                String newPriceText = JOptionPane.showInputDialog(contentPane, "Enter the new price:", "Update Price", JOptionPane.PLAIN_MESSAGE);
                if (newPriceText != null && !newPriceText.trim().isEmpty()) {
                    try {
                        double newPrice = Double.parseDouble(newPriceText);
                        updateDrinkPrice(drinkName, newPrice);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(contentPane, "Invalid price format", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        managerMenu.add(updatePriceMenuItem);

        JMenuItem viewOrdersMenuItem = new JMenuItem("View Orders");
        viewOrdersMenuItem.addActionListener(e -> {
            displayOrders();
        });
        menuBar.add(viewOrdersMenuItem);

        JMenuItem viewDetailedReportMenuItem = new JMenuItem("View Detailed Report");
        viewDetailedReportMenuItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(contentPane, "View Detailed Report functionality will be implemented later.", "View Detailed Report", JOptionPane.INFORMATION_MESSAGE);
        });
        menuBar.add(viewDetailedReportMenuItem);

        JMenuItem viewSalesReportMenuItem = new JMenuItem("View Sales Report");
        viewSalesReportMenuItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(contentPane, "View Sales Report functionality will be implemented later.", "View Sales Report", JOptionPane.INFORMATION_MESSAGE);
        });
        menuBar.add(viewSalesReportMenuItem);

        loadInitialDrinks();
    }

    private void displayOrders() {
        StringBuilder ordersText = new StringBuilder();
        for (Order order : orders) {
        	 ordersText.append("Receipt:\n");
            ordersText.append("Customer Name: ").append(order.getCustomerName()).append("\n");
            ordersText.append("Drinks:\n");
            for (String item : order.getItems()) {
                ordersText.append("- ").append(item).append("\n");
            }
            ordersText.append("\n");
        }

        JTextArea ordersArea = new JTextArea(ordersText.toString());
        ordersArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(ordersArea);

        JOptionPane.showMessageDialog(contentPane, scrollPane, "Orders", JOptionPane.PLAIN_MESSAGE);
    }

    private void clearSelection() {
        for (JPanel selectedPanel : selectedDrinkPanels) {
            selectedPanel.setBackground(null);
        }
        selectedDrinkPanels.clear();
    }

    private void loadInitialDrinks() {
    	
    	//new
    	addDrink(new Drink("Espresso",new ImageIcon("\\\\Users\\\\ADMIN\\\\Pictures/espresso_1.jpg"), 59.00, "Hot Coffee"));
    	 addDrink(new Drink("Americano", new ImageIcon("\\Users\\ADMIN\\Pictures/americano coffee.jpg"), 59.00, "Hot Coffee"));
    	
    	 
    	 addDrink(new Drink("Iced Cappuccino", new ImageIcon("\\Users\\ADMIN\\Downloads/iced-cappuccino.jpg"), 89.00, "Cold Drinks"));
    	
    	
    	addDrink(new Drink("Espresso",new ImageIcon("\\Users\\ADMIN\\Pictures/espresso_1.jpg"), 59.00, "Cold Drinks"));
    	addDrink(new Drink("Espresso",new ImageIcon("\\Users\\ADMIN\\Pictures/espresso_1.jpg"), 59.00, "Milk Drinks"));
    	addDrink(new Drink("Espresso",new ImageIcon("\\Users\\ADMIN\\Pictures/espresso_1.jpg"), 59.00, "soft")); //ito
        
    
    	//old
    	addDrink(new Drink("Espresso",new ImageIcon("\\Users\\ADMIN\\Pictures/espresso_1.jpg"), 59.00, "Milk Drinks"));
        addDrink(new Drink("Hot Coffee", null, 50.0, "Hot Coffee"));
        addDrink(new Drink("Iced Coffee", null, 70.0, "Cold Drinks"));
        addDrink(new Drink("Milkshake", null, 80.0, "Milk Drinks"));
    }

    public void addDrink(Drink drink) {
        drinkList.add(drink);
        JPanel drinkPanel = createDrinkPanel(drink);
        String category = drink.getCategory();

        boolean categoryExists = false;

        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (tabbedPane.getTitleAt(i).equals(category)) {
                JPanel categoryPanel = (JPanel) tabbedPane.getComponentAt(i);
                categoryPanel.add(drinkPanel);
                categoryPanel.revalidate();
                categoryPanel.repaint();
                categoryExists = true;
                break;
            }
        }

        if (!categoryExists) {
            JPanel newCategoryPanel = createDrinksPanel(category);
            newCategoryPanel.add(drinkPanel);
            tabbedPane.addTab(category, newCategoryPanel);
        }
    }

    public void removeDrink(String drinkName) {
        drinkList.removeIf(drink -> drink.getName().equals(drinkName));
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            JPanel categoryPanel = (JPanel) tabbedPane.getComponentAt(i);
            for (Component component : categoryPanel.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel drinkPanel = (JPanel) component;
                    Drink drink = (Drink) drinkPanel.getClientProperty("drink");
                    if (drink.getName().equals(drinkName)) {
                        categoryPanel.remove(drinkPanel);
                        categoryPanel.revalidate();
                        categoryPanel.repaint();
                        break;
                    }
                }
            }
        }
    }

    public void updateDrinkPrice(String drinkName, double newPrice) {
        for (Drink drink : drinkList) {
            if (drink.getName().equals(drinkName)) {
                drink.setPrice(newPrice);
                for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                    JPanel categoryPanel = (JPanel) tabbedPane.getComponentAt(i);
                    for (Component component : categoryPanel.getComponents()) {
                        if (component instanceof JPanel) {
                            JPanel drinkPanel = (JPanel) component;
                            Drink panelDrink = (Drink) drinkPanel.getClientProperty("drink");
                            if (panelDrink.getName().equals(drinkName)) {
                                JLabel priceLabel = (JLabel) drinkPanel.getComponent(2);
                                priceLabel.setText("P" + newPrice);
                                drinkPanel.revalidate();
                                drinkPanel.repaint();
                                break;
                            }
                        }
                    }
                }
                break;
            }
        }
    }

    private JPanel createDrinkPanel(Drink drink) {
        JPanel drinkPanel = new JPanel();
        drinkPanel.setLayout(new BorderLayout());

        JLabel lblImage = new JLabel(drink.getImage());
        drinkPanel.add(lblImage, BorderLayout.CENTER);

        JLabel lblName = new JLabel(drink.getName(), SwingConstants.CENTER);
        lblName.setFont(new Font("Arial", Font.PLAIN, 16));
        drinkPanel.add(lblName, BorderLayout.NORTH);

        JLabel lblPrice = new JLabel("P" + drink.getPrice(), SwingConstants.CENTER);
        lblPrice.setFont(new Font("Arial", Font.PLAIN, 14));
        drinkPanel.add(lblPrice, BorderLayout.SOUTH);

        drinkPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        drinkPanel.putClientProperty("drink", drink);

        drinkPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedDrinkPanels.contains(drinkPanel)) {
                    drinkPanel.setBackground(null);
                    selectedDrinkPanels.remove(drinkPanel);
                } else {
                    drinkPanel.setBackground(Color.LIGHT_GRAY);
                    selectedDrinkPanels.add(drinkPanel);
                }
            }
        });

        return drinkPanel;
    }

    private JPanel createDrinksPanel(String category) {
        JPanel drinksPanel = new JPanel();
        drinksPanel.setLayout(new GridLayout(0, 3, 10, 10));
        for (Drink drink : drinkList) {
            if (drink.getCategory().equals(category)) {
                drinksPanel.add(createDrinkPanel(drink));
            }
        }
        return drinksPanel;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    private static class Order {
        private String customerName;
        private List<String> items;

        public Order(String customerName, String[] items) {
            this.customerName = customerName;
            this.items = new ArrayList<>();
            for (String item : items) {
                this.items.add(item);
            }
        }

        public String getCustomerName() {
            return customerName;
        }

        public List<String> getItems() {
            return items;
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Menu frame = new Menu();
                frame.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
