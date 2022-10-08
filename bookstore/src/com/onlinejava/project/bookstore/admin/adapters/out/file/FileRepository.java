package com.onlinejava.project.bookstore.admin.adapters.out.file;

import com.onlinejava.project.bookstore.admin.AdminLauncher;
import com.onlinejava.project.bookstore.core.function.Consumers;
import com.onlinejava.project.bookstore.core.util.reflect.SettableEntity;
import com.onlinejava.project.bookstore.core.util.FileUtils;
import com.onlinejava.project.bookstore.common.domain.entity.Entity;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class FileRepository<T extends Entity> {
    protected List<T> list;

    public abstract void save();


    public void saveEntityToCSVFile(String fileName, Class<T> clazz, boolean hasHeader) {
        try {
            String tempFileName = fileName + ".tmp";
            File tmpFile = new File(tempFileName);
            tmpFile.createNewFile();

            if (hasHeader) {
                Files.writeString(tmpFile.toPath(), Entity.toCsvHeader(clazz) + "\n", StandardOpenOption.APPEND);
            }
            this.list.forEach(Consumers.unchecked((T entity) -> {
                Files.writeString(Path.of(tempFileName), entity.toCsvString() + "\n", StandardOpenOption.APPEND);
            }));

            Files.move(tmpFile.toPath(), Path.of(fileName), StandardCopyOption.REPLACE_EXISTING);
            tmpFile.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<T> getEntityListFromLines(String filePath, Class<T> clazz) {
        List<String> lines = FileUtils.getFileLines(filePath).toList();
        return getEntityListFromLines(lines, clazz, AdminLauncher.HAS_HEADER);
    }

    public List<T> getEntityListFromLines(List<String> lines, Class<T> clazz, boolean hasHeader) {
        return hasHeader
                ? getEntityListFromLinesWithHeader(lines, clazz)
                : getEntityListFromLinesWithoutHeader(lines, clazz);
    }

    private List<T> getEntityListFromLinesWithHeader(List<String> lines, Class<T> clazz) {
        if (lines.size() <= 1) {
            return Collections.emptyList();
        }

        String[] headers = lines.get(0).split(",");
        return lines.stream().skip(1)
                .map(line -> {

                    SettableEntity<T> ObjectSetter = new SettableEntity(clazz);
                    String[] values = Arrays.stream(line.split(",")).map(String::trim).toArray(String[]::new);
                    for (int i = 0; i < headers.length; i++) {
                        ObjectSetter.set(headers[i], values[i]);
                    }

                    return ObjectSetter.getObject();
                })
                .collect(Collectors.toList());
    }

    private List<T> getEntityListFromLinesWithoutHeader(List<String> lines, Class<T> clazz) {
        return lines.stream()
                .map(line -> {
                    SettableEntity<T> ObjectSetter = new SettableEntity(clazz);
                    Field[] fields = clazz.getDeclaredFields();
                    String[] values = Arrays.stream(line.split(",")).map(String::trim).toArray(String[]::new);
                    for (int i = 0; i < fields.length && i < values.length; i++) {
                        ObjectSetter.set(fields[i].getName(), values[i]);
                    }

                    return ObjectSetter.getObject();
                })
                .collect(Collectors.toList());
    }

}
