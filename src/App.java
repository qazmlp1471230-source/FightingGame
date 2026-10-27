import net.gamicode.ui.FightingGame;
import net.gamicode.ui.Login;

public class App {
    public static void main(String[] args) {
        //负责启动程序，不写业务逻辑
        //启动登录注册页面
        Login l = new Login();
        l.start();

        FightingGame fg = new FightingGame();
    }
}

