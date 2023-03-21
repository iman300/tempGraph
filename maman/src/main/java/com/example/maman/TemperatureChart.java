package com.example.maman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Button;

import java.net.URL;
import java.time.Month;
import java.util.*;

public class TemperatureChart implements Initializable {

    @FXML
    private BarChart<String, Double> temperatureChart;

    @FXML
    private Button nextYearButton;
    @FXML
    private Button previousYearButton;




    private final int MIN_TEMP = 10;
    private final int MAX_TEMP = 40;
    private final int NUM_YEARS = 5;

    private int currentYear;
    private List<XYChart.Series<String, Double>> yearData;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        yearData = new ArrayList<>(NUM_YEARS);
        for (int i = 0; i < NUM_YEARS; i++) {
            yearData.add(null);
        }
        updateChartData(currentYear);
    }
    @FXML
    private void handleNextYearButton(ActionEvent event) {
        if (currentYear < Calendar.getInstance().get(Calendar.YEAR)) {
            currentYear++;
            updateChartData(currentYear);
        }
    }

    @FXML
    private void handlePrevYearButton(ActionEvent event) {
        if (currentYear > Calendar.getInstance().get(Calendar.YEAR) - NUM_YEARS) {
            currentYear--;
            updateChartData(currentYear);
        }
    }
    private Map<Integer, XYChart.Series<String, Double>> temperatureData = new HashMap<>();

    private void updateChartData(int year) {
        if (!temperatureData.containsKey(year)) {
            XYChart.Series<String, Double> dataSeries = new XYChart.Series<>();
            dataSeries.setName("Temperature Data for Year " + year);

            Random rand = new Random();
            double[] avgTemperatures = new double[12];
            for (int i = 1; i <= 12; i++) {
                double avgTemp = rand.nextInt(MAX_TEMP) + MIN_TEMP;
                avgTemperatures[i - 1] = avgTemp;
            }

            int hottestMonth = 0;
            int coldestMonth = 0;
            for (int i = 1; i < avgTemperatures.length; i++) {
                if (avgTemperatures[i] > avgTemperatures[hottestMonth]) {
                    hottestMonth = i;
                }
                if (avgTemperatures[i] < avgTemperatures[coldestMonth]) {
                    coldestMonth = i;
                }
            }

            for (int i = 1; i <= 12; i++) {
                String monthName = getMonthName(i);
                double avgTemp = avgTemperatures[i - 1];

                XYChart.Data<String, Double> data = new XYChart.Data<>(monthName, avgTemp);
                dataSeries.getData().add(data);

            }
            temperatureData.put(year, dataSeries);
            temperatureChart.getData().clear();
            temperatureChart.getData().add(temperatureData.get(year));

            // add style to hottest and coldest month columns
            Node hottestMonthNode = dataSeries.getData().get(hottestMonth).getNode();
            hottestMonthNode.setStyle("-fx-bar-fill: red;");

            Node coldestMonthNode = dataSeries.getData().get(coldestMonth).getNode();
            coldestMonthNode.setStyle("-fx-bar-fill: blue;");

        } else {
            temperatureChart.getData().clear();
            temperatureChart.getData().add(temperatureData.get(year));
        }
    }


    private String getMonthName(int monthNumber) {
        return Month.of(monthNumber).name();
    }

}