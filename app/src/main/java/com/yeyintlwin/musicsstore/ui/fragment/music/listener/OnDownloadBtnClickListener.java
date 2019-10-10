package com.yeyintlwin.musicsstore.ui.fragment.music.listener;

import com.yeyintlwin.musicsstore.ui.fragment.music.entity.MusicInfo;

public interface OnDownloadBtnClickListener {
    void onDownloadAndResume(MusicInfo musicInfo);

    void onPause(MusicInfo musicInfo);
}
