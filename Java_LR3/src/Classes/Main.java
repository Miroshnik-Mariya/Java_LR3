import Classes.*;
import Exception.*;
import Interfaces.*;

void main() {
    BooksSeries booksSeries = new BooksSeries("Чужестранка", new int[] {600,800,200},5);
    booksSeries.setElement(1,1200);
    System.out.println("Название серии книг: " + booksSeries.getTitle() + ";\nколичество страниц в книгах: " + Arrays.toString(booksSeries.getArray()) + ";\nрейтинг: " + booksSeries.getRating());

//    System.out.println(booksSeries.getIndex(1));
    System.out.println("");

    Serial serial = new Serial("Красная королева", new int[]{10,12,110}, 4);
    serial.setElement(1,130);
    System.out.println("Название сериала: " + serial.getTitle() + ";\nколичество серий в сезоне: " + Arrays.toString(serial.getArray()) + ";\nрейтинг: " + serial.getRating());
//    System.out.println(serial.getForIndex(1));
    System.out.println(serial.toString());
    System.out.println(booksSeries.toString());
}
