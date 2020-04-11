/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.util;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author MBS GROUP
 */
public final class ArchivosUtil {

    private static final Logger LOGGER = LogManager.getLogger(ArchivosUtil.class);

    public static Boolean subirArchivo(MultipartFile file, String ruta, String nombreArchivo) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                File dir = new File(ruta);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File serverFile = new File(dir.getAbsolutePath() + File.separator + nombreArchivo);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                LOGGER.info("Server File Location=" + serverFile.getAbsolutePath());

            } catch (Exception e) {
                LOGGER.error("Error al subir archivo: " + nombreArchivo, e);
                return false;
            }
        } else {
            LOGGER.error("Error al subir archivo: " + nombreArchivo + ", archivo esta vacio.");
            return false;
        }
        return true;
    }

    public static void eliminarArchivo(String ruta, String nombreArchivo) {
        File archivo = new File(ruta + File.separator + nombreArchivo);
        try {
            if (archivo.exists()) {
                archivo.delete();
            }
        } catch (Exception e) {
            LOGGER.error("Error al eliminar el archivo: " + nombreArchivo, e);
        }
    }

    public static String generarNombreArchivo(MultipartFile archivo, String prefijo) {
        Date ahora = new Date();
        String extension = FilenameUtils.getExtension(archivo.getOriginalFilename());
        return prefijo + "_" + ahora.getTime() + "." + extension;
    }

    public static Boolean subirArchivoAWS(MultipartFile multipartFile, String ruta, String nombreArchivo) {
        AmazonS3 s3client = AmazonS3ClientBuilder.standard().build();
        try {
            LOGGER.info("Uploading a new object to S3 from a file");
            PutObjectRequest request = new PutObjectRequest(ruta, nombreArchivo, multipartFile.getInputStream(), new ObjectMetadata());
            request.setCannedAcl(CannedAccessControlList.PublicRead);
            s3client.putObject(request);

        } catch (AmazonServiceException ase) {
            LOGGER.error("Caught an AmazonServiceException, which "
                    + "means your request made it "
                    + "to Amazon S3, but was rejected with an error response"
                    + " for some reason.");
            LOGGER.error("Error Message:    " + ase.getMessage());
            LOGGER.error("HTTP Status Code: " + ase.getStatusCode());
            LOGGER.error("AWS Error Code:   " + ase.getErrorCode());
            LOGGER.error("Error Type:       " + ase.getErrorType());
            LOGGER.error("Request ID:       " + ase.getRequestId());
            return false;
        } catch (AmazonClientException ace) {
            LOGGER.error("Caught an AmazonClientException, which "
                    + "means the client encountered "
                    + "an internal error while trying to "
                    + "communicate with S3, "
                    + "such as not being able to access the network.");
            LOGGER.error("Error Message: " + ace.getMessage());
            return false;
        } catch (IOException ex) {
            LOGGER.error("Error Message: " + ex.getMessage());
            return false;
        }
        return true;
    }

}
