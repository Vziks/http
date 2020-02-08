package info.vziks.httpserver.controller;

import info.vziks.httpserver.interfaces.HTTPBody;
import info.vziks.httpserver.interfaces.Interpreter;
import info.vziks.httpserver.interpreter.PHPInterpreter;
import info.vziks.httpserver.interpreter.PythonInterpreter;
import info.vziks.httpserver.utils.RequestLogger;
import org.apache.tika.Tika;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

public class FileBody implements HTTPBody {

    private Map<String, Interpreter> stringInterpreterMap;

    public String filePath;
    public File file;
    public int start;
    public int stop;

    public FileBody(String filePath) {
        this.filePath = filePath;
        this.file = new File(filePath);
        this.start = 0;
        this.stop = (int) file.length();
        this.init();
    }

    private void init() {
        this.stringInterpreterMap = new HashMap<>();
        this.stringInterpreterMap.put("text/x-php", new PHPInterpreter());
        this.stringInterpreterMap.put("text/x-python", new PythonInterpreter());
    }

    public byte[] output() {
        byte[] output = null;
        try {

            output = this.readFile();
            if (this.stringInterpreterMap.containsKey(contentType())) {
                output = this.stringInterpreterMap.get(contentType())
                        .process(file)
                        .toString()
                        .getBytes();
            }
        } catch (IOException e) {
            RequestLogger.log(RequestLogger.ERROR, e);
            e.printStackTrace();
        }
        return this.gzipCompress(output);
    }

    public String contentType() {
        String mimeType = "text/plain";

        Tika tika = new Tika();
        try {
            mimeType = tika.detect(file);
        } catch (IOException e) {
            RequestLogger.log(RequestLogger.ERROR, e);
            e.printStackTrace();
        }
        return mimeType;
    }

    public long contentLength() {
        return stop;
    }

    public void setRange(int start, int stop) {
        this.start = start;
        this.stop = stop;
    }

    private byte[] readFile() throws IOException {
        Path path = Paths.get(filePath);
        return Arrays.copyOfRange(Files.readAllBytes(path), start, stop);
    }


    public static byte[] gzipCompress(byte[] uncompressedData) {
        byte[] result = new byte[]{};
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(uncompressedData.length);
             GZIPOutputStream gzipOS = new GZIPOutputStream(bos)) {
            gzipOS.write(uncompressedData);
            gzipOS.close();
            result = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
