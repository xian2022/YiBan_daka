package org.yibanPunchCard.utils;

import com.alibaba.fastjson.JSON;
import org.yibanPunchCard.entity.Student;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class OpUtil {
    public static String randomLocation(){
        Random random = new Random();
        double latitude = ((random.nextInt(11)*100+34348916.0)/1000000);
        double longitude = ((random.nextInt(12)*100+107160322.0)/1000000);
        return longitude+","+latitude;
    }
    public static List<Student> readJsonFile(String filename) {
        String jsonStr;
        try {
            File jsonFile = new File(String.valueOf(Objects.requireNonNull(OpUtil.class.getClassLoader().getResource(filename)).getFile()));
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8);
            int ch = 0;
            StringBuilder sb = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return JSON.parseArray(jsonStr,Student.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
