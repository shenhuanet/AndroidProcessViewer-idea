package com.shenhua.idea.plugin.processviewer.callback

import com.shenhua.idea.plugin.processviewer.bean.Process

/**
 * Created by shenhua on 2017/11/27.
 * Email shenhuanet@126.com
 *
 * @author shenhua
 */
interface OnKillProcessCallback {

    void onState(int state)

}