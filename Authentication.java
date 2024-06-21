package Acosta_gui;

public class Authentication {

    // Dummy credentials for the cashier
    private static final String CASHIER_USERNAME = "cashier"  ;
    private static final String CASHIER_PASSWORD = "123";

    public static boolean authenticate(String username, String password) {
        return CASHIER_USERNAME.equals(username) && CASHIER_PASSWORD.equals(password);
    }
}
