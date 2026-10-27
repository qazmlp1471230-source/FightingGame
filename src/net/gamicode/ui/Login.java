package net.gamicode.ui;

import net.gamicode.domain.User;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;


public class Login {
    public void start() {
        System.out.println("游戏的登录注册页面打开了~");
        //Ctrl + Alt + T:选择对应语句包裹代码

        ArrayList<User> list = new ArrayList<>();//创建集合来存放用户数据

        while (true) {
            System.out.println("-----------------------------------");
            System.out.println("|                                 |");
            System.out.println("|                                 |");
            System.out.println("|        欢迎来到文字格斗游戏！       |");
            System.out.println("|                                 |");
            System.out.println("|                                 |");
            System.out.println("-----------------------------------");
            System.out.println("请选择操作:1注册 2登录 3退出");

            Scanner sc = new Scanner(System.in);
            String choose = sc.next();

            switch (choose) {
                case "1" -> registr(list);
                case "2" -> login(list);
                case "3" -> {
                        System.out.println("用户选择了退出操作~");
                        System.exit(0);
                }
                default -> System.out.println("输入有误,请重新输入~");
            }
        }
    }
    public void registr(ArrayList<User> list) {
        System.out.println("用户选择了注册操作~");
        //创建用户对象
        User u = new User();
        //设置用户对象的属性
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("请输入用户名:");
            String username = sc.next();
            if (!checkLen(3, 16, username)) {//用户名长度符合要求
                System.out.println("用户名长度不符合要求,必须在3-16个字符之间");
                continue;
            }
            if (!checkUsername(username)) {
                System.out.println("用户名只能由字母和数字组成，不能包含其他字符");
                continue;
            }
            if (contains(list, username)) {
                System.out.println("用户名已存在,请重新输入~");
                continue;
            }
            //用户名符合要求
            u.setUsername(username);
            break;
        }
        while (true) {
            System.out.println("请输入密码:");
            String password = sc.next();
            System.out.println("请再次输入密码:");
            String password2 = sc.next();
            if (!password.equals(password2)) {
                System.out.println("两次输入的密码不一致,请重新输入~");
                continue;
            }
            if (!checkLen(3, 8, password)) {
                System.out.println("密码长度不符合要求,必须在3-8个字符之间");
                continue;
            }
            if (!checkPassword(password)) {
                System.out.println("密码格式不符合要求,必须由字母和数字组成，不能包含其他字符");
                continue;
            }
            u.setPassword(password);
            break;
        }
        list.add(u);
        System.out.println("注册成功！");
    }
    public void login(ArrayList<User> list) {
        System.out.println("用户选择了登录操作~");
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名:");
        String username = sc.next();
        if (!contains(list, username)) {
            System.out.println("用户名不存在,请重新输入~");
            return;
        }
        int index = findIndex(list, username);
        User u = list.get(index);
        if (!u.isStatus()) {
            System.out.println("用户已禁用,请联系管理员~");
            return;
        }
        String rightPassword = u.getPassword();
        for (int i = 0; i < 3; i++) {
            System.out.println("请输入密码:");
            String password = sc.next();
            while (true) {
                String rightCode = getCode();
                System.out.println("请输入验证码:" + rightCode);
                System.out.println("请输入验证码:");
                String code = sc.next();
                if (!rightCode.equalsIgnoreCase(code)) {
                    System.out.println("验证码错误,请重新输入~");
                    continue;
                } else {
                    break;
                }
            }
            if (rightPassword.equals(password)) {
                System.out.println("登录成功！欢迎回来," + u.getUsername() + "!");
                FightingGame fg = new FightingGame();
                fg.gameStart(u.getUsername());
                break;
            } else {
                System.out.println("密码错误,请重新输入~");
                if (i == 2) {
                    u.setStatus(false);
                    System.out.println("密码错误次数过多,账号已锁定,请联系管理员~");
                    return;
                } else {
                    System.out.println("密码错误，你还有" + (2 - i) + "次机会");
                }
            }
        }
    }

    public boolean contains(ArrayList<User> list, String username) {
        for (User u : list) {
            //判断用户是否存在
            if (u.getUsername().equals(username)) return true;
        }
        return false;
    }
    public int[] getCount(String userInfo) {
        int charCount = 0, numCount = 0, otherCount = 0;
        for (char c : userInfo.toCharArray()) {
            if (Character.isLetter(c)) charCount++;
            else if (Character.isDigit(c)) numCount++;
            else otherCount++;
        }
        return new int[]{charCount, numCount, otherCount};
    }

    public int findIndex(ArrayList<User> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            User u = list.get(i);//取得对象
            if (u.getUsername().equals(username)) {//对比用户名字存不存在
                return i;//返回索引
            }
        }
        return -1;
    }
    public boolean checkLen(int minlen, int maxlen, String str) {//检查长度
        return str.length() >= minlen && str.length() <= maxlen;
    }

    public boolean checkPassword(String password) {//检查密码格式
        int[] arr = getCount(password);
        return arr[0] > 0 && arr[1] > 0 && arr[2] == 0;//必须由字母和数字组成，不能包含其他字符
    }

    public boolean checkUsername(String username) {//检查用户名格式
        int[] arr = getCount(username);
        return arr[0] > 0 && arr[1] >= 0 && arr[2] == 0;//至少一个字母，数字可以有，也可以没有，其他字符不能有
    }

    /*    public static String getCode() {
        ArrayList<Character> list = new ArrayList<>();//存放所有可能的字符
        for (int i = 0; i < 10; i++) {
            list.add((char) (i + '0'));
        }
        for (int i = 0; i < 26; i++) {
            list.add((char) (i + 'a'));
            list.add((char) (i + 'A'));
        }
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(list.size());//获取一个随机索引
            sb.append(list.get(index));
        }
        System.out.println(sb);
        return sb.toString();
    }*/
    public static String getCode() {
        ArrayList<Character> list = new ArrayList<>();//存放所有可能的字符
        for (int i = 0; i < 26; i++) {
            list.add((char) (i + 'a'));
            list.add((char) (i + 'A'));
        }
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(list.size());//获取一个随机索引
            sb.append(list.get(index));
        }
        sb.append(r.nextInt(10));
        char[] arr = sb.toString().toCharArray();
        int i = r.nextInt(arr.length);//获取一个随机索引
        char temp = arr[i];
        arr[i] = arr[arr.length - 1];
        arr[arr.length - 1] = temp;//随机索引的字符和最后一个字符交换
        String code = new String(arr);
        return code;
    }
}
