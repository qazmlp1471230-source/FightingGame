package net.gamicode.domain;

import java.util.Random;

public class User {
    private String id;
    private String username;
    private String password;
    private boolean status;

    public User() {
        this.id = createID();
        this.status = true;//默认状态为true
    }

    public User(String id, String username, String password, boolean status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = true;//默认状态为true
    }

    public String createID() {//生成ID
        StringBuilder sb = new StringBuilder("庆源");//格式庆源+5位随机数

        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            int num = r.nextInt(10);//生成5个0-9的随机数拼接到庆源后面
            sb.append(num);//附加内容
        }

        return sb.toString();//StringBuilder和String不是一个类型，需要转换
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }//boolean类型的方法不是get!!!

    public void setStatus(boolean status) {
        this.status = status;
    }
}
