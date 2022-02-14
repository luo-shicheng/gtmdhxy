package com.lsc.test.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.omg.CORBA.MARSHAL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ScriptUtil {

    private static final Logger logger = LoggerFactory.getLogger(ScriptUtil.class);

    public static final SimpleDateFormat HOUR_MIN_SEC_MILLSEC = new SimpleDateFormat("HH:mm:ss SSS");

    public static final SimpleDateFormat YEAR_MONTH_DAY_HOUR_MIN_SEC_MILLSEC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
    /**
     * 脚本执行，日志文件实时输出
     *
     * @param command    命令行
     * @param scriptFile 脚本文件
     * @param params     脚本参数
     */
    public static int exec(String command, String scriptFile, String... params) {
        List<String> cmdarray = new ArrayList<>();
        cmdarray.add(command);
        cmdarray.add(scriptFile);
        if (params != null && params.length > 0) {
            Collections.addAll(cmdarray, params);
        }
        String[] cmdArrayFinal = cmdarray.toArray(new String[0]);
        // process-exec
        return exec(cmdArrayFinal);
    }

    /**
     * 脚本执行，日志文件实时输出
     *
     * @param command 完整命令行，只能是String或String[]或List<String>
     */
    @SuppressWarnings("unchecked")
    public static int exec(Object command) {
        Thread inputThread = null;
        Thread errThread = null;
        Process process;
        try {
            if (command instanceof String) {
                process = Runtime.getRuntime().exec((String) command);
            } else if (command instanceof String[]) {
                process = Runtime.getRuntime().exec((String[]) command);
            } else if (command instanceof List) {
                ProcessBuilder pb = new ProcessBuilder();
                pb.command((List<String>) command);
                process = pb.start();
            } else {
                logger.warn("当前命令类型 {} 格式不支持", command.getClass().getName());
                return -1;
            }
        } catch (Exception e) {
            logger.warn("生成脚本命令异常", e);
            return -1;
        }
        long start = System.currentTimeMillis();
        try {
            inputThread = new Thread(() -> {
                try (InputStream is = process.getInputStream()) {
                    log(is);
                } catch (Exception e) {
                    logger.warn("打印输入流信息失败");
                }
            });
            errThread = new Thread(() -> {
                try (InputStream is = process.getErrorStream()) {
                    log(is);
                } catch (Exception e) {
                    logger.warn("打印错误流信息失败");
                }
            });
            final Thread thread1 = inputThread;
            final Thread thread2 = errThread;
            Thread daemon = new Thread(() -> {
                while (System.currentTimeMillis() - start < 5 * 1000) {
                    try{
                        Thread.sleep(5000);
                    }catch (Exception e){

                    }

                }
                logger.warn("命令 {} 超时，强行干掉当前命令", command);
                kill(process, thread1, thread2);
            });
            inputThread.start();
            errThread.start();
            daemon.setDaemon(true);
            daemon.start();
            return process.waitFor();
        } catch (Exception e) {
            logger.warn("执行脚本命令异常", e);
            return -1;
        } finally {
            kill(process, inputThread, errThread);
        }

    }

    private static void log(InputStream is) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String line;
        while ((line = in.readLine()) != null) {
            logger.info(line);
        }
        in.close();
    }

    private static void kill(Process process, Thread inputThread, Thread errThread) {
        if (inputThread != null && inputThread.isAlive()) {
            inputThread.interrupt();
        }
        if (errThread != null && errThread.isAlive()) {
            errThread.interrupt();
        }
        if (process.isAlive()) {
            process.destroy();
        }

    }

    public static void main(String[] args) throws Exception{
        ObjectMapper o =new ObjectMapper();


    }
}
