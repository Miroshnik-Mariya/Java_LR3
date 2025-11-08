package Classes;

import Interfaces.Content;
import Exception.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public class WrapperContent implements Content {
    private final Content wrappedContent;

    public WrapperContent(Content content) {
        this.wrappedContent = content;
    }

    @Override
    public synchronized String getTitle() {
        return wrappedContent.getTitle();
    }

    @Override
    public synchronized void setTitle(String title) {
        wrappedContent.setTitle(title);
    }

    @Override
    public synchronized int getRating() {
        return wrappedContent.getRating();
    }

    @Override
    public synchronized void setRating(int rating) {
        wrappedContent.setRating(rating);
    }

    @Override
    public synchronized int[] getArray() {
        int[] original = wrappedContent.getArray();
        return original != null ? original.clone() : new int[0];
    }

    @Override
    public synchronized void setArray(int[] array) {
        int[] copy = array != null ? array.clone() : new int[0];
        wrappedContent.setArray(copy);
    }

    @Override
    public synchronized int getElement(int index) {
        return wrappedContent.getElement(index);
    }

    @Override
    public synchronized void setElement(int index, int value) {
        wrappedContent.setElement(index, value);
    }

    @Override
    public synchronized void output(OutputStream out) throws IOException {
        wrappedContent.output(out);
    }

    @Override
    public synchronized void write(Writer out) throws IOException {
        wrappedContent.write(out);
    }

    @Override
    public synchronized double calculateAverage() throws SeriesOperationException {
        return wrappedContent.calculateAverage();
    }
    
    @Override
    public synchronized int hashCode() {
        return wrappedContent.hashCode();
    }

    public synchronized Content getWrappedContent() {
        return wrappedContent;
    }

    @Override
    public int length(){
        return wrappedContent.length();
    }
}