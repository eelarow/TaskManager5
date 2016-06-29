package com.emil.taskmanager.data;

/**
 * Created by HSchool on 23/06/2016.
 */
public class myTask {
    private String taskKey;
    private String  text;
    private boolean isCompleted = false;
    private int prio;
    private String location;
    private String phone;

    public myTask(String text, boolean isCompleted, int prio, String location, String phone) {
        this.text = text;
        this.isCompleted = isCompleted;
        this.prio = prio;
        this.location = location;
        this.phone = phone;
    }

    public myTask() {
    }

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public int getPrio() {
        return prio;
    }

    public void setPrio(int prio) {
        this.prio = prio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "myTask{" +
                "text='" + text + '\'' +
                ", isCompleted=" + isCompleted +
                ", prio=" + prio +
                ", location='" + location + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }


}
