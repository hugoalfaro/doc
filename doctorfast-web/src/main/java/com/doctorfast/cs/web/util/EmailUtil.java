/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctorfast.cs.web.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author MBS GROUP
 */
public class EmailUtil {

    private static final Logger LOGGER = LogManager.getLogger(EmailUtil.class);

    private String host;
    private String port;
    private String cc;
    private String alias;
    private final String usuario;
    private final String clave;

    public EmailUtil(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }

    public EmailUtil(String host, String port, String cc, String alias, String usuario, String clave) {
        this.host = host;
        this.port = port;
        this.cc = cc;
        this.alias = alias;
        this.usuario = usuario;
        this.clave = clave;
    }

    public String enviarPorSitio(String correo, String asunto, String mensaje) {

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, clave);
            }
        });

        try {
            final MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(usuario, alias));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo));
            message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
            message.setSubject(asunto);
            message.setText(mensaje, "utf-8", "html");

            new Thread(new Runnable() {
                public void run() {
                    try {
                        Transport.send(message);
                    } catch (MessagingException e) {
                        LOGGER.error("Error enviando", e);
                    }
                }
            }).start();

        } catch (MessagingException e) {
            LOGGER.error("Error enviando", e);
            return correo + "(" + e.getMessage() + "),";
        } catch (UnsupportedEncodingException ex) {
            LOGGER.error("Error enviando", ex);
            return correo + "(" + ex.getMessage() + "),";
        }

        return "";
    }

    public String enviarPorGMAIL(String correo, String asunto, String mensaje) {
        String rpta = "";
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, clave);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(usuario));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo));
            message.setSubject(asunto);
            message.setText(mensaje, "utf-8", "html");

            Transport.send(message);

        } catch (MessagingException e) {
            LOGGER.error("Error enviando", e);
            return correo + "(" + e.getMessage() + "),";
        }

        return rpta;
    }

    public MimeBodyPart adjuntarArchivo(String nombre, String ruta) throws MessagingException {
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(ruta);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(nombre);
        return messageBodyPart;
    }

}
