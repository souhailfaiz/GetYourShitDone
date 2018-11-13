package com.academy.youngcapital.getyourshitdone.util;

import com.academy.youngcapital.getyourshitdone.model.Task;

import java.util.ArrayList;
import java.util.List;

public class ListSorter {
    public static void sendToBottom(List<Task> list, int index){
        Task task = list.get(index);
        list.remove(index);
        list.add(task);
    }

    public static void senToTop(List<Task> list, int index) {
        Task task = list.get(index);
        list.remove(index);
        list.add(0, task);
    }
}
