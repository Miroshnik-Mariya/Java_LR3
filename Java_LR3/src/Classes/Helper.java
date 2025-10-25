package Classes;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Interfaces.Content;

public class Helper {
    //запись в байтовый поток
    public static void outputContent (Content o, OutputStream out) throws IOException{
        o.output(out);
    }


    //чтение из байтового потока
    public static Content inputContent(InputStream in) throws IOException {
        DataInputStream doc = new DataInputStream(in);
        String title = doc.readUTF();
        int rating = doc.readInt();
        int length = doc.readInt();
        int[] arr = new int[length];
        for(int i = 0; i<length; i++){
            arr[i] = doc.readInt();
        }
        return new Serial(title, arr, rating);
    }


    //запись в символьный поток
    public static void writeContent (Content o, Writer out) throws IOException{
        o.write(out);
    }


    //чтение из символьного потока
    //  public static Content readContent(Reader in) throws IOException {
//        StreamTokenizer doc = new StreamTokenizer(in);
//        doc.parseNumbers();
//
//        if (doc.nextToken() != StreamTokenizer.TT_WORD) {
//            throw new IOException("Expected title");
//        }
//        String title = doc.sval.replace("_", " "); // восстанавливаем пробелы
//
//        if (doc.nextToken() != StreamTokenizer.TT_NUMBER) {
//            throw new IOException("Expected rating");
//        }
//        int rating = (int) doc.nval;
//
//        if (doc.nextToken() != StreamTokenizer.TT_NUMBER) {
//            throw new IOException("Expected length");
//        }
//        int length = (int) doc.nval;
//
//        int[] arr = new int[length];
//        for (int i = 0; i < length; i++) {
//            if (doc.nextToken() != StreamTokenizer.TT_NUMBER) {
//                throw new IOException("Array element expected at index " + i);
//            }
//            arr[i] = (int) doc.nval;
//        }
//        return new BooksSeries(title, arr, rating);

    //чтение из символьного потока
        public static Content readContent(BufferedReader br) throws IOException {
            String line = br.readLine();
            if (line == null || line.trim().isEmpty()) {
                return null; // Конец файла или пустая строка
            }

            // Разбиваем строку по пробелам
            String[] tokens = line.trim().split("\\s+");

            if (tokens.length < 3) {
                throw new IOException("Invalid line format: " + line);
            }

            // Первый токен - название (может содержать подчеркивания)
            String title = tokens[0].replace("_", " ");

            // Второй токен - рейтинг
            int rating;
            try {
                rating = Integer.parseInt(tokens[1]);
            } catch (NumberFormatException e) {
                throw new IOException("Invalid rating format: " + tokens[1]);
            }

            // Третий токен - длина массива
            int length;
            try {
                length = Integer.parseInt(tokens[2]);
            } catch (NumberFormatException e) {
                throw new IOException("Invalid length format: " + tokens[2]);
            }

            // Проверяем, что достаточно элементов для массива
            if (tokens.length < 3 + length) {
                throw new IOException("Not enough array elements. Expected: " + length +
                        ", found: " + (tokens.length - 3));
            }

            // Читаем элементы массива
            int[] arr = new int[length];
            for (int i = 0; i < length; i++) {
                try {
                    arr[i] = Integer.parseInt(tokens[3 + i]);
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid array element format: " + tokens[3 + i]);
                }
            }

            return new BooksSeries(title, arr, rating);
    }


    //вывод сериализованных объектов
    public static void serializeContent (Content o, OutputStream out) throws IOException{
        try(ObjectOutputStream doc = new ObjectOutputStream(out)) {
            doc.writeObject(o);
            doc.flush();
        }
    }


    //ввод десериализованного объекта
    public static Content deserializeContent(InputStream in) throws ClassNotFoundException, IOException{
        try(ObjectInputStream doc = new ObjectInputStream(in)) {
            return (Content) doc.readObject();
        }
    }


    //вывод объектов
    public static void writeFormatContent (Content o, Writer out) throws IOException{
        PrintWriter pw = new PrintWriter(out);
        pw.printf("%s;%d", o.getTitle(), o.getRating());
        for (int p : o.getArray()) {
            pw.printf(";%d", p);
        }
        pw.println();
        pw.flush();
    };



    public static Content readFormatContent(Scanner in) throws IOException {
        String line = in.nextLine();
        String[] parts = line.split(";");
        if (parts.length < 3) {
            throw new IOException("Недостаточно данных в строке: " + line);
        }

        String title = parts[0];
        int rating = Integer.parseInt(parts[1]);
        int count = Integer.parseInt(parts[2]);

        int[] arr = new int[count];
        for (int i = 0; i < count && (3 + i) < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[3 + i]);
        }
        return new Serial(title, arr, rating);
    }
}
