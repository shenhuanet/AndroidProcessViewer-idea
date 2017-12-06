package com.shenhua.idea.plugin.processviewer.etc

import com.google.common.eventbus.EventBus

/**
 * Created by shenhua on 2017-12-06-0006.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class BusProvider {

    private static EventBus sBus = new EventBus();

    static EventBus get() {
        sBus
    }

}
