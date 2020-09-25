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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

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

    public List<String> tack(JTextArea textArea, JTextField textInfo) throws IOException {
        final Document doc = Jsoup.connect(entity.getUrl()).get();
        final Elements elements = doc.select(entity.getQuery());
        final List<String> msgList = new ArrayList<>();
        if (elements.size() == 0) {
            JOptionPane.showMessageDialog(null, "未查找到任何资源！");
            return msgList;
        }
        textInfo.setText(String.valueOf(elements.size()));
        String currDate = LocalDate.now().toString();
        Path savePath = entity.getSavePath() != null ? entity.getSavePath().resolve(defaultSaveDir).resolve(currDate) : Paths.get(defaultSaveDir).resolve(currDate);
        if (!Files.exists(savePath))
            Files.createDirectories(savePath);
        textArea.append("to path: " + savePath.toAbsolutePath() + newLine);
        final ExecutorService pool = Executors.newFixedThreadPool(50);
        elements.forEach(el -> {
            String attr = el.attr(entity.getAttr());
            textArea.append(el.toString() + newLine);
            if (!attr.startsWith(Protocol.http.get()) && !attr.startsWith(Protocol.https.get())) {
                String host;
                try {
                    host = getHost(entity.getUrl());
                } catch (MalformedURLException e) {
                    throw new IllegalStateException(e);
                }
                attr = attr.startsWith(separator) ? host + attr : host + separator + attr;
            }
            textArea.append(attr + newLine);
            String fn = attr.lastIndexOf(separator) > 0 ? attr.substring(attr.lastIndexOf(separator)) : "";
            String extFn = fn.lastIndexOf(dot) > 0 ? fn.substring(fn.lastIndexOf(dot)) : "";
            String realFn = generateName();
            if (!extFn.isEmpty()) {
                AtomicReference<String> atomicAttr = new AtomicReference<>(attr);
                Future<?> future = pool.submit(() -> {
                    try {
                        final byte[] data = Request.Get(atomicAttr.get()).execute().returnContent().asBytes();
                        final Path writePath = save(savePath.resolve(realFn + extFn), data);
                        msgList.add(atomicAttr.get() + newLine);
                        textArea.append(atomicAttr + newLine);
                        log.info(atomicAttr.get());
                    } catch (IOException e) {
                        textArea.append(e.toString());
                    } catch (Exception e) {
                        textArea.append(e.toString());
                    }
                });
            } else {
                String msg = "未知的类型！" + attr;
                log.info(msg);
                textArea.append(msg);
            }
        });
        pool.shutdown();
        try {
            pool.awaitTermination(-1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "资源抓取完成！");
        return msgList;
    }
}
