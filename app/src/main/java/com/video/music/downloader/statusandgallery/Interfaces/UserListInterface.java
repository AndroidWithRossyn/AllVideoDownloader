package com.video.music.downloader.statusandgallery.Interfaces;


import com.video.music.downloader.statusandgallery.model.downloader.FBStoryModel.NodeModel;
import com.video.music.downloader.statusandgallery.model.downloader.story.TrayModel;

public interface UserListInterface {
    void userListClick(int position, TrayModel trayModel);
    void fbUserListClick(int position, NodeModel trayModel);
}
