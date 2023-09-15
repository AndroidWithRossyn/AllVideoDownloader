package com.coco.m3u8lib;

import java.util.ArrayList;
import java.util.List;

import com.coco.m3u8lib.bean.M3U8Task;

class DownloadQueue {

    private List<M3U8Task> queue;

    public DownloadQueue(){
        queue = new ArrayList<>();
    }

    public void offer(M3U8Task task){
        queue.add(task);
    }

    public M3U8Task poll(){
        try {
            if (queue.size() >= 2){
                queue.remove(0);
                return queue.get(0);
            }else if (queue.size() == 1){
                queue.remove(0);
            }
        }catch (Exception e){
        }
        return null;
    }

    public M3U8Task peek(){
        try {
            if (queue.size() >= 1){
                return queue.get(0);
            }
        }catch (Exception e){
        }
        return null;
    }

    public boolean remove(M3U8Task task){
        if (contains(task)){
            return queue.remove(task);
        }
        return false;
    }

    public boolean contains(M3U8Task task){
        return queue.contains(task);
    }

    public M3U8Task getTask(String url){
        try {
            for (int i = 0; i < queue.size(); i++){
                if (queue.get(i).getUrl().equals(url)){
                    return queue.get(i);
                }
            }
        }catch (Exception e){
        }

        return null;
    }

    public boolean isEmpty(){
        return size() == 0;
    }

    public int size(){
        return queue.size();
    }

    public boolean isHead(String url){
        return isHead(new M3U8Task(url));
    }

    public boolean isHead(M3U8Task task){
        return task.equals(peek());
    }
}
