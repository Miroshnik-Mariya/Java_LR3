package Threads;

import Interfaces.Content;
import Exception.InvalidDataException;

public class ThreadWrite extends Thread {
    private Content content;

    public ThreadWrite(Content content) {
        super();
        this.content = content;
    }

    @Override
    public void run() {
        try {
            int[] array = content.getArray();
            System.out.println("Начало записи. Длина массива: " + array.length);

            for (int i = 0; i < array.length; i++) {
                int value = 1 + (int) (Math.random() * 1000);
                content.setElement(i, value);
                System.out.println(" Write: " + value + " to position " + i);
                Thread.sleep(10);
            }
            System.out.println("Запись окончена.");
        } catch (InterruptedException e) {
            System.out.println(getName() + " was interrupted during writing");
            Thread.currentThread().interrupt();
        } catch (InvalidDataException e) {
            System.out.println(getName() + " data error: " + e.getMessage());
        }
    }
}