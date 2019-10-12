package com.yeyintlwin.musicsstore.ui.fragment.download.child.queue.listener;

import com.yeyintlwin.musicsstore.ui.fragment.download.child.queue.entity.QueueInfo;

public interface OnDownloadBtnClickListener {
    void onDownloadAndResume(QueueInfo queueInfo);

    void onPause(QueueInfo queueInfo);
}
