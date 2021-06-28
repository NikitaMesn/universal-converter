package app;

import java.util.Scanner;

public class MainApp {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args)   {

        start("Введите url:");
    }

    public static void start(String text) {
        System.out.println(text);
        String url = input.nextLine();

        try {
            HtmlParser siteParser = new HtmlParser(url);
            siteParser.printStatistic();
            siteParser.savePage();
        } catch (IllegalArgumentException illegalArgumentException) {
            ExceptionUtils.writeExceptionToFile(illegalArgumentException);
            System.out.println("Ошибка в url.\nСсылка должна начинаться с http:// ");
            //start("Ошибка в url-адресе, попробуйте еще раз:");
        }catch (NullPointerException nullPointerException){
            System.out.println("Такого сайта не существует. Попробуйте еще раз");
        } catch (Exception e) {
            ExceptionUtils.writeExceptionToFile(e);
            System.out.println("Что то пошло не так... " + "\nПопробуйте еще раз");
            //start("Что то пошло не так... " + "\nПопробуйте еще раз:");
        }
    }
}
