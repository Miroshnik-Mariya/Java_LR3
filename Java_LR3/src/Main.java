import Classes.*;
import Classes.Serial;
import Interfaces.*;
import Exception.*;
import Threads.ThreadRead;
import Threads.ThreadWrite;

import java.io.*;
import java.util.ArrayList;
import java.util.*;

public class Main {
    static List<Content> contentDatabase = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Лабораторная работа №3. Выполнила студентка группы 6301-020302D\nМирошник Мария");

        boolean flag = true;

        while (flag) {
            System.out.println("\n\nВыберите действие: ");
            System.out.println("1. Заполнение базы элементов с консоли");
            System.out.println("2. Байтовый ввод/вывод (Задание 1)");
            System.out.println("3. Текстовый ввод/вывод (Задание 1)");
            System.out.println("4. Сериализация/десериализация (Задание 2)");
            System.out.println("5. Форматный текстовый ввод/вывод (Задание 3)");
            System.out.println("6. Показать все публикации");
            System.out.println("7. Лабораторная работа №5. Классы ThreadWrite и ThreadRead");
            System.out.println("8. Лабораторная работа №5. Runnable");
            System.out.println("9. Лабораторная работа №5. Класс WrapperContent");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

            try {
                switch (choice) {
                    case 0: //выход
                        System.out.println("До новых встреч!");
                        flag = false;
                        break;
                    case 1:
                        fillFromConsole();
                        break;
                    case 2:
                        byteStreamOperations();
                        break;
                    case 3:
                        textStreamOperations();
                        break;
                    case 4:
                        serializationOperations();
                        break;
                    case 5:
                        formattedTextOperations();
                        break;
                    case 6:
                        showAllContent();
                        break;
                    case 7:
                        threadClasses();
                        break;

                    case 8:
                        runnableClasses();
                        break;

                    case 9:
                        wrapperClasses();
                        break;

                    default:
                        System.out.println("Команда не распознана. Повторите ввод: ");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
                e.printStackTrace();
            }
        }
        scanner.close();
    }

