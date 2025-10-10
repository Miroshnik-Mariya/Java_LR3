package Classes;

import Exception.*;
import Interfaces.Content;

public class Serial implements Content {
    private String title;
    private int[] episodesSeason; //количество серий в каждом сезоне
    private int rating; //рейтинг


    public Serial() {
        title = "Unknown title";
        episodesSeason = new int[0];
        rating = 0;
    }

    public Serial(String title, int[] episodesSeason, int rating) {
        setTitle(title);
        setArray(episodesSeason);
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
    public void setArray(int[] episodesSeason) {
        if (episodesSeason == null) {
            throw new InvalidDataException("Неверно указано количество серий в каждом сезоне.");
        }
        for (int series : episodesSeason) {
            if (series < 0) {
                throw new InvalidDataException("Количество серий не может быть отрицательным числом.");
            }
        }
        this.episodesSeason = episodesSeason.clone();
    }

    @Override
    public int[] getArray() {
        //проверка на массив с длиной 0
        return episodesSeason;
    }

    @Override
    public int getElement(int idx) {
        if (idx < 0 || idx > episodesSeason.length - 1) {
            throw new InvalidDataException("Индекс вышел за границы массива.");
        }
        return episodesSeason[idx];
    }

    @Override
    public void setElement(int idx, int value){
        if (idx < 0 || idx > episodesSeason.length - 1) {
            throw new InvalidDataException("Индекс вышел за границы массива.");
        }
        else{
            episodesSeason[idx]=value;
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
                                       СЕРИАЛ
                        ═══════════════════════════════════
                          Название: %-20s
                          Количество сезонов: %2d
                          Рейтинг: %1d
                          Среднее кол-во серий в сезоне: %3s
                        ═══════════════════════════════════
                        """,
                title.length() > 20 ? title.substring(0, 17) + "..." : title,
                episodesSeason.length,
                rating,
                avgString
        );
    }

    @Override
    public double calculateAverage() throws SeriesOperationException {
        if (episodesSeason.length == 0) {
            throw new SeriesOperationException("Серия не содержит книг");
        }
        double res = 0;
        int sum = 0;
        for (int pages : episodesSeason) {
            sum += pages;
        }
        return sum/episodesSeason.length;
    }
}
