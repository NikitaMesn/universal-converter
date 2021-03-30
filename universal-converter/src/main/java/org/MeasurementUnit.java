package org;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MeasurementUnit {
    private final String name;
    private BigDecimal valueToSuperUnit;
    private final List<MeasurementUnit> units = new ArrayList<>();
    private final List<String> unitsName = new ArrayList<>();
    private MeasurementUnit superUnit;
    private static List<MeasurementUnit> allUnits = new ArrayList<>();


    public MeasurementUnit(String name, String value, MeasurementUnit referUnit) {
        this.name = name;
        this.superUnit = referUnit;
        this.valueToSuperUnit = new BigDecimal(value);
        allUnits.add(this); //создал список из всех новых unit, чтобы проще найти нужный

    }

    public MeasurementUnit(String name) {
        this.name = name;
    }

    public List<MeasurementUnit> getAllUnits() {
        return units;
    }

    public List<String> getAllUnitsName() {
        return unitsName;
    }

    public void addUnit(MeasurementUnit unit) {
        units.add(unit);
        unitsName.add(unit.getName());
    }

    public String getName() {
        return name;
    }


    public BigDecimal getValueToSuperUnit() {
        return valueToSuperUnit;
    }

    public  MeasurementUnit getSuperUnit() {
        return superUnit;
    }

    public String getSuperUnitName() {
        return  superUnit.getName();
    }

    public Set<String> getAllNamesSuperUnit() {
        Set<String> setSuperUnits = new HashSet<>();
        MeasurementUnit temple = this;

        while (temple.getSuperUnit() != null) {
            temple = temple.getSuperUnit();
            setSuperUnits.add(temple.getName());
        }

        return setSuperUnits;
    }

    public MeasurementUnit getMeasurementUnitByName(String from, String to) {
        for (MeasurementUnit un: allUnits) {
            if (to.equals(un.getName())) {
                if(un.getAllNamesSuperUnit().contains(from)) {
                    return un;
                }
            }
        }
        return null;
    }


    public static BigDecimal getConvertValue(MeasurementUnit from, MeasurementUnit to) {
        BigDecimal value;
        value = BigDecimal.ONE;

        while(!from.getName().equals(to.getName())) {
            value = value.multiply(to.getValueToSuperUnit());
            to = to.getSuperUnit();
        }

        return value.setScale(15, BigDecimal.ROUND_HALF_UP);
    }

}


