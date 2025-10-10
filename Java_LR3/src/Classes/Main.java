import Classes.*;
import Exception.*;
import Interfaces.*;

void main() {
    BooksSeries booksSeries = new BooksSeries("Чужестранка", new int[] {600,800,200},5);
    System.out.println("Название серии книг: " + booksSeries.getTitle() + ";\nколичество страниц в книгах: " + Arrays.toString(booksSeries.getPagesSeries()) + ";\nрейтинг: " + booksSeries.getRating());
}
