package com.bookmanager.bookmanager.services.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.FileInputStream;
import java.io.IOException;

public final class FtpUploader {

    public static void uploadFile(String server, int port, String user, String pass, String filePath, String remotePath) throws IOException {
        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            try (FileInputStream inputStream = new FileInputStream(filePath)) {
                ftpClient.storeFile(remotePath, inputStream);
            }

        } finally {
            ftpClient.logout();
            ftpClient.disconnect();
        }
    }
}
