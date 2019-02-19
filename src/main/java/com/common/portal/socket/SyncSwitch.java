package com.common.portal.socket;

/**
 * @description: 同步开关
 * @author: guoyanjun
 * @date: 2018/12/4 9:12
 */
public class SyncSwitch {
    private boolean syncSwitch = false;

    public boolean isSyncSwitch() {
        return syncSwitch;
    }

    public void setSyncSwitch(boolean syncSwitch) {
        this.syncSwitch = syncSwitch;
    }
}
