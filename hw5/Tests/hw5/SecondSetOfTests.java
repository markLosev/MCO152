package hw5;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class SecondSetOfTests {

    LinkedList<Integer> list;
    Integer number = 1;


    @Before
    public void setUp() throws Exception {
        list = new LinkedList<>();

    }

    /*Clear Method*/
    @Test
    public void afterClearSizeShouldBeZero() {

        list.add(number);
        list.clear();
        assertEquals(0,list.size());
    }

    /*Equals Method*/
    @Test
    public void equalsShouldReturnTrue() {

        list.add(4);
        list.add(5);

        LinkedList<Integer> secondList = new LinkedList<>();
        secondList.add(4);
        secondList.add(5);

        assertTrue(list.equals(secondList));
    }

    @Test
    public void equalsShouldReturnFalseBecauseAmountOfElementsAreDifferent() {

        list.add(4);
        list.add(5);

        LinkedList<Integer> secondList = new LinkedList<>();
        secondList.add(6);

        assertFalse(list.equals(secondList));
    }

    @Test
    public void equalsShouldReturnFalseBecauseOrderIsDifferent() {

        list.add(4);
        list.add(5);

        LinkedList<Integer> secondList = new LinkedList<>();
        secondList.add(5);
        secondList.add(4);

        assertFalse(list.equals(secondList));
    }

    /*HashCode Method*/
    @Test
    public void hashCodeShouldBe1090() {

        list.add(4);
        list.add(5);

        assertEquals(1090, list.hashCode());
    }

    /*IndexOf Method*/
    @Test
    public void indexReturnedShouldBe1() {

        list.add(4);
        list.add(5);
        list.add(6);

        assertEquals(1,list.indexOf(5));
    }

    @Test
    public void indexReturnedShouldBeNegative1() {

        list.add(4);
        list.add(5);
        list.add(6);

        assertEquals(-1,list.indexOf(7));
    }

    /*Iterator Method*/
    @Test
    public void objectReturnedShouldBeOfSubclassOfIteratorInterface() {

        assertTrue(list.iterator() instanceof Iterator);
    }


}