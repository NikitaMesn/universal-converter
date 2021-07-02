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
            siteParser.saveStatistic();
        } catch (IllegalArgumentException illegalArgumentException) {
            ExceptionUtils.writeExceptionToFile(illegalArgumentException);
            System.out.println("Ошибка в url.\nСсылка должна начинаться на http:// или https:// ");

        }catch (NullPointerException nullPointerException){
            System.out.println("Такого сайта не существует. Проверьте URL");
        } catch (Exception e) {
            ExceptionUtils.writeExceptionToFile(e);
            System.out.println("Что то пошло не так... " + "\nПопробуйте еще раз");
        }
    }
}
