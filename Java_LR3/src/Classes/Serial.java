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
        setEpisodesSeason(episodesSeason);
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


    public void setEpisodesSeason(int[] episodesSeason) {
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

    public int[] getEpisodesSeason() {
        //проверка на массив с длиной 0
        return episodesSeason;
    }

    public int getForIndex(int idx) {
        if (idx < 0 || idx > episodesSeason.length - 1) {
            throw new InvalidDataException("Индекс вышел за границы массива.");
        }
        return episodesSeason[idx];
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

    public static void Show() {
        System.out.println();
    }

    @Override
    public String toString() {
        return String.format("""
                        ═══════════════════════════════════
                                       СЕРИАЛ
                        ═══════════════════════════════════
                          Название: %-20s
                          Количество сезонов: %2d
                          Рейтинг: %1d
                        ═══════════════════════════════════
                        """,
                title.length() > 20 ? title.substring(0, 17) + "..." : title,
                episodesSeason.length,
                rating
        );
    }


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
