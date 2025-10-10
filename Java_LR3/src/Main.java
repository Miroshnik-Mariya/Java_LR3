import Classes.*;
import Interfaces.*;
import Exception.*;
import java.util.ArrayList;
import java.util.*;

public class Main{
    static List<Content> contentDatabase = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Лабораторная работа №3. Выполнила студентка группы 6301-020302D\nМирошник Мария");
        Scanner scanner = new Scanner(System.in);

        int l = 0;
        String title = "";
        int rating = 0;
        Content content;

        boolean flag = true;

        while (flag) {
            System.out.println("\n\nВыберите действие: ");
            System.out.println("1 - создать элемент типа BookSeries;");
            System.out.println("2 - создать элемент типа Serial;");
            System.out.println("3 - вывести информацию об объектах;");
            System.out.println("4 - разделить массив по результатам работы бизнес-метода;");
            System.out.println("5 - разделить массив по типам элементов;");
            System.out.println("0 - завершение программы;");

            System.out.print("\nВведите номер действия: ");
            String menu = scanner.next();

            switch (menu) {
                case "0": //выход
                    System.out.println("До новых встреч!");
                    flag = false;
                    break;


                case "1": //создать элемент типа BookSeries
                    title = getStringInput("\nВведите название: ");
                    rating = getIntInput("Введите рейтинг (0-5): ");
                    l = getIntInput("Введите количество элементов: ");
                    int[] array = new int[l];
                    for (int i = 0; i < l; i++) {
                        array[i] = getIntInput("Элемент " + (i + 1) + ": ");
                    }

                    content = new BooksSeries(title, array, rating);
                    System.out.println("Книжная серия добавлена!");
                    contentDatabase.add(content);
                    break;


                case "2": //создать элемент типа Serial
                    title = getStringInput("\nВведите название: ");
                    rating = getIntInput("Введите рейтинг (0-5): ");
                    l = getIntInput("Введите количество элементов: ");
                    int[] arr = new int[l];
                    for (int i = 0; i < l; i++) {
                        arr[i] = getIntInput("Элемент " + (i + 1) + ": ");
                    }

                    content = new Serial(title, arr, rating);
                    System.out.println("Сериал добавлен!");
                    contentDatabase.add(content);
                    break;


                case "3": //вывести информацию об объектах
                    showAllContent();
                    break;


                case "4": //разделить массив по результатам работы бизнес-метода
                    groupByBusinessResult();
                    break;


                case "5": //разделить массив по типам элементов
                    splitByType();
                    break;

                default:
                    System.out.println("Команда не распознана. Повторите ввод: ");
                    scanner.next();

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
        System.out.println("═".repeat(60));

        if (contentDatabase.isEmpty()) {
            System.out.println("База данных пуста!");
            return;
        }
        // Map для группировки: результат -> список объектов
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
            if (entry.getValue().size() > 1) { // Показываем только группы с >1 объектом
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

    private static int getIntInput (String prompt){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число!");
            }
        }
    }

    private static String getStringInput (String prompt){
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        return scanner.nextLine();
    }


}
