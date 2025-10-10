package Interfaces;

import Exception.SeriesOperationException;

public interface Content {
    String getTitle();
    void setTitle(String title);

    int getRating();
    void setRating(int rating);

    int[] getArray();
    void setArray(int[] array);

    int getElement(int index);
    void setElement(int index, int value);

    double calculateAverage() throws SeriesOperationException;
}