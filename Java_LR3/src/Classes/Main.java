import Classes.*;
import Exception.*;
import Interfaces.*;

void main() {
    BooksSeries booksSeries = new BooksSeries("Чужестранка", new int[] {600,800,200},5);
    System.out.println("Название серии книг: " + booksSeries.getTitle() + ";\nколичество страниц в книгах: " + Arrays.toString(booksSeries.getPagesSeries()) + ";\nрейтинг: " + booksSeries.getRating());

//    System.out.println(booksSeries.getIndex(1));
    System.out.println("");

    Serial serial = new Serial("Красная королева", new int[]{10,12,11}, 4);
    System.out.println("Название сериала: " + serial.getTitle() + ";\nколичество серий в сезоне: " + Arrays.toString(serial.getEpisodesSeason()) + ";\nрейтинг: " + serial.getRating());
//    System.out.println(serial.getForIndex(1));
    System.out.println(serial.toString());
    System.out.println(booksSeries.toString());
}
