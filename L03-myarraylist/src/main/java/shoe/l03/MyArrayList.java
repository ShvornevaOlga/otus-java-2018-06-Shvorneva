package shoe.l03;

import java.util.*;

public class MyArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elementData;
    private int size;

    MyArrayList() {
        this.elementData = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        throw new RuntimeException();
    }

    public Iterator<T> iterator() {
        return listIterator();
    }

    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    public <T1> T1[] toArray(T1[] a) {
        throw new RuntimeException();
    }

    public boolean add(T t) {
        if (size == elementData.length)
             grow();
        this.elementData[size] = t;
        size++;
        return true;
    }

    private void grow() {
        this.elementData = Arrays.copyOf(elementData, newCapacity());
    }

    private int newCapacity() {
        int oldCapacity = elementData.length;
        return oldCapacity + (oldCapacity >> 1);
    }

    public boolean remove(Object o) {
        throw new RuntimeException();
    }

    public boolean containsAll(Collection<?> c) {
        throw new RuntimeException();
    }

    public boolean addAll(Collection<? extends T> c) {

        throw new RuntimeException();
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        throw new RuntimeException();
    }

    public boolean removeAll(Collection<?> c) {
        throw new RuntimeException();
    }

    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException();
    }

    public void clear() {
        throw new RuntimeException();
    }

    public T get(int index) {
        Objects.checkIndex(index, size);
        return elementData(index);
    }

    public T set(int index, T element) {
        Objects.checkIndex(index, size);
        T oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }

    public void add(int index, T element) {
        if (index >= 0 && index < size) {
            T temp = elementData(index);
            elementData[index] = element;
            for (int j = index + 1; j < size; j++) {
                T temp1 = elementData(j);
                elementData[j] = temp;
                temp = temp1;
            }
            size++;
        } else throw new IndexOutOfBoundsException();
    }

    public T remove(int index) {
        if (index >= 0 && index < size) {
            T t = elementData(index);
            System.arraycopy(elementData, index + 1, elementData, index, size-1);
            size--;
            return t;
        } else throw new IndexOutOfBoundsException();
    }

    public int indexOf(Object o) {
        throw new RuntimeException();
    }

    public int lastIndexOf(Object o) {
        throw new RuntimeException();
    }

    public ListIterator<T> listIterator() {
        return new ListIterator<>() {
            private int cursor;
            private int lastRet = -1;

            @Override
            public boolean hasNext() {
                return nextIndex() != size;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    lastRet = cursor;
                    return elementData(cursor++);
                } else
                    throw new NoSuchElementException();
            }

            @Override
            public boolean hasPrevious() {
                return cursor != 0;
            }

            @Override
            public T previous() {
                if (hasPrevious()) {
                    lastRet = --cursor;
                    return elementData(cursor);
                } else
                    throw new NoSuchElementException();
            }

            @Override
            public int nextIndex() {
                return cursor;
            }

            @Override
            public int previousIndex() {
                return cursor - 1;
            }

            @Override
            public void remove() {
                MyArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            }

            @Override
            public void set(T t) {
                if (lastRet >= 0) {
                    MyArrayList.this.set(lastRet, t);
                }
            }

            @Override
            public void add(T t) {
                int i = cursor;
                MyArrayList.this.add(i, t);
                cursor = i + 1;
                lastRet = -1;
            }
        };
    }

    public ListIterator<T> listIterator(int index) {
        throw new RuntimeException();
    }

    public List<T> subList(int fromIndex, int toIndex) {
        throw new RuntimeException();
    }

    private T elementData(int index) {
        return (T) elementData[index];
    }
}
