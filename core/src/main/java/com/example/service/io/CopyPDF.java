package com.example.service.io;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyPDF {


    public static void main(String[] args) {
        // spring读取文件到输入流
        String path = "CandlestickChartReport.pdf";
        Resource resource = new ClassPathResource(path);
        InputStream inputStream = null;
        OutputStream out = null;

        try {
            inputStream = resource.getInputStream();
            out = new FileOutputStream("a.pdf");
            byte[] bytes = new byte[1024 * 8];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
