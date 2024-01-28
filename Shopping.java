import java.util.*;
class User {
    private String name;
    private String pw;
    private String phonenum;
    private static HashMap<String,Integer> usercart;
    private static HashMap<String, User> registeredUsers = new HashMap<>();
    public User(String name, String pw, String phonenum) {
        this.name = name;
        this.pw = pw;
        this.phonenum = phonenum;
        this.usercart=new HashMap<>();
    }
    public User(){ 
    }
    public void orderhistory(String userName, Map<String, Product> productlist) {
        float sum = 0;
        for (Map.Entry<String, Integer> entry : registeredUsers.get(userName).usercart.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("Item: " + key + ", Value: " + value);
            sum += (productlist.get(key).price * value);
        }
        if(sum==0.0){
            System.out.println("Your cart is empty...");
        }
        else{
            System.out.println("Checkoutsum : " + sum);
        }        
    }
    public void addproduct(String userName,String productname,int q){
        registeredUsers.get(userName).usercart.put(productname,q);
        System.out.println("ITEM ADDED TO CART...");
    }
    public void removeProduct(String userName,String productname,int q){
        int existing_quantity=registeredUsers.get(userName).usercart.get(productname);
        if(existing_quantity<q){
            System.out.println("Unreacheable request...");
        }
        else if(existing_quantity==q){
            registeredUsers.get(userName).usercart.remove(productname);
        }
        else{
            registeredUsers.get(userName).usercart.put(productname,existing_quantity-q);
        }
        System.out.println("ITEM REMOVED FROM CART...");
    }
    public boolean isAlreadyRegistered(String userName) {
        return registeredUsers.containsKey(userName);
    }
    public boolean checkpw(String username,String password){
        return (registeredUsers.get(username).pw.equals(password));
    }
    public void registerUser(User user) {       
            registeredUsers.put(user.name, user);
            System.out.println("Registration successful...");
    }  
}
class Product{
    String itemname;
    int quantity;
    float price;
    Product(String itemname,int quantity,float price){
        this.itemname=itemname;
        this.quantity=quantity;
        this.price=price;
    }    
}
class Shopping {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashMap<String,Product>  productlist=new HashMap<>();
        Product p = new Product("Sugarpackets", 2, 44);
        productlist.put(p.itemname, p);        
        p = new Product("32inchtv", 20, 14990);
        productlist.put(p.itemname, p);        
        p = new Product("Charger cables", 18, 100);
        productlist.put(p.itemname, p);        
        p = new Product("Smart watch", 10, 20000);
        productlist.put(p.itemname, p);        
        p = new Product("Apple SE", 18, 9999);
        productlist.put(p.itemname, p);        
        p = new Product("washing machine", 59, 56999);
        productlist.put(p.itemname, p);

        loops:while (true) {
            System.out.println("-----WELCOME TO FLIPKART-----");
            System.out.println("Choose...........");
            System.out.println("1. Signup");
            System.out.println("2. Login");
            System.out.println("3. Close Application");
            int choice = scanner.nextInt();
            User newUser = new User();
            switch (choice) {
                case 1:
                    System.out.println("Enter your name: ");
                    scanner.nextLine();
                    String name = scanner.nextLine();                    
                    while (newUser.isAlreadyRegistered(name)) {
                        System.out.println("User already registered. Enter a different name : ");
                        name = scanner.nextLine();
                    }
                    System.out.println("Enter your password: ");
                    String password = scanner.nextLine();
                    System.out.println("Enter your phone number: ");
                    String phoneNumber = scanner.nextLine();
                    User user = new User(name, password, phoneNumber);
                    user.registerUser(user);
                    break;               
                case 2:
                    System.out.println("Enter your name: ");
                    scanner.nextLine();
                    String loginName = scanner.nextLine();
                    if (newUser.isAlreadyRegistered(loginName)) {
                        System.out.println("Enter your pw: ");
                        String loginpw = scanner.nextLine();
                        if (newUser.checkpw(loginName,loginpw)) {
                             System.out.println("Login successful...\n");
                            loops1: while(true){
                                System.out.println("\n1. Available product");
                                System.out.println("2. Add an item to cart");
                                System.out.println("3. Remove an item from cart");
                                System.out.println("4. Display order history");
                                System.out.println("5. Logout\n");
                                int choice1=scanner.nextInt();
                                switch(choice1){
                                    case 1:
                                        for (Product product : productlist.values()) {
                                            System.out.println(product.itemname + "  " + product.price+"/-  "+product.quantity);
                                        }
                                        break;
                                    case 2:
                                        System.out.println("Enter the item of the product u want : ");
                                        scanner.nextLine();
                                        String item=scanner.nextLine();
                                        if(!productlist.containsKey(item) || productlist.get(item).quantity==0){
                                            System.out.println("Out of Stock...");
                                        }
                                        else{
                                            System.out.println("Enter the quantity of the item : ");
                                            int q=scanner.nextInt();
                                            if(q>productlist.get(item).quantity){
                                                System.out.println("Ur required quantity is unavailble...");
                                            }
                                            else{
                                                productlist.put(item,new Product(productlist.get(item).itemname,productlist.get(item).quantity-q,productlist.get(item).price));
                                                newUser.addproduct(loginName,item,q);
                                            }                                            
                                        }                                        
                                        break;
                                    case 3:
                                        System.out.println("Enter the item u want to remove : ");
                                        scanner.nextLine();
                                        item=scanner.nextLine();
                                        System.out.println("Enter the quantity of the item : ");
                                        int q=scanner.nextInt();
                                        productlist.put(item,new Product(productlist.get(item).itemname,productlist.get(item).quantity+q,productlist.get(item).price));
                                        newUser.removeProduct(loginName,item,q);
                                        break;
                                    case 4:
                                        newUser.orderhistory(loginName,productlist);
                                        break;
                                    case 5:
                                        break loops1;
                                    default:
                                        System.out.println("Invalid input");                                      
                                }                          
                             }
                        }
                        else{
                            System.out.println("Wrong password...");
                        }                                              
                    } 
                    else {
                        System.out.println("User not found. Please register.");
                    }
                    break;
                    case 3:
                        break loops;
                    default:
                        System.out.println("Invalid input");                
            }
        }        
    }
}
