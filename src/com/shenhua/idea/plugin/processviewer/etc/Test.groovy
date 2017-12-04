package com.shenhua.idea.plugin.processviewer.etc

/**
 * Created by shenhua on 2017-11-27-0027.
 *
 * @author shenhua
 *         Email shenhuanet@126.com
 */

String result = "USER      PID   PPID  VSIZE  RSS   WCHAN              PC  NAME\n" +
        "root      1     0     1944   948   SyS_epoll_ 0000000000 S /init\n" +
        "root      2     0     0      0       kthreadd 0000000000 S kthreadd\n" +
        "root      3     2     0      0     smpboot_th 0000000000 S ksoftirqd/0\n" +
        "root      5     2     0      0     worker_thr 0000000000 S kworker/0:0H\n" +
        "root      7     2     0      0     smpboot_th 0000000000 S migration/0\n" +
        "root      8     2     0      0     rcu_gp_kth 0000000000 S rcu_preempt\n" +
        "root      9     2     0      0     rcu_gp_kth 0000000000 S rcu_bh\n" +
        "root      10    2     0      0     rcu_gp_kth 0000000000 S rcu_sched\n" +
        "root      11    2     0      0     smpboot_th 0000000000 S migration/1" +
        "root      4570  2     0      0     worker_thr 0000000000 S kworker/u17:1\n" +
        "system    5008  2491  1558060 34620 SyS_epoll_ 0000000000 S com.huawei.lcagent\n" +
        "u0_a6     5026  2491  1713744 55796 SyS_epoll_ 0000000000 S com.google.android.gms.persistent\n" +
        "u0_a6     5400  2491  1823508 49572 SyS_epoll_ 0000000000 S com.google.android.gms\n" +
        "u0_a10    5880  2492  1682680 25516 SyS_epoll_ 0000000000 S com.android.gallery3d\n" +
        "radio     5897  2491  1548944 28720 SyS_epoll_ 0000000000 S com.huawei.android.ds"

String[] lines = result.split("\n")
String line
String[] pre
for (int i = 1; i < lines.length; i++) {
    if (lines[i].contains(".")) {
        line = lines[i].replaceAll("\\s+", " ")
        pre = line.split(" ")
        println(pre[0])
    }
}