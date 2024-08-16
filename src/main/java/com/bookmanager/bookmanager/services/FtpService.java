package com.bookmanager.bookmanager.services;

import com.bookmanager.bookmanager.services.util.ExcelExporter;
import com.bookmanager.bookmanager.services.util.FtpUploader;
import com.bookmanager.bookmanager.services.util.TxtExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class FtpService {

    @Value("${ftp.server}")
    private String ftpServer;

    @Value("${ftp.port}")
    private int ftpPort;

    @Value("${ftp.username}")
    private String ftpUsername;

    @Value("${ftp.password}")
    private String ftpPassword;

    @Value("${ftp.remote.directory}")
    private String ftpRemoteDirectory;

    @Value("${ftp.local.directory}")
    private String ftpLocalDirectory;

    public void sendFileToFtpServer(List<MultipartFile> files, String format) throws IOException {
        for (MultipartFile file : files) {

            List<String> lines = new BufferedReader(new InputStreamReader(file.getInputStream()))
                    .lines().toList();

            String excel = "excel";
            String originalFilename = file.getOriginalFilename();

            String baseFilename = originalFilename != null && originalFilename.contains(".")
                    ? originalFilename.substring(0, originalFilename.lastIndexOf('.'))
                    : originalFilename;

            String localPath = ftpLocalDirectory + baseFilename + (format.equalsIgnoreCase(excel) ? ".xlsx" : ".txt");

            if (format.equalsIgnoreCase(excel)) {
                ExcelExporter.exportToExcel(lines, localPath);
            } else if (format.equalsIgnoreCase("txt")) {
                TxtExporter.exportToTxt(lines, localPath);
            } else {
                throw new IOException("Invalid format");
            }

            String remotePath = ftpRemoteDirectory + baseFilename + (format.equalsIgnoreCase(excel) ? ".xlsx" : ".txt");
            FtpUploader.uploadFile(ftpServer, ftpPort, ftpUsername, ftpPassword, localPath, remotePath);
        }
    }
}
