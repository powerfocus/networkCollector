package org.py.networkCollector.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.file.Path;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Remote {
    private String url;
    private String query;
    private String attr;
    private Path savePath;
}
