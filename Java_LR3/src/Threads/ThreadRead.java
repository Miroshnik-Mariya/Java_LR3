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
            //int[] array = content.getArray();
            System.out.println("Начало чтения");

            for (int i = 0; i < content.length(); i++) {
                int value = content.getElement(i);
                System.out.println(" Read: " + value + " from position " + i);
                //Thread.sleep(10); // Небольшая задержка для наглядности
            }

            System.out.println("Чтение завершено. Прочитано элементов: " + content.length());

        } catch (InvalidDataException e) {
            System.out.println(getName() + " ошибка данных: " + e.getMessage());
        }
    }
}