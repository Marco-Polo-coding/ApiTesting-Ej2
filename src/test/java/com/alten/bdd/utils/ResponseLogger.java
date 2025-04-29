package com.alten.bdd.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResponseLogger {
    private static final Logger logger = LogManager.getLogger(ResponseLogger.class);
    
    public static void logResponseStatus(int expectedStatus, int actualStatus) {
        if (expectedStatus == actualStatus) {
            switch (actualStatus) {
                case 200:
                    logger.info("Operación exitosa: Se esperaba 200 (OK) y se recibió 200 (OK)");
                    break;
                case 302:
                    logger.info("Redirección: Se esperaba 302 y se recibió 302");
                    break;
                case 400:
                    logger.error("Error 400: No se encuentra el servicio o la request solicitada (como se esperaba)");
                    break;
                case 500:
                    logger.error("Error 500: Error en el Servicio (como se esperaba)");
                    break;
                default:
                    logger.info("Se recibió el código esperado " + actualStatus);
            }
        } else {
            logger.error("Se esperaba {} ({}) pero se recibió {} ({})", expectedStatus, getDescription(expectedStatus), actualStatus, getDescription(actualStatus));
        }
    }

    private static String getDescription(int statusCode) {
        return switch (statusCode) {
            case 200 -> "OK";
            case 302 -> "Redirección";
            case 400 -> "No se encuentra el servicio o la request solicitada";
            case 404 -> "No encontrado";
            case 500 -> "Error en el Servicio";
            default -> "Código desconocido";
        };
    }

    public static void assertAndLogStatus(int expectedStatus, int actualStatus) {
        logResponseStatus(expectedStatus, actualStatus);
        org.junit.Assert.assertEquals(expectedStatus, actualStatus);
    }

    public static void logCliente(
        String descripcionAccion,
        String endpoint,
        int expectedStatus,
        int actualStatus,
        boolean exito,
        String motivoFallo // null si es éxito
    ) {
        String expectedDesc = getDescription(expectedStatus);
        String actualDesc = getDescription(actualStatus);
        if (exito) {
            logger.info("{}: {}\n       [{}] Código recibido: {} ({})",
                descripcionAccion,
                getMensajeExito(descripcionAccion),
                endpoint,
                actualStatus,
                actualDesc
            );
        } else {
            logger.error("{}: {}\n        Motivo: {}\n        [{}] Código esperado: {} ({}), código recibido: {} ({})",
                descripcionAccion,
                getMensajeFallo(descripcionAccion),
                motivoFallo,
                endpoint,
                expectedStatus,
                expectedDesc,
                actualStatus,
                actualDesc
            );
        }
    }

    private static String getMensajeExito(String accion) {
        return switch (accion.toLowerCase()) {
            case "creación de mascota" -> "La mascota se creó correctamente.";
            case "búsqueda de mascota tras creación" -> "La mascota se encontró correctamente después de crearla.";
            case "actualización de mascota" -> "La mascota se actualizó correctamente.";
            case "eliminación de mascota" -> "La mascota se eliminó correctamente.";
            case "verificación de eliminación" -> "La mascota ya no existe, como se esperaba.";
            default -> "La acción se realizó correctamente.";
        };
    }

    private static String getMensajeFallo(String accion) {
        return switch (accion.toLowerCase()) {
            case "creación de mascota" -> "No se pudo crear la mascota.";
            case "búsqueda de mascota tras creación" -> "No se pudo encontrar la mascota después de crearla.";
            case "actualización de mascota" -> "No se pudo actualizar la mascota.";
            case "eliminación de mascota" -> "No se pudo eliminar la mascota.";
            case "verificación de eliminación" -> "La mascota todavía existe, pero no debería.";
            default -> "La acción no se pudo completar.";
        };
    }
} 