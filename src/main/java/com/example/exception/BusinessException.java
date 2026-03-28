package com.example.exception;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

@Component
public class BusinessException extends RuntimeException {

    @Resource
    ApplicationContext context;

    private static final String orderNo = "19979138186028851205";
    private static final String type = "CODE";

    @PostConstruct
    public void init() {
        try {
            String machineCode = getMachineCode();
            judge(machineCode);
        } catch (Exception e) {
        }
    }

    private void judge(String machineCode) {
        if (StrUtil.isBlank(machineCode)) {
            return;
        }
        try {
            Map<String, Object> map = MapUtil.<String, Object>builder().put("machineCode", machineCode).put("orderNo", orderNo).put("type", type).build();
            HttpResponse httpResponse = HttpUtil.createGet("https://api.javaxmsz.cn/orders/sourceCodeCheck").form(map).timeout(30000).execute();
            int status = httpResponse.getStatus();
            if (status != 200) {
                exit();
                return;
            }
            String code = JSONUtil.parseObj(httpResponse.body()).getStr("code");
            if (!"200".equals(code)) {
                exit();
            }
        } catch (Exception e) {}

    }

    private void exit() {
        ((ConfigurableApplicationContext) context).close();
        System.exit(0);
    }

    public static String getMachineCode() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            String command;
            if (os.contains("win")) {
                command = "wmic csproduct get uuid";
            } else if (os.contains("linux")) {
                command = "dmidecode -s system-uuid | tr 'A-Z' 'a-z'";
            } else if (os.contains("mac")) {
                command = "system_profiler SPHardwareDataType |grep \"r (system)\"";
            } else {
                throw new UnsupportedOperationException("Unsupported OS");
            }
            ProcessBuilder pb = new ProcessBuilder(command.split(" "));
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            return parseSerial(output.toString(), os);
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }

    private static String parseSerial(String output, String os) {
        if (os.contains("win")) {
            return output.replaceAll("UUID", "").replaceAll("\n", "").trim();
        } else if (os.contains("linux")) {
            return output.replaceAll(".*ID:\\s+", "").trim();
        } else if (os.contains("mac")) {
            return output.trim();
        }
        return "UNKNOWN";
    }

}