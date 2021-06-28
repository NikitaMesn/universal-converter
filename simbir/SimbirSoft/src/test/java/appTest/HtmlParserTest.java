package appTest;


import app.HtmlParser;
import org.junit.Test;
import java.util.List;

public class HtmlParserTest {

    @Test(expected = IllegalArgumentException.class)
    public void newHTMLParserShouldThrowIllArgException()  {
        List.of("hello.com", "http:/google.com", "http://google")
                .forEach(HtmlParser::new);
    }

    @Test(expected = NullPointerException.class)
    public void newHTMLParserShouldThrowNullPointerException()  {
        List.of("http://hafgbr.com/ru/post/3hehetr/", "http://gloogle.com" )
                .forEach(HtmlParser::new);
    }


}
