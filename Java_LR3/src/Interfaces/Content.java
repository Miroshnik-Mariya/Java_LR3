package Interfaces;

import Exception.SeriesOperationException; 
import java.io.OutputStream;
import java.io.Writer;

public interface Content {
    String getTitle();
    void setTitle(String title);

    int getRating();
    void setRating(int rating);

    int[] getArray();
    void setArray(int[] array);

    int getElement(int index);
    void setElement(int index, int value);

    //записи в байтовый поток
    void output(OutputStream out);

    //записи в символьный поток
    void write(Writer out);


    double calculateAverage() throws SeriesOperationException;
}