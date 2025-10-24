package Classes;

import Exception.*;
import Interfaces.Content;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class BooksSeries implements Content {
    private String title;
    private int[] pagesSeries; //количество страниц в каждой книге серии
    private int rating; //рейтинг

    public BooksSeries() {
        title = "Unknown title";
        pagesSeries = new int[1];
        rating = 0;
    }

//    public BooksSeries(String title, int[] pagesSeries, int rating) {
//        setTitle(title);
//        setArray(pagesSeries);
//        setRating(rating);
//    }


    public BooksSeries(String title, int[] pagesSeries, int rating) {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidDataException("Введено пустое название.");
        }
        this.title = title;

        if (pagesSeries == null) {
            throw new InvalidDataException("Неверно указано количество страниц.");
        }
        for (int pages : pagesSeries) {
            if (pages < 0) {
                throw new InvalidDataException("Количество страниц не может быть отрицательным.");
            }
        }
        this.pagesSeries = pagesSeries.clone();

        if (rating < 0 || rating > 5) {
            throw new InvalidDataException("Рейтинг должен быть от 0 до 5.");
        }
        this.rating = rating;
    }

    @Override
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidDataException("Введено пустое название.");
        }
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setArray(int[] pagesSeries) {
        if (pagesSeries == null) {
            throw new InvalidDataException("Неверно указано количество страниц в книгах серии.");
        }
        for (int pages : pagesSeries) {
            if (pages < 0) {
                throw new InvalidDataException("Количество страниц не может быть отрицательным чсислом.");
            }
        }
        this.pagesSeries = pagesSeries.clone();
    }

    @Override
    public int[] getArray() {
        //проверка на массив с длиной 0
        return pagesSeries;
    }

    @Override
    public int getElement(int idx) {
        if (idx < 0 || idx > pagesSeries.length - 1) {
            throw new InvalidDataException("Индекс вышел за границы массива.");
        }
        return pagesSeries[idx];
    }

    @Override
    public void setElement(int idx, int value){
        if (idx < 0 || idx > pagesSeries.length - 1) {
            throw new InvalidDataException("Индекс вышел за границы массива.");
        }
        else{
            pagesSeries[idx]=value;
        }
    }

    @Override
    public void setRating(int rating) {
        if (rating >= 0 && rating < 6) {
            this.rating = rating;
        } else {
            throw new InvalidDataException("Введена неверная оценка. Рейтинг может быть целым числом от 0 до 5.");
        }
    }

    @Override
    public int getRating() {
        return rating;
    }

//
//    @Override
//    public String toString() {
//        double avg = calculateAverage();
//        String avgString = String.format("%.1f", avg);
//
//        return String.format("""
//                        ═══════════════════════════════════
//                                     СЕРИЯ КНИГ
//                        ═══════════════════════════════════
//                          Название: %-20s
//                          Количество книг: %2d
//                          Рейтинг: %1d
//                          Среднее кол-во стр в книге: %5s
//                        ═══════════════════════════════════
//                        """,
//                title.length() > 20 ? title.substring(0, 17) + "..." : title,
//                pagesSeries.length,
//                rating,
//                avgString
//        );
//    }

    @Override
    public double calculateAverage() throws SeriesOperationException {
        if (pagesSeries.length == 0) {
            throw new SeriesOperationException("Серия не содержит книг");
        }
        double res = 0;
        int sum = 0;
        for (int pages : pagesSeries) {
            sum += pages;
        }
        return (double) sum / pagesSeries.length;
    }



    @Override
    public String toString() {
        try {
            double avgPages = calculateAverage();
            return String.format("""
                            ═══════════════════════════════════
                                         СЕРИЯ КНИГ
                            ═══════════════════════════════════
                              Название: %s
                              Книг: %d
                              Рейтинг: %d/5
                              Среднее: %.1f стр.
                            ═══════════════════════════════════
                            """,
                    title.length() > 20 ? title.substring(0, 17) + "..." : title,
                    pagesSeries.length,
                    rating,
                    avgPages
            );
        } catch (SeriesOperationException e) {
            return String.format("""
                            ═══════════════════════════════════
                                         СЕРИЯ КНИГ
                            ═══════════════════════════════════
                              Название: %s
                              Книг: %d
                              Рейтинг: %d/5
                              Среднее: ошибка расчета
                            ═══════════════════════════════════
                            """,
                    title.length() > 20 ? title.substring(0, 17) + "..." : title,
                    pagesSeries.length,
                    rating
            );
        }
    }

    @Override
    public boolean equals(Object obj) {
        // 1. Проверка на ссылочную идентичность
        if (this == obj) return true;

        // 2. Проверка на null и совпадение класса
        if (obj == null || getClass() != obj.getClass()) return false;

        // 3. Приведение типа
        BooksSeries that = (BooksSeries) obj;

        // 4. Сравнение всех значимых полей
        return rating == that.rating &&
                title.equals(that.title) &&
                Arrays.equals(pagesSeries, that.pagesSeries);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + Arrays.hashCode(pagesSeries);
        result = 31 * result + rating;
        return result;
    }



//    public void output(OutputStream out) {
//        try {
//            String data = "Title: " + getTitle() + "\n" +
//                    "Pages Series: " + Arrays.toString(getArray()) + "\n" +
//                    "Rating: " + getRating() + "\n";
//            out.write(data.getBytes(StandardCharsets.UTF_8));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    //записи в байтовый поток
    @Override
    public void output(OutputStream out) throws IOException {
        DataOutputStream text = new DataOutputStream(out);
        text.writeUTF(title);
        text.writeInt(rating);
        //dos.writeInt(bookPages.length);
        for (int v : pagesSeries) text.writeInt(v);
        text.flush();
    }


    //записи в символьный поток
    @Override
    public void write(Writer out) throws IOException{
//        try {
//            out.write("Title: " + getTitle() + "\n");
//            out.write("Pages Series: " + Arrays.toString(getArray()) + "\n");
//            out.write("Rating: " + getRating() + "\n");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        PrintWriter pw = new PrintWriter(out);
        pw.print(title + " " + rating);
        for (int v : pagesSeries) pw.print(" " + v);
        //pw.println();
        pw.flush();
    }
}
