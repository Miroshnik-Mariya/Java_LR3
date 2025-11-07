package Threads;

import Interfaces.Content;
import Exception.InvalidDataException;

public class ThreadRead extends Thread {
    private Content content;

    public ThreadRead(Content content) {
        super();
        this.content = content;
    }

    @Override
    public void run() {
        try {
            int[] array = content.getArray();
            System.out.println("Начало чтения. Длина массива: " + array.length);

            for (int i = 0; i < array.length; i++) {
                // Читаем значение из массива
                int value = content.getElement(i);

                System.out.println(" Read: " + value + " from position " + i);

                // Небольшая задержка для наглядности
                Thread.sleep(5);
            }

            System.out.println("Чтение завершено. Прочитано элементов: " + array.length);
        } catch (InterruptedException e) {
            System.out.println(getName() + " was interrupted during reading");
            Thread.currentThread().interrupt();
        } catch (InvalidDataException e) {
            System.out.println(getName() + " data error: " + e.getMessage());
        }
    }
}