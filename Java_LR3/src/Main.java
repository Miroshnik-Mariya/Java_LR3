import Classes.*;
import Interfaces.*;

import java.util.ArrayList;
import java.util.*;

public class Main{
    public static void main(String[] args) {
//    BooksSeries booksSeries = new BooksSeries("Чужестранка", new int[] {600,800,200},5);
//    booksSeries.setElement(1,1200);
//    System.out.println("Название серии книг: " + booksSeries.getTitle() + ";\nколичество страниц в книгах: " + Arrays.toString(booksSeries.getArray()) + ";\nрейтинг: " + booksSeries.getRating());
//
////    System.out.println(booksSeries.getIndex(1));
//    System.out.println("");
//
//    Serial serial = new Serial("Красная королева", new int[]{10,12,110}, 4);
//    serial.setElement(1,130);
//    System.out.println("Название сериала: " + serial.getTitle() + ";\nколичество серий в сезоне: " + Arrays.toString(serial.getArray()) + ";\nрейтинг: " + serial.getRating());
////    System.out.println(serial.getForIndex(1));
//    System.out.println(serial.toString());
//    System.out.println(booksSeries.toString());


        System.out.println("Лабораторная работа №3. Выполнила студентка группы 6301-020302D\nМирошник Мария");

        List<Content> contentDatabase = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);


//    int index = 0;
//    double value;
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
            System.out.println("5 - разделить массив по типам элемента;");
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
//
//
//            case "3": //вывести информацию об объектах
//                System.out.println("\nДлина вектора: " + vec.getLength());
//                break;
//
//
//            case "4": //мин значение вектора
//                System.out.println("\nМин. значение вектора: " + vec.min());
//                break;
//
//
//            case "5": //макс значение вектора
//                System.out.println("\nМакс. значение вектора: " + vec.max());
//                break;
//
//
//            case "6": //сортировка вектора по возрастанию
//                System.out.println("\nИсходный вектор: ");
//                vec.print();
//                vec.insertionSort();
//                System.out.println("\nНовый вектор: ");
//                vec.print();
//                break;

                default:
                    System.out.println("Команда не распознана. Повторите ввод: ");
                    scanner.next();

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
