import com.sun.jdi.connect.TransportTimeoutException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;

import java.net.SocketTimeoutException;
import java.net.URL;

import java.nio.file.Files;

import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

public class Main {


    private static final String PATH_FOLDER = "C:\\Users\\Павел\\Desktop\\Новая папка";
    private static String PATH_DOWNLOAD;


    public static void main(String[] args) throws IOException, SocketTimeoutException {

        System.out.println("Введите путь куда скачать изображения: ");
        PATH_DOWNLOAD = new Scanner(System.in).nextLine();


        String url = "https://lenta.ru/";

        List<String> images = new ArrayList<>();
        Document document = Jsoup.connect(url).get();
        Elements elements = document.select("img");


        for (Element e : elements) {
            String imageURL = e.attr("[abs:src]");

            if (imageURL.startsWith("https:")) {

                images.add(imageURL);
            }
        }
        downloadImages(images);
    }

    private static void downloadImages(List<String> images) throws IOException, FileNotFoundException {

        for (String path : images) {
            try {
                InputStream inputStream = new URL(path).openStream();
                Files.createDirectories(Paths.get(PATH_DOWNLOAD));
                Files.copy(inputStream, Paths.get(PATH_DOWNLOAD + new File(path).getName()), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Скачка завершена в папку: " + PATH_DOWNLOAD);
    }
}



