package com.telenav.kain.test.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by zfshi on 5/27/2015.
 */
public class TestProcessBuilder {

    public static void main(String[] args) throws IOException, InterruptedException {

        ProcessBuilder processBuilder = new ProcessBuilder(new String[]{"cmd.exe", "ping www.baidu.com"});
//        processBuilder.command("ipconfig");

        Process process = processBuilder.start();
        StringBuilder result = new StringBuilder();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
                System.out.println(processBuilder.command().toString() + " --->: " + line);
            }
        } catch (IOException e) {
            System.out.println("failed to read output from process");
            e.printStackTrace();
        } finally {
            reader.close();
        }
        process.waitFor();
        int exit = process.exitValue();
        if (exit != 0) {
            throw new IOException("failed to execute:" + processBuilder.command() + " with result:" + result);
        } else {
            System.out.println("Success!");
        }

    }
}
