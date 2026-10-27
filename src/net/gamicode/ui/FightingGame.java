package net.gamicode.ui;

import net.gamicode.domain.Character;
import net.gamicode.domain.EnemyCharacter;
import net.gamicode.domain.HeroCharacter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FightingGame {
    public void gameStart(String username) {
        System.out.println("-----------------------------------");
        System.out.println("|                                 |");
        System.out.println("|                                 |");
        System.out.println("|      " + username + " 欢迎回来             |");
        System.out.println("|                                 |");
        System.out.println("|                                 |");
        System.out.println("-----------------------------------");

        HeroCharacter player = createPlayerCharacter(username);
        System.out.println("您的角色创建成功！");
        System.out.println("您的角色信息如下：");

        System.out.println("角色名：" + player.getName());
        System.out.println("生命值：" + player.getHP());
        System.out.println("攻击力：" + player.getAttack());
        System.out.println("防御力：" + player.getDefense());
        displaySkills(player);

        ArrayList<EnemyCharacter> enemies = new ArrayList<>();
        enemies.add(new EnemyCharacter("初级战士", 80, 15, 10, "猛击"));
        enemies.add(new EnemyCharacter("敏捷刺客", 60, 20, 5, "快速攻击"));
        enemies.add(new EnemyCharacter("重装坦克", 120, 10, 20, "防御姿态"));
        enemies.add(new EnemyCharacter("神秘法师", 70, 25, 8, "火球术"));

        int count = 1;// 记录轮数
        int wins = 0;// 记录胜利场数
        // 游戏主循环,角色死亡则结束游戏
        while (player.isAlive()) {//角色死亡则结束游戏
            if (wins != 0) {
                for (int i = 0; i < enemies.size(); i++) {
                    EnemyCharacter c = enemies.get(i);
                    c.setMaxHP(c.getMaxHP() + 10);
                    c.setHP(c.getMaxHP());
                    c.setAttack(c.getAttack() + 3);
                    c.setDefense(c.getDefense() + 2);
                    c.setDefending(false);
                }
            }
            System.out.println("敌人信息如下：");
            Random r = new Random();
            int randomIndex = r.nextInt(enemies.size());//生成一个随机索引
            EnemyCharacter enemy = enemies.get(randomIndex);//获取随机敌人
            //展示敌人信息
            enemy.displayInfo();
            //战斗开始
            System.out.println("--------------------------");
            System.out.println("第" + count + "轮战斗开始！ 对手：" + enemy.getName());
            int round = 1;// 记录回合
            while (player.isAlive() && enemy.isAlive()) {//角色和敌人均存活则继续战斗
                System.out.println("--------------------------");
                System.out.println("第" + round + "回合战斗进行中！");
                //打印敌我双方的血条
                System.out.println(getHealthBar(player.getName(), player.getHP(), player.getMaxHP()));
                System.out.println(getHealthBar(enemy.getName(), enemy.getHP(), enemy.getMaxHP()));
                //玩家回合
                playerTurn(player, enemy);
                //判断敌人是否死亡
                if (!enemy.isAlive()) {
                    System.out.println("你过关！");
                    wins++;
                    break;
                }
                //敌人回合,一半概率使用普通攻击，一半概率使用技能
                enemyTurn(enemy, player);
                //判断玩家角色是否死亡
                if (!player.isAlive()) {
                    System.out.println("您被击败了，游戏结束！");
                    break;
                }
                round++;//如果双方均存活，则回合数加一
            }
            //胜利后玩家则随机恢复20-40血量
            if (player.isAlive()) {
                Random r1 = new Random();
                int healHP = r1.nextInt(21) + 20;
                player.heal(healHP);
                System.out.println("您恢复了" + healHP + "HP");
                System.out.println("您胜利了" + wins + "场战斗!");
                System.out.println("-------------------------------------------");
            }
            //每胜利三场，玩家角色属性获得提升
            if (player.isAlive() && wins % 3 == 0) {
                System.out.println("您胜利了" + wins + "场战斗，角色属性获得提升！");
                //提升最大生命值
                player.setMaxHP(player.getMaxHP() + 30);
                //提升攻击力
                player.setAttack(player.getAttack() + 5);
                //提升防御力
                player.setDefense(player.getDefense() + 3);
                System.out.println("最大生命值提升30，攻击力提升5，防御力提升3");
                System.out.println("当前属性：" + player.getName());
                System.out.println("最大生命值：" + player.getMaxHP());
                System.out.println("攻击力：" + player.getAttack());
                System.out.println("防御力：" + player.getDefense());
            }
            if (player.isAlive()) {
                System.out.println("是否继续游戏？(y/n)");
                Scanner sc = new Scanner(System.in);
                String choice = sc.next();
                if (choice.equalsIgnoreCase("n")) {
                    break;
                } else {
                    System.out.println("继续游戏！");
                }
                count++;
                continue;//其实可以省略，因为不写依旧会继续循环
            }
        }
        //游戏结束,结算
        System.out.println("=========================================");
        System.out.println("游戏结束，感谢游玩！");
        System.out.println("您的最终胜利场数为：" + wins);
        System.exit(0);
    }
    //定义一个方法，展示敌我血条
    public String getHealthBar(String name, int HP, int maxHP) {
        int barLength = 20;
        int filled = (int) (HP * 1.0 / maxHP * barLength);
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(": [");
        for (int i = 0; i < barLength; i++) {
            if (i < filled) {
                sb.append("■");
            } else {
                sb.append(" ");
            }
        }
        sb.append("]").append(HP).append("/").append(maxHP).append("HP");
        return sb.toString();
    }
    //定义一个方法，执行玩家回合
    public void playerTurn(HeroCharacter player, EnemyCharacter enemy) {
        System.out.println("======玩家回合======");
        System.out.println("1.普通攻击");
        System.out.println("2.强力一击");
        System.out.println("3.生命汲取");
        System.out.println("选择行动(1-3):");
        Scanner sc = new Scanner(System.in);
        String choice = sc.next();
        switch (choice) {
            default : System.out.println("无效的选择，默认普通攻击");//如果不是1,2,3则触发case穿透
            case "1" : {
                System.out.println("您使用了普通攻击");
                System.out.println("您对敌人造成了" + calculateDamage(player.getAttack(), enemy.getDefense()) + "点伤害");
                enemy.takeDamage(calculateDamage(player.getAttack(), enemy.getDefense()));
                break;
            }
            case "2" : {
                if (player.getHP() > 10) {
                    System.out.println("您使用了强力一击");
                    player.takeDamage(10);
                    System.out.println("消耗10HP，您对敌人造成了" + calculateDamage((int) (player.getAttack() * 1.8), enemy.getDefense()) + "点伤害");
                    enemy.takeDamage(calculateDamage((int) (player.getAttack() * 1.8), enemy.getDefense()));
                } else {
                    System.out.println("体力不足，攻击失败");
                }
                break;
            }
            case "3" : {
                if (player.getHP() > 10) {
                    System.out.println("您使用了生命汲取");
                    Random r = new Random();
                    r.nextInt();
                    int healHP = r.nextInt(31);
                    player.heal(healHP);
                    System.out.println("您恢复了" + healHP + "HP");
                    System.out.println("消耗10HP，您恢复了" + healHP + "HP");
                    break;
                }
            }
        }
    }
    //定义一个方法，创建玩家角色
    public HeroCharacter createPlayerCharacter(String username) {
        System.out.println("创建您的角色");
        System.out.println("您的角色名为：" + username);
        int points = 20;
        System.out.println("您有" + points + "点属性点，可以分配给角色");
        System.out.println("1.生命值（每点+10）");
        System.out.println("2.攻击力（每点+2）");
        System.out.println("3.防御力（每点+1）");
        Scanner sc = new Scanner(System.in);
        String[] attributes = {"生命值", "攻击力", "防御力"};
        int[] value = new int[3];
        for (int i = 0; i < attributes.length; i++) {
            System.out.println("请输入" + attributes[i] + "的点数：(剩余" + points + "点)");
            int attributeValue = sc.nextInt();
            if (attributeValue < 0) {
                System.out.println("属性点不能为负数,默认分配0点");
                attributeValue = 0;
            }
            if (attributeValue > points) {
                System.out.println("属性点不足！剩余属性点全部分配到" + attributes[i]);
                attributeValue = points;
            }
            points -= attributeValue;
            value[i] = attributeValue;//分配给三个属性的属性点
        }
        HeroCharacter player = new HeroCharacter(
                username,
                100 + value[0] * 10,
                10 + value[1] * 2,
                0 + value[2] * 1
        );
        player.skills.add("1.普通攻击");
        player.skills.add("2.强力一击");
        player.skills.add("3.生命汲取");

        return player;
    }
    //遍历技能
    public void displaySkills(HeroCharacter player) {
        System.out.println("您的技能如下：");
        for (String skill : player.skills) {//增强for循环遍历技能
            System.out.println(skill);
        }
    }
    //定义一个方法，计算伤害
    public int calculateDamage(int attack, int defense) {
        return attack - defense > 0 ? attack - defense : 1;
    }
    //定义一个方法，敌人回合
    public void enemyTurn(EnemyCharacter enemy, HeroCharacter player) {
        System.out.println("======敌人回合======");
        String action = "普通攻击";//敌人的攻击手段，默认是普通攻击
        Random r = new Random();
        int num = r.nextInt(10);//如果小于5则使用普通攻击，否则使用技能
        if (num >= 5) {
            action = enemy.getSkills();
        }
        switch (action) {
            case "普通攻击" : {
                System.out.println("敌人使用了普通攻击");
                int damage = calculateDamage(enemy.getAttack(), player.getDefense());
                System.out.println("您受到了" + damage + "点伤害");
                player.takeDamage(damage);
                break;
            }
            case "猛击" : {
                System.out.println("敌人使用了猛击");
                int damage = calculateDamage((int)(enemy.getAttack() * 1.5), player.getDefense());
                System.out.println("您受到了" + damage + "点伤害");
                player.takeDamage(damage);
                break;
            }
            case "快速攻击" : {
                System.out.println("敌人使用了快速攻击");
                int damage3 = 0;
                for (int i = 0; i < 2; i++) {
                    int damage2 = calculateDamage(enemy.getAttack() / 2, player.getDefense());
                    damage3 += damage2;
                };
                System.out.println("您受到了" + damage3 + "点伤害");
                player.takeDamage(damage3);
                break;
            }
            case "防御姿态" : {
                System.out.println("敌人使用了防御姿态");
                enemy.setDefending(true);
                break;
            }
            default: {
                System.out.println("敌人使用了火球术");
                int damage = calculateDamage((int)(enemy.getAttack() * 1.8), player.getDefense());
                System.out.println("您受到了" + damage + "点伤害");
                player.takeDamage(damage);
                break;
            }
        }
    }
}
