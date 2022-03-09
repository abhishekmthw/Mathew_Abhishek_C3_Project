import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;

    @Mock
    Restaurant spiedRestaurant;

    @BeforeEach
    public void setup() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>ORDER TOTAL<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Test
    public void calculate_order_total_should_return_correct_amount() throws itemNotFoundException  {
        ArrayList<String> orderItems = new ArrayList<String>();
        orderItems.add("Sweet corn soup");
        orderItems.add("Vegetable lasagne");
        assertEquals(388, restaurant.calculateOrderTotal(orderItems));
    }

    @Test
    public void calculate_order_total_should_return_zero_when_no_items_are_selected() throws itemNotFoundException  {
        ArrayList<String> orderItems = new ArrayList<String>();
        assertEquals(0, restaurant.calculateOrderTotal(orderItems));
    }

    @Test
    public void calculate_order_total_should_throw_exception_when_item_is_not_listed() throws itemNotFoundException {
        ArrayList<String> orderItems = new ArrayList<String>();
        orderItems.add("Sweet corn soup");
        orderItems.add("French fries");
        assertThrows(itemNotFoundException.class, ()->restaurant.calculateOrderTotal(orderItems));
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>ORDER TOTAL<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        spiedRestaurant = Mockito.spy(restaurant);
        Mockito.lenient().when(spiedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("18:00:30"));
        assertTrue(spiedRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        spiedRestaurant = Mockito.spy(restaurant);
        Mockito.lenient().when(spiedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("23:00:00"));
        assertFalse(spiedRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() throws itemNotFoundException {
        assertThrows(itemNotFoundException.class, ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}