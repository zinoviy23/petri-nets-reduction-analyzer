package hse.se.aaizmaylov.petrinetslibrary.utils.math;

import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Range;

import java.util.Objects;

@SuppressWarnings({"unused", "WeakerAccess"})
public class IntMatrix {
    private final long[][] elements;

    private final int rows;

    private final int columns;

    public IntMatrix(int rows, int columns) {
        this.columns = checkSize(columns);
        this.rows = checkSize(rows);

        elements = new long[rows][];

        for (int i = 0; i < rows; i++)
            elements[i] = new long[columns];
    }

    public IntMatrix(@NonNull long[][] elements) {
        this(checkSize(elements.length), Objects.requireNonNull(elements[0]).length);

        for (int i = 0; i < rows; i++) {
            if (Objects.requireNonNull(elements[i]).length != columns)
                throw new IllegalArgumentException("Is not rectangle matrix! Size must be equals!" +
                        " Expected columns =" + columns + ", but given = " + elements[i].length);

            System.arraycopy(elements[i], 0, this.elements[i], 0, columns);
        }
    }

    public void setColumn(int column, @NonNull IntVector vector) {
        checkColumnIndex(column);

        if (rows != vector.size()) {
            throw new IllegalArgumentException("Vector's size must be equal to rows number!" +
                    " But it " + vector.size());
        }

        for (int i = 0; i < rows; i++) {
            this.elements[i][column] = vector.get(i);
        }
    }

    public void setRows(int row, @NonNull IntVector vector) {
        checkRowIndex(row);

        if (columns != vector.size()) {
            throw new IllegalArgumentException("Vector's size must be equal to columns number!" +
                    " But it " + vector.size());
        }

        for (int i = 0; i < columns; i++) {
            this.elements[row][i] = vector.get(i);
        }
    }

    public IntVector getColumn(int column) {
        checkColumnIndex(column);

        IntVector res = new IntVector(rows);

        for (int i = 0; i < rows; i++)
            res.set(i, elements[i][column]);

        return res;
    }

    public long get(int row, int column) {
        checkRowIndex(row);
        checkColumnIndex(column);

        return elements[row][column];
    }

    public void set(int row, int column, long value) {
        checkColumnIndex(column);
        checkRowIndex(row);

        elements[row][column] = value;
    }

    public IntVector getRow(int row) {
        checkRowIndex(row);

        return new IntVector(elements[row]);
    }

    public IntVector multyply(@NonNull IntVector vector) {
        if (vector.size() != columns)
            throw new IllegalArgumentException("Vector's size must be equal to columns number!" +
                    " But it " + vector.size());

        IntVector res = new IntVector(rows);

        for (int i = 0; i < rows; i++) {
            res.set(i, dotWithRow(vector, i));
        }

        return res;
    }

    public long dotWithRow(@NonNull IntVector vector, int row) {
        checkRowIndex(row);

        long elRes = 0;
        for (int j = 0; j < columns; j++) {
            elRes += vector.get(j) * elements[row][j];
        }
        return elRes;
    }

    public IntVector multiplyLeft(@NonNull IntVector vector) {
        if (vector.size() != rows) {
            throw new IllegalArgumentException("Vector's size must be equal to rows number!" +
                    " But it " + vector.size());
        }

        IntVector res = new IntVector(columns);

        for (int i = 0; i < columns; i++) {
            res.set(i, dotWithColumn(vector, i));
        }

        return res;
    }

    public long dotWithColumn(@NonNull IntVector vector, int column) {
        checkColumnIndex(column);

        long elRes = 0;
        for (int j = 0; j < rows; j++) {
            elRes += elements[j][column] * vector.get(j);
        }
        return elRes;
    }

    @Range(from = 1, to = Integer.MAX_VALUE)
    public int getRows() {
        return rows;
    }

    @Range(from = 1, to = Integer.MAX_VALUE)
    public int getColumns() {
        return columns;
    }

    @Contract(value = "_ -> param1", pure = true)
    private static int checkSize(int size) {
        if (size <= 0)
            throw new IllegalArgumentException("Size cannot be less or equal to zero!");

        return size;
    }

    private void checkColumnIndex(int column) {
        if (column < 0 || column >= columns)
            throw new IndexOutOfBoundsException("Invalid column number " + column);
    }

    private void checkRowIndex(int row) {
        if (row < 0 || row >= rows)
            throw new IndexOutOfBoundsException("Invalid row number " + row);
    }
}
