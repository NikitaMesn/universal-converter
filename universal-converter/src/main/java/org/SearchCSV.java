package org;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/*
 * Задачи класса:
 * 1. Создание связнанных элементов MeasurementUnit
 * 2. Проверка нахождения элементов запроса в CSV файле
 *
 *
 *  в файл можно добавлять новые правила, без отключения сервера
 */

public class SearchCSV {
    String pathToCSV;
    CSVReader reader;
    String[] nextLine;

    public SearchCSV(String pathToCSV)  {
        this.pathToCSV = pathToCSV;
    }

    public void createMeasurementUnit(MeasurementUnit unit) throws IOException {

        reader = new CSVReader(new FileReader(pathToCSV), ',', '"', 0);

        while ((nextLine = reader.readNext()) != null) {

            if (nextLine.length == 3  && unit.getName().equals(nextLine[0])
                    && !unit.getAllNamesSuperUnit().contains(nextLine[1])
                    && !unit.getAllUnitsName().contains(nextLine[0])) {

                MeasurementUnit subUnit = new MeasurementUnit(nextLine[1], nextLine[2], unit);
                if (!unit.getAllUnitsName().contains(subUnit.getName())) {
                    unit.addUnit(subUnit);
                }
            }
            if (nextLine.length == 3 && unit.getName().equals(nextLine[1])
                    && !unit.getAllNamesSuperUnit().contains(nextLine[0])
                    && !unit.getAllUnitsName().contains(nextLine[0])) {

                BigDecimal value = new BigDecimal(nextLine[2]);
                value = getDecimal(value);

                MeasurementUnit subUnit = new MeasurementUnit(nextLine[0],
                        "" + value, unit);
                if (!unit.getAllUnitsName().contains(subUnit.getName())) {
                    unit.addUnit(subUnit);
                }
            }
        }
        reader.close();

    }

    private BigDecimal getDecimal(BigDecimal value) {
        value = BigDecimal.ONE.divide(value,15, BigDecimal.ROUND_HALF_UP);
        return value;
    }

    public void setAllSubUnits(MeasurementUnit unit) throws IOException { // рекурсией находим вся связанные элементы
        if (unit.getAllUnits().size() != 0) {
            for(MeasurementUnit u: unit.getAllUnits()) {
                createMeasurementUnit(u);
                setAllSubUnits(u);
            }
        }
    }

    public Boolean checkNameInCSV (List<String> list) throws IOException {
        boolean isConsist = false;

        for (String str : list) {
            isConsist = false;

            reader = new CSVReader(new FileReader(pathToCSV), ',', '"', 0);
            while ((nextLine = reader.readNext()) != null) {
                if ((nextLine.length == 3)  && str.equals(nextLine[0])) {
                    isConsist = true;
                }

                if ((nextLine.length == 3) && str.equals(nextLine[1])) {
                    isConsist = true;
                }
            }
        }
        reader.close();
        return isConsist;
    }
}
