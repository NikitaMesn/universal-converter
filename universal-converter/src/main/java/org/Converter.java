package org;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


/*
 * Задачи класса:
 * 1. Получить пригодный для конвертации вид данных запроса
 * 2. Осущиствить проверку возможности провести конвертацию
 * 3. Провести конвертацию
 *
 */
public class Converter {
    private Map<String, String> mapPOST;
    private String from;
    private String to;
    private SearchCSV csv;


    public Converter(String pathToCSV, Map<String, String> mapPOST) throws FileNotFoundException {
        this.mapPOST = mapPOST;
        this.from = mapPOST.get("from");
        this.to = mapPOST.get("to");
        this.csv= new SearchCSV(pathToCSV);


    }

    private List<String> createNumerator() throws Exception400 {
        List<String> numerator = new ArrayList<>();
        if ("".contains(from)) {

        } else if (from.contains("1/")) {

            numerator.addAll(Arrays.asList(from.split("\\*")));
        } else if (from.contains("/")) {
            String[] fr = from.split("/");
            if (fr.length > 2) throw new Exception400();

            numerator.addAll(Arrays.asList(fr[0].split("\\*")));
        } else {
            numerator.addAll(Arrays.asList(from.split("\\*")));
        }

        if (to.contains("/") && !to.contains("1/")) {
            String[] t = to.split("/");
            if (t.length > 2) throw new Exception400();
            numerator.addAll(Arrays.asList(t[1].split("\\*")));
        }

        return numerator;

    }

    private List<String> createDenominator() throws Exception400 {
        List<String> denominator = new ArrayList<>();

        if ("".equals(to)) {

        }else if (to.contains("1/")) {
            denominator.addAll(Arrays.asList(to.split("\\*")));// в задание сказано что могут быть герцы

        } else if (to.contains("/")) {
            String[] t = to.split("/");
            if (t.length > 2) throw new Exception400();
            denominator.addAll(Arrays.asList(t[0].split("\\*")));

        } else  {
            denominator.addAll(Arrays.asList(to.split("\\*")));
        }

        if (from.contains("/") && !from.contains("1/")) {
            String[] f= from.split("/");
            if (f.length > 2) throw new Exception400();
            denominator.addAll(Arrays.asList(f[1].split("\\*")));
        }

        return denominator;
    }


    private Map<String, List<String>> chekNumDem() throws Exception404, Exception400, IOException {
        //проверяем все элементы запроса, в случаю не подходящих, бросаем ошибку, которую ловим в EchoHandler
        Map<String, List<String>> mapNumDem = new HashMap<>();
        List<String> numerator = createNumerator();
        List<String> denominator = createDenominator();
        List<String> duplicates = new ArrayList<>();


        if (  (numerator.size() != 0 || denominator.size() != 0)
                && (numerator.size() < denominator.size() || numerator.size() > denominator.size())
            ) throw new Exception404();

        if(!csv.checkNameInCSV(numerator) || !csv.checkNameInCSV(denominator)) {
            throw new Exception400();
        }

        for (int i = 0; i < numerator.size(); i++) {
            for (int j = 0; j < denominator.size(); j++) {
                if (numerator.get(i).equals(denominator.get(j))) {
                    duplicates.add(numerator.get(i));
                }
            }
        }

        numerator.removeAll(duplicates);
        denominator.removeAll(duplicates);

        mapNumDem.put("numerator", numerator);
        mapNumDem.put("denominator",denominator);
        return mapNumDem;
    }


    public String makeConvert() throws Exception404, IOException, Exception400 {
        BigDecimal result = BigDecimal.ONE;
        List<String> numerator = chekNumDem().get("numerator");
        List<String> denominator = chekNumDem().get("denominator");

        if (numerator.size() == 0 && denominator.size() == 0 ) {
            return BigDecimal.ONE.toString();
        }

        for (String num : numerator) {

            MeasurementUnit unit = new MeasurementUnit(num);
            csv.createMeasurementUnit(unit);
            csv.setAllSubUnits(unit);

            MeasurementUnit searchUnit = null;
            for (String den : denominator) {

                searchUnit = new MeasurementUnit();

                searchUnit = searchUnit.getMeasurementUnitByName(
                        den,  unit);

                if (searchUnit != null) break;

            }
            if (searchUnit == null) throw new Exception404();

            result = result.multiply(MeasurementUnit.getConvertValue(unit,searchUnit));
        }

        result = result.setScale(15,
                BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros();


        if (result.scale()<0)
            result= result.setScale(0);



        return result.toPlainString();
    }
}

class Exception404 extends Exception {

}

class Exception400 extends Exception {

}
