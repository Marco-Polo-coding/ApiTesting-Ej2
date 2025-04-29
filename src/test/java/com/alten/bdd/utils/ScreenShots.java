package com.alten.bdd.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ScreenShots {

    private static final Logger LOGGER = LogManager.getLogger(ScreenShots.class);
    private static final String EXTENSION = ".png";

    public String screenShot(WebDriver browser, String reportPath) throws IOException {
        try {
            File scrFile = ((TakesScreenshot) browser).getScreenshotAs(OutputType.FILE);

            String fileName = "screenshot_" + System.currentTimeMillis() + EXTENSION;
            File destination = Paths.get(reportPath, fileName).toFile();

            FileUtils.copyFile(scrFile, destination);
            LOGGER.info("Captura guardada en: " + destination.getAbsolutePath());

            return destination.getAbsolutePath();

        } catch (IOException e) {
            LOGGER.error("Error al guardar captura de pantalla: " + e.getMessage());
            return "";
        }
    }
}
