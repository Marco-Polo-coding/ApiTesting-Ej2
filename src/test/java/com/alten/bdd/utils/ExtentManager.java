package com.alten.bdd.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ExtentManager {

    private static final Logger LOGGER = LogManager.getLogger(ExtentManager.class);
    private static final String REPORT_BASE_DIR = "target/extent-reports/";
    private static ExtentReports extent;
    private static String currentReportPath;

    /**
     * Crea el reporte solo si hay fallos.
     */
    public static ExtentReports createReportIfNeeded(String suiteName) {
        if (extent != null) return extent;

        String safeName = suiteName.replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9_]", "");
        String folderName = "Test_" + safeName + "_Failed";
        String dirPath = REPORT_BASE_DIR + folderName + "/";
        String filePath = dirPath + "ExtentReport.html";

        File reportDir = new File(dirPath);
        if (!reportDir.exists() && reportDir.mkdirs()) {
            LOGGER.info("Carpeta creada: " + dirPath);
        }

        ExtentSparkReporter reporter = new ExtentSparkReporter(filePath);
        reporter.config().setReportName("Reporte de pruebas: " + suiteName);
        reporter.config().setDocumentTitle("Resultado de ejecución: " + suiteName);

        extent = new ExtentReports();
        extent.attachReporter(reporter);

        currentReportPath = filePath;
        LOGGER.info("Ruta del reporte: " + filePath);

        return extent;
    }

    public static String getCurrentReportPath() {
        return currentReportPath;
    }
}
