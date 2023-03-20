package com.example.maman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Button;

import java.net.URL;
import java.time.Month;
import java.util.Calendar;
import java.util.Random;
import java.util.ResourceBundle;

public class TemperatureChart implements Initializable {

    @FXML
    private BarChart<String, Double> temperatureChart;

    @FXML
    private Button nextYearButton;

    private  int MIN_TEMP =10;
    private  int MAX_TEMP = 40;

    private int currentYear;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        updateChartData(currentYear);
    }

    @FXML
    private void handleNextYearButton(ActionEvent event) {
        currentYear++;
        updateChartData(currentYear);
    }

    private void updateChartData(int year) {
        XYChart.Series<String, Double> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Temperature Data for Year " + year);

        Random rand = new Random();
        double[] avgTemperatures = new double[12];
        for (int i = 1; i <= 12; i++) {
//            double tempSum = 0;
            double avgTemp=rand.nextInt(MAX_TEMP)+MIN_TEMP;
//            for (int j = 0; j < 30; j++) {
//                tempSum += rand.nextInt(MAX_TEMP)+MIN_TEMP;
//            }
//            double avgTemp = tempSum / 30.0;
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
        double coldestMonthAvg = avgTemperatures[coldestMonth];
        double hottestMonthAvg = avgTemperatures[hottestMonth];

        for (int i = 1; i <= 12; i++) {
            String monthName = getMonthName(i);
            double avgTemp = avgTemperatures[i-1];

            XYChart.Data<String, Double> data = new XYChart.Data<>(monthName, avgTemp);
            Node node = data.getNode();
            if (avgTemp == hottestMonthAvg) {
//                node.setStyle("-fx-bar-fill: -fx-exceeded;");
            } else if (avgTemp == coldestMonthAvg) {
//                node.setStyle("-fx-bar-fill: -fx-exceeded;");
            } else {
//                Node node = data.getNode();
                if (node != null) {
                    node.setStyle("");
                }
            }
            dataSeries.getData().add(data);

        }

        temperatureChart.getData().clear();
        temperatureChart.getData().add(dataSeries);
    }

    private String getMonthName(int monthNumber) {
        return Month.of(monthNumber).name();
    }
}