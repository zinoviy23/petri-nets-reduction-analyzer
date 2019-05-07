package hse.se.aaizmaylov.petrinetslibrary.utils.math;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Arrays;

@SuppressWarnings({"unused", "WeakerAccess"})
public class IntVector {
    private final long[] elements;
    private final int size;

    public IntVector(int size) {
        checkSize(size);

        elements = new long[size];
        this.size = size;
    }

    public IntVector(@NonNull long[] elements) {
        this(elements.length);

        System.arraycopy(elements, 0, this.elements, 0, elements.length);
    }

    public void set(int index, long value) {
        checkBounds(index);

        elements[index] = value;
    }

    public long get(int index) {
        checkBounds(index);

        return elements[index];
    }

    @Range(from = 1, to = Integer.MAX_VALUE)
    public int size() {
        return size;
    }

    @NotNull
    public IntVector sum(@NonNull IntVector other) {
        checkSizeOfRhs(other);

        IntVector result = new IntVector(size());

        for (int i = 0; i < size; i++) {
            result.set(i, elements[i] + other.elements[i]);
        }

        return result;
    }

    @NotNull
    public IntVector subtract(@NonNull IntVector other) {
        checkSizeOfRhs(other);

        IntVector result = new IntVector(size());

        for (int i = 0; i < size; i++) {
            result.set(i, elements[i] - other.elements[i]);
        }

        return result;
    }

    @NotNull
    public IntVector multiply(long c) {
        IntVector result = new IntVector(size());

        for (int i = 0; i < size; i++) {
            result.set(i, elements[i] * c);
        }

        return result;
    }

    public long dot(@NonNull IntVector other) {
        checkSizeOfRhs(other);

        long res = 0;

        for (int i = 0, size = size(); i < size; i++) {
            res += elements[i] * other.elements[i];
        }

        return res;
    }

    @NotNull
    public IntVector normalized() {
        IntVector result = new IntVector(size);

        long gcd = gcd();

        for (int i = 0; i < size; i++) {
            result.set(i, elements[i] / gcd);
        }

        return result;
    }

    @NotNull
    public IntVector inverted() {
        IntVector result = new IntVector(size);

        for (int i = 0; i < size; i++) {
            result.set(i, -elements[i]);
        }

        return result;
    }


    private long gcd() {
        long gcd = 1;

        for (long element : elements) {
            gcd = gcd(gcd, element);
        }

        return gcd;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long r = a % b;
            a = b;
            b = r;
        }

        return a;
    }

    private static void checkSize(int size) {
        if (size <= 0)
            throw new IllegalArgumentException("Size cannot be less or equal to zero!");
    }

    private void checkSizeOfRhs(@NonNull IntVector other) {
        if (other.size() != size())
            throw new IllegalArgumentException("Sizes must be equal! But they are " + size() + " and " + other.size());
    }

    private void checkBounds(int index) {
        if (index < 0 || index >= elements.length)
            throw new IndexOutOfBoundsException("IntVector do not support indices like " + index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntVector)) return false;
        IntVector intVector = (IntVector) o;
        return Arrays.equals(elements, intVector.elements);
    }

    @Override
    public int hashCode() {
        return size;
    }
}
