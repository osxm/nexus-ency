/**
 * Copyright (C)  Oscar Chen(XM):
 * 
 * Date: 2024-10-31
 * Author: XM
 */

package com.osxm.nexus;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class NexusUploader {

    public static void upload(String repoUrl, String userName, String password, String filePath) {
        // String nexusUrl = "http://<nexus-host>:<nexus-port>/repository/<repo-id>/";
        // String username = "your-username";
        // String password = "your-password";
        // String filePath = "path/to/your/file.js";

        try {
            Path path = Paths.get(filePath);
            byte[] fileBytes = Files.readAllBytes(path);
            String auth = userName + ":" + password;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(repoUrl))
                    .header("Authorization", "Basic " + encodedAuth)
                    .header("Content-Type", "application/octet-stream") // 根据需要调整Content-Type
                    .POST(BodyPublishers.ofByteArray(fileBytes))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response status code: " + response.statusCode());
            System.out.println("Response body: " + response.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
