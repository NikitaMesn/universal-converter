package org;

import java.math.BigDecimal;
import java.util.*;

/*
 * Задачи класса:
 * 1. Хранения связи между единицами измерений
 *
 *
 */


public class MeasurementUnit {
    private final String name;
    private BigDecimal valueToSuperUnit;
    private final List<MeasurementUnit> units = new ArrayList<>();
    private final List<String> unitsName = new ArrayList<>();
    private MeasurementUnit superUnit;
    private MeasurementUnit subUnit;


    public MeasurementUnit(String name, String value, MeasurementUnit referUnit) {
        this.name = name;
        this.superUnit = referUnit;
        this.valueToSuperUnit = new BigDecimal(value);


    }

    public MeasurementUnit(String name) {
        this.name = name;
    }

    public MeasurementUnit() {
        this.name = null;
    }

    public List<MeasurementUnit> getAllUnits() {
        return units;
    }

    public List<String> getAllUnitsName() {
        return unitsName;
    }

    public String getName() {
        return name;
    }

    public void addUnit(MeasurementUnit unit) {
        units.add(unit);
        unitsName.add(unit.getName());
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



    private void setMeasurementUnitByName(String to, MeasurementUnit mainUnit) {

        if (mainUnit.getName().equals(to)) {
            this.subUnit = mainUnit;

        } else {
            if (mainUnit.getAllUnits().size() != 0); {
                for (MeasurementUnit unit: mainUnit.getAllUnits()) {
                    getMeasurementUnitByName(to,  unit);
                }
            }
        }

    }

    public MeasurementUnit getMeasurementUnitByName(String to, MeasurementUnit mainUnit) {
        setMeasurementUnitByName(to, mainUnit);
        return subUnit;
    }


    public Set<String> getAllNamesSuperUnit() {
        Set<String> setOfSuperUnits = new HashSet<>();
        MeasurementUnit temple = this;

        while (temple.getSuperUnit() != null) {
            temple = temple.getSuperUnit();
            setOfSuperUnits.add(temple.getName());
        }

        return setOfSuperUnits;
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



