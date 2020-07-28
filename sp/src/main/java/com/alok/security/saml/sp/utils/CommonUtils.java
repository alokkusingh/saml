package com.alok.security.saml.sp.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

@Slf4j
public class CommonUtils {
    public static String parsePEMFile(String pemFilePath) throws IOException {
        File pemFile = new File(pemFilePath);
        if (!pemFile.isFile() || !pemFile.exists()) {
            throw new FileNotFoundException(String.format("The file '%s' doesn't exist.", pemFile.getAbsolutePath()));
        }
        StringBuilder content = new StringBuilder();
        Scanner scanner = new Scanner(pemFile);
        while (scanner.hasNextLine()) {
            content.append(scanner.nextLine());
            content.append("\n");
        }

        scanner.close();

        return content.toString();
    }
}
