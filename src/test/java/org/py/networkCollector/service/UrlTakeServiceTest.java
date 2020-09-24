package org.py.networkCollector.service;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UrlTakeServiceTest {
    @Test
    public void test() throws IOException {
        final URL url = new URL("http://taohuazu9.com/thread-2230624-1-1.html");
        System.out.println(url.getProtocol());
        System.out.println(url.getHost());
        System.out.println(url.getPath());
        System.out.println(url.getFile());
        System.out.println(Paths.get(url.getFile()).getFileName());
    }

    @Test
    public void pathTest() {
        final Path path = Paths.get("a/b/c/d/e");
        final Path f = path.resolve("f");
        System.out.println(path);
        System.out.println(f);
        System.out.println(f.relativize(Paths.get("a/b/c")));
    }
}