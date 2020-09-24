package org.py.networkCollector.service;

import lombok.extern.java.Log;
import org.apache.http.client.fluent.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.py.networkCollector.model.Protocol;
import org.py.networkCollector.model.Remote;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Log
public final class UrlTakeService extends Service {
    private Remote entity;
    private String defaultSaveDir = "files";

    public UrlTakeService(String url, String query, String attr, Path savePath) {
        entity = Remote.builder()
                .url(url)
                .query(query)
                .attr(attr)
                .savePath(savePath)
                .build();
    }

    @Override
    public String toString() {
        return entity.toString();
    }

    public Remote getEntity() {
        return entity;
    }

    public void setEntity(Remote entity) {
        this.entity = entity;
    }

    public List<String> tack() throws IOException {
        final Document doc = Jsoup.connect(entity.getUrl()).get();
        final Elements elements = doc.select(entity.getQuery());
        final List<String> msgList = new ArrayList<>();
        if (elements.size() == 0) {
            JOptionPane.showMessageDialog(null, "未查找到任何资源！");
            return msgList;
        }
        Path savePath = entity.getSavePath() != null ? entity.getSavePath().resolve(defaultSaveDir) : Paths.get(defaultSaveDir);
        if (!Files.exists(savePath))
            Files.createDirectories(savePath);
        elements.forEach(el -> {
            String attr = el.attr(entity.getAttr());
            if (!attr.startsWith(Protocol.http.get()) || !attr.startsWith(Protocol.https.get())) {
                String host;
                try {
                    host = getHost(entity.getUrl());
                } catch (MalformedURLException e) {
                    throw new IllegalStateException(e);
                }
                attr = attr.startsWith(separator) ? host + attr : host + separator + attr;
            }
            String fn = attr.substring(attr.lastIndexOf(separator));
            String extFn = fn.substring(fn.lastIndexOf(dot));
            String realFn = generateName();
            if (!extFn.isEmpty()) {
                try {
                    final byte[] data = Request.Get(attr).execute().returnContent().asBytes();
                    final Path writePath = save(savePath.resolve(realFn + extFn), data);
                    String msg = attr;
                    msgList.add(msg + "\n");
                    log.info(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                log.info("未知的文件类型！" + attr);
            }
        });
        return msgList;
    }
}
