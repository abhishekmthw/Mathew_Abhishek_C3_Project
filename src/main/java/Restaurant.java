import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public int calculateOrderTotal(ArrayList<String> orderItems) throws itemNotFoundException {
        if(orderItems.size() == 0) {
            return 0;
        }
        int orderTotal = 0;
        ArrayList<String> menuItems = new ArrayList<String>();
        for(Item menuItem: this.menu) {
            menuItems.add(menuItem.getName());
        }
        for(String orderItem: orderItems) {
            if(!menuItems.contains(orderItem)) {
                throw new itemNotFoundException(orderItem);
            }
        }
        for(Item item: this.menu) {
            if(orderItems.contains(item.getName())) {
                orderTotal += item.getPrice();
            }
        }
        return orderTotal;
    }

    public boolean isRestaurantOpen() {
        LocalTime timeNow = this.getCurrentTime();
        if(timeNow.isAfter(this.openingTime) && timeNow.isBefore(closingTime)) {
            return true;
        } else {
            return false;
        }
    }

    public LocalTime getCurrentTime(){ return  LocalTime.now(); }

    public List<Item> getMenu() {
        return this.menu;
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }

    public String getName() {
        return name;
    }

}
