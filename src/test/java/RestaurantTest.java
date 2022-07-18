import com.sun.source.tree.ModuleTree;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    @BeforeEach
    public void refactoring_repeated_code(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant spiedRestaurant = Mockito.spy(restaurant);

        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("11:00:00"));

        boolean isOpen = spiedRestaurant.isRestaurantOpen();
        assertTrue(isOpen);

    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant spiedRestaurant = Mockito.spy(restaurant);

        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("10:00:00"));

        boolean isOpen = spiedRestaurant.isRestaurantOpen();
        assertFalse(isOpen);
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
    public void removing_item_that_does_not_exist_should_throw_exception() {


        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //This is the part 3 test cases for calculating the total price of menu items.
    @Test
    public void adding_individual_price_of_menu_items_will_give_a_total_price(){
        List<String> orderedMenuItems= new ArrayList<>();
        orderedMenuItems.add("Sweet corn soup");
        orderedMenuItems.add("Vegetable lasagne");
        int sum = restaurant.getTotalPrice(orderedMenuItems);
        assertEquals(119+269,sum);
    }

    @Test
    public void adding_individual_price_of_menu_items_will_give_a_total_price_test_2(){
        restaurant.addToMenu("Sizzling brownie",319);
        restaurant.addToMenu("French fries",0);
        List<String> orderedMenuItems= new ArrayList<>();
        orderedMenuItems.add("Sizzling brownie");
        orderedMenuItems.add("French fries");
        int sum = restaurant.getTotalPrice(orderedMenuItems);
        assertEquals(319+0,sum);
    }


}