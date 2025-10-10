package Classes;

import Exception.*;

public class BooksSeries {
    private String title;
    private int[] pagesSeries; //количество страниц в каждой книге серии
    private int rating; //рейтинг

    public BooksSeries(){
        title = "Unknown title";
        pagesSeries = new int[0];
        rating = 0;
    }

    public BooksSeries(String title, int[] pagesSeries, int rating){
        setTitle(title);
        setPagesSeries(pagesSeries);
        setRating(rating);
    }

    public void setTitle(String title){
        if (title == null || title.trim().isEmpty()){
            throw new InvalidDataException("Введено пустое название.");
        }
        this.title = title;
    }

    public String getTitle(){
        return title;
    }


    public void setPagesSeries(int[] pagesSeries){
        if (pagesSeries == null){
            throw new InvalidDataException("Неверно указано количество страниц в книгах серии.");
        }
        for (int pages : pagesSeries){
            if (pages < 0){
                throw new InvalidDataException("Количество страниц не может быть отрицательным.");
            }
        }
        this.pagesSeries = pagesSeries.clone();
    }

    public int[] getPagesSeries(){
        //проверка на массив с длиной 0
        return pagesSeries;
    }


    public void setRating(int rating){
        if (rating>=0 && rating<6){
            this.rating = rating;
        }
        else{
            throw new InvalidDataException("Введена неверная оценка. Рейтинг может быть целым числом от 0 до 5.");
        }
    }

    public int getRating(){
        return rating;
    }

}
