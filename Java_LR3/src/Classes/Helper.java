package Classes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import Interfaces.Content;

public class Helper {
    //запись в байтовый поток
    public static void outputContent (Content o, OutputStream out) throws IOException{
        o.output(out);
    };

    //чтение из байтового потока
    /*
    public static Content inputContent(InputStream in) throws IOException{
        write(in);
    };*/


    //записи в символьный поток
    public static void writeContent (Content o, Writer out) throws IOException{
        o.write(out);
    };
    //-- чтения из символьного потока
    //<Интерфейс> read<Интерфейс>(Reader in);

}
