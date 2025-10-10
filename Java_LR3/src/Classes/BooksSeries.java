package Classes;

import Exception.*;
import Interfaces.Content;

public class BooksSeries implements Content {
    private String title;
    private int[] pagesSeries; //количество страниц в каждой книге серии
    private int rating; //рейтинг

    public BooksSeries() {
        title = "Unknown title";
        pagesSeries = new int[0];
        rating = 0;
    }

    public BooksSeries(String title, int[] pagesSeries, int rating) {
        setTitle(title);
        setArray(pagesSeries);
        setRating(rating);
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


    @Override
    public String toString() {
        double avg = calculateAverage();
        String avgString = String.format("%.1f", avg);

        return String.format("""
                        ═══════════════════════════════════
                                     СЕРИЯ КНИГ
                        ═══════════════════════════════════
                          Название: %-20s
                          Количество книг: %2d
                          Рейтинг: %1d
                          Среднее кол-во стр в книге: %5s
                        ═══════════════════════════════════
                        """,
                title.length() > 20 ? title.substring(0, 17) + "..." : title,
                pagesSeries.length,
                rating,
                avgString
        );
    }

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
}
