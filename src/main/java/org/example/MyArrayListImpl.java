package org.example;

import org.example.exceptions.EmptyListException;
import org.example.interfaces.MyList;

import java.util.Comparator;

/**
 * @author lukman
 * My array list Class, implements interface interfaces.MyList
 * @param <T>
 */
public class MyArrayListImpl<T> implements MyList<T> {
    /**
     * Array of elements of class T
     */
    private T[] values;
    /**
     * Int value indicating the number of elements in the array
     */
    private int size;
    /**
     * Default array length
     * @see MyArrayListImpl#MyArrayListImpl()
     */
    private static final int DEFAULT_SIZE = 10;

    /**
     * Constructor without params, use default value of array length
     * @see MyArrayListImpl#MyArrayListImpl(int)
     * @see MyArrayListImpl#MyArrayListImpl(Object[])
     * @see MyArrayListImpl#DEFAULT_SIZE
     */
    public MyArrayListImpl(){
        this.values = (T[]) new Object[DEFAULT_SIZE];
        this.size = 0;
    }

    /**
     * Constructor with array length value in param
     * @param arrayLength - array length
     * @see MyArrayListImpl#MyArrayListImpl()
     * @see MyArrayListImpl#MyArrayListImpl(Object[])
     */
    public MyArrayListImpl(int arrayLength){
        this.values = (T[]) new Object[arrayLength];
        this.size = 0;
    }

    /**
     * Constructor based on array in param
     * @param values - array of elements of class T
     * @see MyArrayListImpl#MyArrayListImpl()
     * @see MyArrayListImpl#MyArrayListImpl(int)
     */
    public MyArrayListImpl(T[] values){
        this.values = values;
        this.size = values.length;
    }

    /**
     * Method that provides dynamic expansion of an array when it's full
     */
    private void resize(){
        if (size >= values.length - 1) {
            int newSize = size * 2 + 1;
            T[] newArray = (T[]) new Object[newSize];
            System.arraycopy(values, 0, newArray, 0, size);
            values = newArray;
        }
    }

    /**
     * Method that provides adding element into end of array
     * @param t - object of class T, adding element
     */
    @Override
    public void add(T t) {
        resize();
        values[size] = t;
        size++;
    }

    /**
     * Method that provides adding element into array by index with shifting other elements by 1 to the right
     * @param index - int value of index of adding element
     * @param t - object of class T, adding element
     * @throws IndexOutOfBoundsException if index in params is bigger than array length. If index == arrayLength, this method works like add()
     */
    @Override
    public void addByIndex(int index, T t) throws IndexOutOfBoundsException {
        if (index > size) {
            throw new IndexOutOfBoundsException("Index is too big, should not be larger than array size: " + size);
        } else if (index == size) {
            resize();
            add(t);
        } else {
            resize();
            for (int i = size; i >= index; i--) {
                values[i + 1] = values[i];
            }
            values[index] = t;
            size++;
        }
    }

    /**
     * Method that provides getting element from array by index
     * @param index - int value of index of getting element
     * @return element from array, object of class T
     * @throws EmptyListException if array is empty
     * @throws IndexOutOfBoundsException if the index is bigger than or equal to the length of the array
     */
    @Override
    public T getElement(int index) throws EmptyListException, IndexOutOfBoundsException{
        if (size == 0) {
            throw new EmptyListException("Cannot get element from empty list");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("Index is too big, should not be larger than array size: " + size);
        } else {
            return (T) values[index];
        }
    }

    /**
     * Method that provides deleting element from array by index with shifting other elements by 1 to the left
     * @param index - int index value of the element to be removed
     * @throws EmptyListException if array is empty
     * @throws IndexOutOfBoundsException if the index is bigger than or equal to the length of the array
     */
    @Override
    public void deleteElement(int index) throws EmptyListException, IndexOutOfBoundsException{
        if (size == 0) {
            throw new EmptyListException("Cannot delete from empty list");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index is too big, should not be larger than array size: " + (size - 1));
        } else {
            for (int i = index; i < size - 1; i++) {
                values[i] = values[i + 1];
            }
            size--;
        }
    }

    /**
     * Method that provides deleting all elements from array. In result use constructor without params
     * @see MyArrayListImpl#MyArrayListImpl()
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++){
            values[i] = null;
        }

        this.values = (T[]) new Object[DEFAULT_SIZE];
        this.size = 0;
    }

    /**
     * @return size - number of elements in array
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Sorts array elements according to the condition of the comparator from param
     * @param comparator - object of class that implements interface Comparator
     * @return sorted array
     */
    @Override
    public MyList<T> sort(Comparator<T> comparator) {
        quickSort(values, 0, size - 1, comparator);
        return this;
    }

    /**
     * Method that provides replace element by index. Unlike the addByIndex(int, Object) method, it replaces the element at the specified index,
     * rather than adding it, so the length of the array does not increase
     * @see MyArrayListImpl#addByIndex(int, Object)
     * @param index - int index value of replacement element
     * @param t - object of class T, injected into array
     * @throws EmptyListException if array is empty
     * @throws IndexOutOfBoundsException if the index is bigger than or equal to the length of the array
     */
    @Override
    public void replaceElement(int index, T t) throws EmptyListException, IndexOutOfBoundsException {
        if (size == 0) {
            throw new EmptyListException("Cannot replace from empty list");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index is too big, should not be larger than array size: " + (size - 1));
        } else {
            resize();
            values[index] = t;
        }
    }

    /**
     * Method that prints array elements to the console
     */
    @Override
    public void printList(){
        for (int i = 0; i < size; i++) {
            System.out.println(values[i].toString());
        }
    }

    /**
     * Method that implements the quicksort algorithm. Recursively calls itself until the array is sorted
     * @param arr - array to be sorted
     * @param from - index of start of array
     * @param to - index of end of array
     * @param comparator -  object of class that implements interface Comparator
     */
    private void quickSort(T[] arr, int from, int to, Comparator<T> comparator){
        if (from < to) {

            int divideIndex = partition(arr, from, to, comparator);

            quickSort(arr, from, divideIndex - 1, comparator);

            quickSort(arr, divideIndex, to, comparator);
        }
    }

    /**
     * An auxiliary quicksort method that rearranges elements in an array
     * @see MyArrayListImpl#quickSort(Object[], int, int, Comparator)
     * @param arr - array to be sorted
     * @param from - index of start of array
     * @param to - index of end of array
     * @param comparator - object of class that implements interface Comparator
     * @return int value - index of the element by which the array will be divided
     */
    private int partition(T[] arr, int from, int to, Comparator<T> comparator) {
        int rightIndex = to;
        int leftIndex = from;

        T pivot = arr[from + (to - from) / 2];
        while (leftIndex <= rightIndex) {

            while (comparator.compare(arr[leftIndex], pivot) < 0) {
                leftIndex++;
            }

            while (comparator.compare(arr[rightIndex], pivot) > 0) {
                rightIndex--;
            }

            if (leftIndex <= rightIndex) {
                swap(arr, rightIndex, leftIndex);
                leftIndex++;
                rightIndex--;
            }
        }
        return leftIndex;
    }

    /**
     * Method that swap to elements in array
     * @see MyArrayListImpl#partition(Object[], int, int, Comparator)
     * @param array - array to be sorted
     * @param index1 - index of the first element to be swapped
     * @param index2 - index of the second element to be swapped
     */
    private void swap(T[] array, int index1, int index2) {
        T tmp  = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
    }
}

