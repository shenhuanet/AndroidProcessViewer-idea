package com.shenhua.idea.plugin.processviewer.cmd

import com.shenhua.idea.plugin.processviewer.etc.Constans

/**
 * Created by shenhua on 2017-11-25-0025.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class CommandLine {

    static String executeCommand(String... command) {
        println(Constans.TAG + "command:${command}")
        StringBuilder stringBuilder = new StringBuilder()
        try {
            ProcessBuilder pb = new ProcessBuilder(command)
            Process process = pb.start()
            process.waitFor()
            InputStreamReader is = new InputStreamReader(process.getInputStream())
            BufferedReader reader = new BufferedReader(is)
            String line
            while ((line = reader.readLine()) != null) {
                stringBuilder.append("${line}\n")
            }
            stringBuilder.toString().trim()
        } catch (InterruptedException e) {
            e.printStackTrace()
            null
        } catch (IOException e) {
            e.printStackTrace()
            null
        }
    }

    static String executeShellCommand(String command) {
        println(Constans.TAG + "command:${command}")
        StringBuilder stringBuilder = new StringBuilder()
        try {
            Process process = Runtime.getRuntime().exec(command)
            InputStreamReader is = new InputStreamReader(process.getInputStream())
            BufferedReader reader = new BufferedReader(is)
            String line
            while ((line = reader.readLine()) != null) {
                stringBuilder.append("${line}\n")
            }
            stringBuilder.toString().trim()
        } catch (InterruptedException e) {
            e.printStackTrace()
            null
        } catch (IOException e) {
            e.printStackTrace()
            null
        }
    }
}