    private static void threadClasses(){
        int[] array = new int[100];
        Content content = new BooksSeries("Нити", array, 5);
        ThreadWrite writer = new ThreadWrite(content);
        ThreadRead reader = new ThreadRead(content);

        writer.start();
        reader.start();

        try {
            writer.join();
            reader.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nОбе нити завершили работу");
    }


    private static void runnableClasses(){
        int[] array = new int[100];
        Content content = new Serial("Runnable", array, 5);
        Semaphore writer = new Semaphore(1);
        Semaphore reader = new Semaphore(0);

        ThreadWriteRun writeRun = new ThreadWriteRun(content, writer, reader);
        ThreadReadRun readRun = new ThreadReadRun(content, reader, writer);

        Thread writeTh = new Thread(writeRun);
        Thread readTh = new Thread(readRun);

        writeTh.start();
        readTh.start();

        try {
            writeTh.join();
            readTh.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nОбе нити завершили работу");
    }

    private static void wrapperClasses() throws InterruptedException {
        Content originalContent = new BooksSeries("Test", new int[]{1, 2, 3}, 5);
        Content syncContent = new WrapperContent(originalContent);

        Thread t1 = new Thread(() -> testContent(syncContent, "Thread-1"));
        Thread t2 = new Thread(() -> testContent(syncContent, "Thread-2"));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Тест завершен");

    }


    private static void testContent(Content content, String threadName) {
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(threadName + " getTitle: " + content.getTitle());
                content.setTitle("Title " + i);
                System.out.println(threadName + " getRating: " + content.getRating());
                content.setRating(i);
                int[] arr = content.getArray();
                content.setArray(new int[]{i, i + 1, i + 2});
                System.out.println(threadName + " getElement("+i+"): " + content.getElement(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void showAllContent() {
        System.out.println("\nПОЛНАЯ ИНФОРМАЦИЯ О КОНТЕНТЕ");
        System.out.println("═".repeat(60));

        if (contentDatabase.isEmpty()) {
            System.out.println("База данных пуста!");
            return;
        }

        for (int i = 0; i < contentDatabase.size(); i++) {
            System.out.println("Запись #" + (i + 1));
            System.out.println(contentDatabase.get(i));
        }
    }

    // Группировка по результату бизнес-метода
    private static void groupByBusinessResult() {
        System.out.println("\nГРУППИРОВКА ПО РЕЗУЛЬТАТУ БИЗНЕС-МЕТОДА");
        if (contentDatabase.isEmpty()) {
            System.out.println("База данных пуста!");
            return;
        }
        Map<Double, List<Content>> resultGroups = new HashMap<>();

        // Собираем объекты с одинаковыми результатами calculateAverage()
        for (Content content : contentDatabase) {
            try {
                double result = content.calculateAverage();
                resultGroups.computeIfAbsent(result, k -> new ArrayList<>()).add(content);
            } catch (SeriesOperationException e) {
                System.out.println("Пропуск '" + content.getTitle() + "': " + e.getMessage());
            }
        }
        if (resultGroups.isEmpty()) {
            System.out.println("Нет данных для группировки");
            return;
        }
        // Выводим группы
        int groupNumber = 1;
        for (Map.Entry<Double, List<Content>> entry : resultGroups.entrySet()) {
            if (entry.getValue().size() > 1) {
                System.out.println("\nГруппа " + groupNumber++ + " (результат: " + entry.getKey() + "):");
                for (Content content : entry.getValue()) {
                    System.out.println("   - " + content.getTitle() +
                            " (" + getContentType(content) + ")");
                }
            }
        }
        if (groupNumber == 1) {
            System.out.println("ℹНет объектов с одинаковыми результатами бизнес-метода");
        }
    }

    private static String getContentType(Content content) {
        if (content instanceof BooksSeries) return "Книжная серия";
        else if (content instanceof Serial) return "Сериал";
        else return "Неизвестный тип";
    }

    private static void splitByType() {
        System.out.println("\nРАЗДЕЛЕНИЕ ПО ТИПАМ ОБЪЕКТОВ");
        System.out.println("═".repeat(60));

        if (contentDatabase.isEmpty()) {
            System.out.println("База данных пуста!");
            return;
        }

        // Создаем два массива для разных типов
        List<BooksSeries> bookSeriesList = new ArrayList<>();
        List<Serial> serialList = new ArrayList<>();

        // Разделяем объекты по реальному типу
        for (Content content : contentDatabase) {
            if (content instanceof BooksSeries) {
                bookSeriesList.add((BooksSeries) content);
            } else if (content instanceof Serial) {
                serialList.add((Serial) content);
            }
        }

        // Выводим результаты
        System.out.println("КНИЖНЫЕ СЕРИИ (" + bookSeriesList.size() + "):");
        if (bookSeriesList.isEmpty()) {
            System.out.println("Нет книжных серий");
        } else {
            for (BooksSeries bookSeries : bookSeriesList) {
                System.out.println("   - " + bookSeries.getTitle() +
                        " | Книг: " + bookSeries.getArray().length +
                        " | Рейтинг: " + bookSeries.getRating() + "/5");
            }
        }

        System.out.println("\nСЕРИАЛЫ (" + serialList.size() + "):");
        if (serialList.isEmpty()) {
            System.out.println("Нет сериалов");
        } else {
            for (Serial serial : serialList) {
                System.out.println("   - " + serial.getTitle() +
                        " | Сезонов: " + serial.getArray().length +
                        " | Рейтинг: " + serial.getRating() + "/5");
            }
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число!");
            }
        }
    }

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    // 1. Заполнение базы элементов с консоли
    private static void fillFromConsole() throws IOException {
        System.out.println("\nЗАПОЛНЕНИЕ БАЗЫ С КОНСОЛИ");

        while (true) {
            System.out.println("\nВыберите тип элемента:");
            System.out.println("1. BooksSeries (Серия книг)");
            System.out.println("2. Serial (Сериал)");
            System.out.println("0. Завершить ввод");
            System.out.print("Ваш выбор: ");

            int typeChoice = scanner.nextInt();
            scanner.nextLine();

            if (typeChoice == 0) break;
            if (typeChoice != 1 && typeChoice != 2) {
                System.out.println("Неверный выбор типа!");
                continue;
            }

            System.out.print("Введите название: ");
            String title = scanner.nextLine();

            System.out.print("Введите рейтинг (0-5): ");
            int rating;

            try {
                rating = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число для рейтинга!");
                continue;
            }

            System.out.print("Введите количество элементов (книг/сезонов): ");
            int count = scanner.nextInt();

            int[] elements = new int[count];
            for (int i = 0; i < count; i++) {
                System.out.print("Введите значение элемента " + (i + 1) + ": ");
                elements[i] = scanner.nextInt();
            }
            scanner.nextLine(); // очистка буфера
            Content content;
            if (typeChoice == 1) {
                content = new BooksSeries(title, elements, rating);
            } else {
                content = new Serial(title, elements, rating);
            }
            contentDatabase.add(content);
            System.out.println("Публикация добавлена: " + content.getTitle());
        }

        System.out.println("База заполнена. Всего элементов: " + contentDatabase.size());
    }

    // 2. Байтовый ввод/вывод (Задание 1)
    private static void byteStreamOperations() throws IOException {
        System.out.println("\nБАЙТОВЫЙ ВВОД/ВЫВОД");

        if (contentDatabase.isEmpty()) {
            System.out.println("В базе нет записей.");
            return;
        }
////        // Цикл записи в байтовый поток
////        FileOutputStream fos = new FileOutputStream("pubs_bytes.dat");
////        DataOutputStream dos = new DataOutputStream(fos);
////
////        dos.writeInt(contentDatabase.size());
////        for (Content p : contentDatabase) {
////            Helper.outputContent(p, dos);
////        }
////        dos.close();
////        System.out.println("Записано объектов: " + contentDatabase.size());
////
////        // Цикл чтения из байтового потока
////        FileInputStream fis = new FileInputStream("pubs_bytes.dat");
////        DataInputStream dis = new DataInputStream(fis);
////
////        int count = dis.readInt();
////        List<Content> loadedByte = new ArrayList<>();
////        for (int i = 0; i < count; i++) {
////            loadedByte.add(Helper.inputContent(dis));
////        }
////        dis.close();
////        for (Content p : loadedByte) {
////            System.out.println("  " + p);
////        }
//
//        // Цикл записи в байтовый поток
//        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("pubs_bytes.dat"))) {
//            dos.writeInt(contentDatabase.size());
//            for (Content p : contentDatabase) {
//                Helper.outputContent(p, dos);
//            }
//        }
//        // Цикл чтения из байтового потока
//        try (DataInputStream dis = new DataInputStream(new FileInputStream("pubs_bytes.dat"))) {
//            int count = dis.readInt();
//            List<Content> loadedByte = new ArrayList<>();
//            for (int i = 0; i < count; i++) {
//                loadedByte.add(Helper.inputContent(dis));
//            }
//            for (Content p : loadedByte) {
//                System.out.println("  " + p);
//            }
        DataOutputStream dos = new DataOutputStream(new FileOutputStream("pubs_bytes.dat"));
        dos.writeInt(contentDatabase.size());
        for (Content p : contentDatabase) {
            Helper.outputContent(p, dos);
        }
        dos.close();

        // Чтение из байтового потока
        DataInputStream dis = new DataInputStream(new FileInputStream("pubs_bytes.dat"));
        int count = dis.readInt();
        List<Content> loadedByte = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            loadedByte.add(Helper.inputContent(dis));
        }
        dis.close();

        for (Content p : loadedByte) {
            System.out.println("  " + p);
        }

    }

    // 3. Текстовый ввод/вывод (Задание 1)
    private static void textStreamOperations() throws IOException {
        System.out.println("\nТЕКСТОВЫЙ ВВОД/ВЫВОД");
        if (contentDatabase.isEmpty()) {
            System.out.println("В базе нет записей.");
            return;
        }
        // Запись в текстовый файл
        try (FileWriter fw = new FileWriter("pubs_text.txt")) {
            for (Content p : contentDatabase) {
                Helper.writeContent(p, fw);
            }
        }
        System.out.println("Записано объектов: " + contentDatabase.size());

        // Чтение из файла
        FileReader reader = new FileReader("pubs_text.txt");
        StringBuilder sb = new StringBuilder();
        int ch;
        while ((ch = reader.read()) != -1) {
            sb.append((char) ch);
        }
        String contentStr = sb.toString();
        reader.close();
        System.out.println("Прочитано из файла:");
        System.out.println(contentStr);
    }

    // 4. Сериализация/десериализация (Задание 2)
    private static void serializationOperations() throws IOException, ClassNotFoundException {
        System.out.println("\nСЕРИАЛИЗАЦИЯ/ДЕСЕРИАЛИЗАЦИЯ");
        if (contentDatabase.isEmpty()) {
            System.out.println("В базе нет записей.");
            return;
        }
        FileOutputStream serOut = new FileOutputStream("pubs.ser");
        ObjectOutputStream oos = new ObjectOutputStream(serOut);
        oos.writeInt(contentDatabase.size());
        for (Content content : contentDatabase) {
            oos.writeObject(content);
        }
        oos.close();
        System.out.println("Сериализация завершена. Записано объектов: " + contentDatabase.size());

        // ДЕСЕРИАЛИЗАЦИЯ
        FileInputStream serIn = new FileInputStream("pubs.ser");
        ObjectInputStream ois = new ObjectInputStream(serIn);
        int objectCount = ois.readInt();
        List<Content> fromSer = new ArrayList<>();
        for (int i = 0; i < objectCount; i++) {
            Content content = (Content) ois.readObject();
            fromSer.add(content);
        }
        ois.close();
        System.out.println("Десериализация завершена. Прочитано объектов: " + fromSer.size());
        for (Content p : fromSer) {
            System.out.println("  " + p);
        }
    }

    // 5. Форматный текстовый ввод/вывод (Задание 3)
    private static void formattedTextOperations() throws IOException {
        System.out.println("\nФОРМАТНЫЙ ТЕКСТОВЫЙ ВВОД/ВЫВОД");
        if (contentDatabase.isEmpty()) {
            System.out.println("В базе нет записей.");
            return;
        }
        // Настройка локали для корректного преобразования вещественных чисел
        Locale.setDefault(Locale.US);
        FileWriter fw = new FileWriter("pubs_formatted.txt");
        for (Content p : contentDatabase) {
            Helper.writeFormatContent(p, fw);
        }
        fw.close();
        // Цикл чтения из текстового потока
        FileReader fr = new FileReader("pubs_formatted.txt");
        Scanner fileScanner = new Scanner(fr);
        List<Content> loadedFormatted = new ArrayList<>();
        while (fileScanner.hasNextLine()) {
            Content p = Helper.readFormatContent(fileScanner);
            if (p != null) {
                loadedFormatted.add(p);
            }
        }
        fileScanner.close();
        fr.close();
        for (Content p : loadedFormatted) {
            System.out.println("  " + p);
        }
    }
}