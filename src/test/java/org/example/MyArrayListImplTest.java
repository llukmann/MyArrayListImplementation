package org.example;

import org.example.exceptions.EmptyListException;
import org.example.interfaces.MyList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MyArrayListImplTest {

    @Test
    public void testAddToEmptyListCorrectly() {
        MyList<Integer> list = new MyArrayListImpl<Integer>();

        list.add(100);

        assertEquals(1, list.getSize());
    }

    @Test
    public void testAddToNotEmptyListCorrectly() {
        String[] arr = {"str1", "str2", "str3"};

        MyList<String> list = new MyArrayListImpl<>(arr);

        list.add("str4");

        assertEquals(4, list.getSize());
    }

    @Test
    public void testAddWithResizeCorrectly() {
        MyList<Integer> list = new MyArrayListImpl<>(2);

        for (int i = 0; i < 100; i++){
            list.add((int) (Math.random() * 10));
            assertEquals(i + 1, list.getSize());
        }


    }

    @Test
    public void testAddByIndexCorrectly() throws EmptyListException {
        MyList<Integer> list = new MyArrayListImpl<>();

        list.add(1);
        list.add(3);
        list.addByIndex(1, 2);

        assertEquals(3, list.getSize());
        assertEquals(2, list.getElement(1));
    }

    @Test
    public void testAddByIndexTooBigIndex() throws IndexOutOfBoundsException {
        MyList<Integer> list = new MyArrayListImpl<>();

        list.add(1);
        list.add(2);
        IndexOutOfBoundsException thrown = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            list.addByIndex(5, 3);
        });

        assertEquals("Index is too big, should not be larger than array size: 2", thrown.getMessage());
    }

    @Test
    public void testAddByIndexToEmptyListCorrectly() throws EmptyListException {
        MyList<Integer> list = new MyArrayListImpl<>();

        list.addByIndex(0, 2);
        list.addByIndex(0, 1);

        assertEquals(2, list.getSize());
        assertEquals(1, list.getElement(0));
        assertEquals(2, list.getElement(1));
    }

    @Test
    public void testAddByIndexWithResizeCorrectly() {
        MyList<Integer> list = new MyArrayListImpl<>();

        for (int i = 0; i < 100; i++) {
            list.addByIndex(0, (int) (Math.random() * 10));
            assertEquals(i + 1, list.getSize());
        }
    }

    @Test
    public void testSort() throws EmptyListException {
        MyList<Integer> list = new MyArrayListImpl<>();
        list.add(3);
        list.add(1);
        list.add(2);
        list.sort(Integer::compare);
        assertEquals(1, list.getElement(0));
        assertEquals(2, list.getElement(1));
        assertEquals(3, list.getElement(2));
    }

    @Test
    public void testDeleteElementCorrectly() throws IndexOutOfBoundsException, EmptyListException {
        MyList<Integer> list = new MyArrayListImpl<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.deleteElement(1);
        assertEquals(2, list.getSize());
        assertEquals(3, list.getElement(1));
    }

    @Test
    public void testDeleteElementFromEmptyList() throws EmptyListException {
        MyList<Integer> list = new MyArrayListImpl<>();

        EmptyListException thrown = Assertions.assertThrows(EmptyListException.class, () -> {
            list.deleteElement(0);
        });

        assertEquals("Cannot delete from empty list", thrown.getMessage());
    }

    @Test
    public void testDeleteElementWithTooBigIndex() throws IndexOutOfBoundsException {
        MyList<Integer> list = new MyArrayListImpl<>();

        for (int i = 0; i < 10; i++) {
            list.add((int)(Math.random() * 10));
        }

        IndexOutOfBoundsException thrown = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            list.deleteElement(10);
        });

        assertEquals("Index is too big, should not be larger than array size: 9", thrown.getMessage());
    }

    @Test
    public void testDeleteAllElementsSequentiallyCorrectly() throws IndexOutOfBoundsException, EmptyListException {
        MyList<Integer> list = new MyArrayListImpl<>();

        for (int i = 0; i < 100; i++){
            list.add((int) (Math.random() * 10));
        }

        for (int i = 100; i > 0; i--){
            list.deleteElement(i-1);
            assertEquals(i-1, list.getSize());
        }
    }

    @Test
    public void testReplaceElementCorrectly() throws IndexOutOfBoundsException, EmptyListException {
        MyList<Integer> list = new MyArrayListImpl<>();
        list.add(1);
        list.add(2);
        list.replaceElement(1, 3);
        assertEquals(3, list.getElement(1));
    }

    @Test
    public void testReplaceElementFromEmptyList() throws EmptyListException{
        MyList<Integer> list = new MyArrayListImpl<>();

        EmptyListException thrown = Assertions.assertThrows(EmptyListException.class, () -> {
            list.replaceElement(0, 3);
        });

        assertEquals("Cannot replace from empty list", thrown.getMessage());
    }

    @Test
    public void testReplaceElementWithTooBigIndex() throws IndexOutOfBoundsException {
        MyList<Integer> list = new MyArrayListImpl<>();

        for (int i = 0; i < 10; i++) {
            list.add((int)(Math.random() * 10));
        }

        IndexOutOfBoundsException thrown = Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            list.replaceElement(10, 3);
        });

        assertEquals("Index is too big, should not be larger than array size: 9", thrown.getMessage());
    }

    @Test
    public void testReplaceAllElementsFromListSequentiallyCorrectly() throws EmptyListException {
        MyList<Integer> list = new MyArrayListImpl<>();

        for (int i = 0; i < 100; i++) {
            list.add((int)(Math.random() * 10));
        }

        for (int i = 0; i < 100; i++) {
            list.replaceElement(i, (int)(Math.random() * 10));
        }

        assertEquals(100, list.getSize());
    }

    @Test
    public void testClear() {
        MyList<Integer> list = new MyArrayListImpl<>();
        list.add(1);
        list.add(2);
        list.clear();
        assertEquals(0, list.getSize());
    }
}
