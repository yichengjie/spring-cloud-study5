package com.yicj.study.stream;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class FileStreamTest {

    @Test
    public void countWord(){
        long uniqueWords =0 ;
        try (Stream<String> lines= Files.lines(Paths.get("D:\\opt\\test\\AuthSignatureFilter.java"),
                Charset.defaultCharset())){
            uniqueWords = lines.flatMap(line -> {
                System.out.println("===> " + line);
                String[] infos = line.split(" ");
                Stream<String> stream = Arrays.stream(infos);
                return stream ;
            })
            .distinct()
            .count() ;
            System.out.println("count words : " + uniqueWords);
        }catch (IOException e){

        }
    }
}
