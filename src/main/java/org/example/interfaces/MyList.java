package org.example.interfaces;

import org.example.exceptions.EmptyListException;

import java.util.Comparator;

public interface MyList<T>{
    void add(T t);

    void addByIndex(int index, T t) throws IndexOutOfBoundsException;

    T getElement(int index) throws EmptyListException, IndexOutOfBoundsException;

    void deleteElement(int index) throws EmptyListException, IndexOutOfBoundsException;

    void clear();

    void replaceElement(int index, T t) throws EmptyListException, IndexOutOfBoundsException;

    int getSize();

    MyList<T> sort(Comparator<T> comparator);

    void printList();
}

