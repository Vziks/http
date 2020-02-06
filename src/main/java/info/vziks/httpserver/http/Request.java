package info.vziks.httpserver.http;


import info.vziks.httpserver.utils.RequestLogger;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class Request
 * Project httpserver
 *
 * @author Anton Prokhorov <vziks@live.ru>
 */
public class Request {

    private BufferedReader reader;
    private String headerString;
    private Map<String, String> headers;
    private String body;
    private String method;
    private String route;
    private String host;
    private Map<String, String> authorization;
    private Map<String, Integer> range;
    private Map<String, String> params;
    private String log;
    private String baseDirectory;
    private LinkedList<String> logs;

    public Request(InputStream input) {
        this.reader = this.getReader(input);
        this.headerString = this.getHeaderString();
        this.headers = this.getHeaders();
        this.body = this.getBody();
        this.method = this.initMethod();
        this.route = this.initRoute();
        this.host = headers.get("Host");
        this.authorization = this.getAuth();
        this.range = this.initRange();
        this.params = this.getParams();
        this.log = headerString + body;
    }

    public BufferedReader getReader(InputStream input) {
        BufferedReader newReader;
        newReader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        return newReader;
    }

    public String getHeaderString() {
        StringBuilder headers = new StringBuilder();
        try {
            String line = reader.readLine();
            while (line != null && !"".equals(line)) {
                headers.append(line);
                headers.append("\r\n");
                line = reader.readLine();
            }
        } catch (IOException e) {
            RequestLogger.log(this, RequestLogger.ERROR, e);
            e.printStackTrace();
        }
        return headers.toString();
    }

    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        String[] rawHeaders = headerString.split("\r\n");
        for (String header : rawHeaders) {
            String[] headerPair = header.split("(: )");
            headers.put(headerPair[0], headerPair[headerPair.length - 1]);
        }
        return headers;
    }

    public String getBody() {
        StringBuilder body = new StringBuilder();
        String length = headers.get("Content-Length");
        if (length != null) {
            while (body.length() < Integer.parseInt(length)) {
                try {
                    body.append((char) reader.read());
                } catch (IOException e) {
                    RequestLogger.log(this, RequestLogger.ERROR, e);
                    e.printStackTrace();
                }
            }
        }
        return body.toString();
    }

    public String searchHeaders(String regex) {

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(headerString);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }

    public String initMethod() {
        return searchHeaders("(^GET|POST|PUT|OPTIONS|DELETE)");
    }

    public String initRoute() {
        String route = searchHeaders("(\\/[^\\s\\?]*)");
        try {
            route = URLDecoder.decode(route, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            RequestLogger.log(this, RequestLogger.ERROR, e);
            e.printStackTrace();
        }
        return route;
    }

    public Map<String, String> getAuth() {
        Map<String, String> protocol = new HashMap<>();
        String auth = headers.get("Authorization");
        if (auth != null) {
            String[] pair = auth.split(" ");
            protocol.put(pair[0], pair[1]);
        }
        return protocol;
    }

    public Map<String, Integer> initRange() {
        Map<String, Integer> rangeMap = new HashMap<>();
        String[] range = this.searchHeaders("((?<=\\nRange: bytes=)([^\\r]+))").split("-");
        if (range.length == 2) {
            rangeMap.put("Start", Integer.parseInt(range[0]));
            rangeMap.put("Stop", Integer.parseInt(range[1]));
            int length = rangeMap.get("Stop") - rangeMap.get("Start") + 1;
            rangeMap.put("Length", length);
        }
        return rangeMap;
    }

    public Map<String, String> getParams() {
        if (getQueryString().isEmpty()) {
            return this.getFormParams();
        } else {
            Map<String, String> params = this.getFormParams();
            params.putAll(this.getQueryString());
            return params;
        }
    }

    public Map<String, String> getFormParams() {
        Map<String, String> params = new HashMap<>();
        String[] rawParams = body.split("&");
        for (String param : rawParams) {
            String[] paramPair = param.split("=");
            if (!"".equals(paramPair[0])) {
                try {
                    params.put(paramPair[0], URLDecoder.decode(paramPair[paramPair.length - 1], StandardCharsets.UTF_8.toString()));
                } catch (UnsupportedEncodingException e) {
                    RequestLogger.log(this, RequestLogger.ERROR, e);
                    e.printStackTrace();
                }
            }
        }
        return params;
    }

    public Map<String, String> getQueryString() {

        Map<String, String> queryString = new HashMap<>();

        String[] queries = this.searchHeaders("(?<=\\?)(\\S+)").split("&");

        for (String query : queries) {
            String[] queryPair = query.split("=");
            String key;
            String value;
            try {
                key = URLDecoder.decode(queryPair[0], StandardCharsets.UTF_8.toString());
                value = URLDecoder.decode(queryPair[queryPair.length - 1], StandardCharsets.UTF_8.toString());
                queryString.put(key, value);
            } catch (UnsupportedEncodingException e) {
                RequestLogger.log(this, RequestLogger.ERROR, e);
                e.printStackTrace();
            }
        }
        return queryString;
    }

    public String getHost() {
        return host;
    }

    public Map<String, String> getAuthorization() {
        return authorization;
    }

    public String getLog() {
        return log;
    }

    public String getBaseDirectory() {
        return baseDirectory;
    }

    public LinkedList<String> getLogs() {
        return logs;
    }

    public Request setBaseDirectory(String baseDirectory) {
        this.baseDirectory = baseDirectory;
        return this;
    }

    public Request setLogs(LinkedList<String> logs) {
        this.logs = logs;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public String getRoute() {
        return route;
    }

    public Map<String, Integer> getRange() {
        return range;
    }

    public Request setMethod(String method) {
        this.method = method;
        return this;
    }

    public Request setRoute(String route) {
        this.route = route;
        return this;
    }
}
