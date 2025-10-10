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
        setPagesSeries(pagesSeries);
        setRating(rating);
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidDataException("Введено пустое название.");
        }
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    public void setPagesSeries(int[] pagesSeries) {
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

    public int[] getPagesSeries() {
        //проверка на массив с длиной 0
        return pagesSeries;
    }

    public int getForIndex(int idx) {
        if (idx < 0 || idx > pagesSeries.length - 1) {
            throw new InvalidDataException("Индекс вышел за границы массива.");
        }
        return pagesSeries[idx];
    }


    public void setRating(int rating) {
        if (rating >= 0 && rating < 6) {
            this.rating = rating;
        } else {
            throw new InvalidDataException("Введена неверная оценка. Рейтинг может быть целым числом от 0 до 5.");
        }
    }

    public int getRating() {
        return rating;
    }


    @Override
    public String toString() {
        return String.format("""
                        ═══════════════════════════════════
                                     СЕРИЯ КНИГ
                        ═══════════════════════════════════
                          Название: %-20s
                          Количество книг: %2d
                          Рейтинг: %1d
                        ═══════════════════════════════════
                        """,
                title.length() > 20 ? title.substring(0, 17) + "..." : title,
                pagesSeries.length,
                rating
        );
    }

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
