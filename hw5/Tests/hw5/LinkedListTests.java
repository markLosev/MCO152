package hw5;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class LinkedListTests {

    LinkedList<Integer> list;
    Integer number = 1;


    @Before
    public void setUp() throws Exception {
        list = new LinkedList<>();

    }

    /*Contains Method*/
    @Test
    public void returnsTrueIfContainsElement() {

        list.add(number);
        assertTrue(list.contains(number));
    }

    @Test
    public void returnsFalseIfDoesntContainsElement() {

        list.add(1);
        assertFalse(list.contains(10));
    }

    /*Add Method*/
    @Test
    public void wasElementAddedToEndOfList() {

        list.add(number);
        assertEquals(list.get(list.size() - 1), number);
    }

    @Test
    public void returnsTrueWhenAddedSuccesfuly() {

        boolean isAdded = list.add(number);
        assertEquals(list.get(list.size() - 1), number);
        assertTrue(isAdded);
    }

    /*Remove Method*/
    @Test
    public void removalDecreasesSizeByOne() {

        list.add(number);
        list.add(5);
        int previousSize = list.size();

        list.remove(number);
        int newSize = list.size();

        assertEquals(previousSize - 1, newSize);


    }

    @Test
    public void sizeRemainsUnchangedIfElementToRemoveDoesntExist() {

        list.add(4);
        list.add(5);
        int previousSize = list.size();

        list.remove((Integer) 10);
        int newSize = list.size();

        assertEquals(previousSize, newSize);
    }

    /*ContainsAll Method*/
    @Test
    public void listDoesContainAllElementsFromGivenList() {

        list.add(4);
        list.add(5);

        LinkedList<Integer> collection = new LinkedList<>();
        collection.add(4);
        collection.add(5);

        assertTrue(list.containsAll(collection));
    }

    @Test
    public void listDoesntContainAllElementsFromGivenList() {

        list.add(4);
        list.add(5);

        LinkedList<Integer> collection = new LinkedList<>();
        collection.add(4);
        collection.add(6);

        assertFalse(list.containsAll(collection));
    }

    @Test
    public void listDoesntContainAllElementsFromGivenListOnlyContainingNull() {

        list.add(4);
        list.add(5);

        LinkedList<Integer> collection = new LinkedList<>();
        collection.add(null);
        collection.add(null);

        assertFalse(list.containsAll(collection));
    }

    /*AddAll to end of List Method*/
    @Test
    public void listAddsAllElementsFromGivenList() {

        list.add(4);
        list.add(5);
        int previousSize = list.size();

        LinkedList<Integer> collection = new LinkedList<>();
        collection.add(6);
        collection.add(7);

        list.addAll(collection);
        int newSize = list.size();

        assertEquals(previousSize + collection.size(), newSize);
    }

    @Test
    public void listContainsAllElementsAfterAddition() {

        list.add(4);
        list.add(5);

        LinkedList<Integer> collection = new LinkedList<>();
        collection.add(6);
        collection.add(7);

        list.addAll(collection);

        assertTrue(list.containsAll(collection));
    }

    @Test
    public void listAddsAllElementsFromGivenListWhichAreNull() {

        list.add(4);
        list.add(5);
        int previousSize = list.size();

        LinkedList<Integer> collection = new LinkedList<>();
        collection.add(null);
        collection.add(null);

        list.addAll(collection);
        int newSize = list.size();

        assertEquals(previousSize + collection.size(), newSize);
    }


    /*AddAll at index Method*/
    @Test
    public void newElementsStartAtGivenIndex() {

        list.add(4);
        list.add(5);


        LinkedList<Integer> collection = new LinkedList<>();
        collection.add(6);
        collection.add(7);

        list.addAll(1, collection);

        assertEquals((Integer) 6, list.get(1));
    }

    @Test
    public void olderElemetsWereProperlyShifted() {

        list.add(4);
        list.add(5);


        LinkedList<Integer> collection = new LinkedList<>();
        collection.add(6);
        collection.add(7);

        list.addAll(1, collection);

        assertEquals((Integer) 5, list.get(list.size() - 1));
    }

    @Test
    public void emptyCollectionDoesntChangeElementsInList() {

        list.add(4);
        list.add(5);


        LinkedList<Integer> collection = new LinkedList<>();

        list.addAll(1, collection);

        assertEquals((Integer) 5, list.get(1));
    }

    /*RemoveAll Method*/
    @Test
    public void listDoesntContainSpecidiedElementsAfterRemoveAll() {

        list.add(4);
        list.add(5);


        LinkedList<Integer> collection = new LinkedList<>();
        collection.add(4);
        collection.add(5);

        list.removeAll(collection);

        assertFalse(list.containsAll(collection));
    }

    @Test
    public void listRemovesAllSpecifiedNumbersInListIncludingDuplicates() {

        list.add(4);
        list.add(5);
        list.add(5);


        LinkedList<Integer> collection = new LinkedList<>();
        collection.add(4);
        collection.add(5);

        list.removeAll(collection);

        assertFalse(list.contains(5));
    }

    @Test
    public void listRemovesNullIfNullIsInCollection() {

        list.add(4);
        list.add(null);

        LinkedList<Integer> collection = new LinkedList<>();
        collection.add(null);

        list.removeAll(collection);

        assertFalse(list.containsAll(collection));
    }

    /*RetainAll Method*/
    @Test
    public void listRetainsOnlyElementsInCollection() {

        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);


        LinkedList<Integer> collection = new LinkedList<>();
        collection.add(4);
        collection.add(5);

        list.retainAll(collection);

        assertTrue(list.containsAll(collection));
    }

    @Test
    public void listRemovesElementsNotInCollection() {

        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);


        LinkedList<Integer> collection = new LinkedList<>();
        collection.add(4);
        collection.add(5);

        list.retainAll(collection);

        assertFalse(list.contains(6));
        assertFalse(list.contains(7));
    }

    /*Size Method*/
    @Test
    public void sizeOfListIsEqualToAmountOfElementsInList() {

        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);

        assertEquals(4, list.size());
    }

    //This test took me forever to run, and I never got to the end... Probably not the best way to do it.
/*    @Test
    public void sizeOfListShouldEqualIntegerMaxValueEvenThoughItHasMoreElements() {

        for (int i = 0; i < Integer.MAX_VALUE ; i++) {
            list.add(number);
        }
*//*        System.out.println(list.size());
        list.add(number);
        System.out.println(list.size());*//*

        assertEquals(Integer.MAX_VALUE, list.size());
    }*/

    @Test
    public void sizeOfListIsEqualToZero() {

        assertEquals(0, list.size());
    }

    @Test void addElementToTheSpecifiedIndex() {
        list.add(1);
        list.add(2);
        list.add(3);

        list.add(0, 5);

        assertEquals(Integer.MAX_VALUE, list.size());
        System.out.println("hi");
    }

    /*isEmpty Method*/
    @Test
    public void isEmptyShouldReturnFalse() {

        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);

        assertFalse(list.isEmpty());
    }

    @Test
    public void isEmptyShouldReturnTrue() {

        assertTrue(list.isEmpty());
    }

    /*isEmpty Method*/



}