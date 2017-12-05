package com.shenhua.idea.plugin.processviewer.etc

/**
 * Created by shenhua on 2017-11-20-0020.
 * @author shenhua
 *         Email shenhuanet@126.com
 */
class Constans {

    static final String WINDOW_ID = "ProcessViewer"
    static final String TAG = "ProcessViewer:"

    /**
     * LEFT
     */
    public static final String LEFT_GROUP_ID = "ProcessViewer.LeftGroup"
    public static final String LEFT_TOOLBAR_ID = "ProcessViewer.LeftToolbar"
    public static final String ACTION_ID_REFRESH = "ProcessViewer.RefreshAction"
    public static final String ACTION_ID_STOP = "ProcessViewer.StopAction"
    public static final String ACTION_ID_CLEAN = "ProcessViewer.CleanAction"
    public static final String ACTION_ID_SETTINGS = "ProcessViewer.SettingsAction"
    public static final String ACTION_ID_HELP = "ProcessViewer.HelpAction"
    /**
     * TOP
     */
    static final String TOP_GROUP_ID = "ProcessViewer.TopGroup"
    static final String TOP_TOOLBAR_ID = "ProcessViewer.TopToolbar"
    /**
     * Table
     */
    public static final String[] COLUMN_NAME = ['user', 'pid', 'ppid', 'vSize', 'rss', 'wChan', 'name']
    public static final int COLUMN_WIDTH = 150
}
