package com.shenhua.idea.plugin.processviewer.cmd

/**
 * Created by shenhua on 2017-11-25-0025.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class CommandLine {

    String executeCommand(String command) {
        System.out.println("command: " + command);
        Process process;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
            InputStreamReader is = new InputStreamReader(process.getInputStream());
            BufferedReader reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
