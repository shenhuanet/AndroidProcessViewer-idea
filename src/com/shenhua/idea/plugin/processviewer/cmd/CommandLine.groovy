package com.shenhua.idea.plugin.processviewer.cmd

/**
 * Created by shenhua on 2017-11-25-0025.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class CommandLine {

    static String executeCommand(String command) {
        println("command:${command}")
        StringBuilder stringBuilder = new StringBuilder()
        try {
            Process process = Runtime.runtime.exec(command)
            process.waitFor()
            InputStreamReader is = new InputStreamReader(process.getInputStream())
            BufferedReader reader = new BufferedReader(is)
            String line
            while ((line = reader.readLine()) != null) {
                stringBuilder.append("${line}\n")
            }
        } catch (InterruptedException e) {
            e.printStackTrace()
            null
        } catch (IOException e) {
            e.printStackTrace()
            null
        }
        stringBuilder.toString()
    }

    static String testExe() {
        StringBuilder stringBuilder = new StringBuilder()
        ProcessBuilder pb = new ProcessBuilder(
                "adb path", "devices", "-l")
        Process process = pb.start()
        process.waitFor()
        InputStreamReader is = new InputStreamReader(process.getInputStream())
        BufferedReader reader = new BufferedReader(is)
        String line
        while ((line = reader.readLine()) != null) {
            stringBuilder.append("${line}\n")
        }
        stringBuilder.toString()
    }
}
