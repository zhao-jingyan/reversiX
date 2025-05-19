package io.github.zhaojingyan;

// 临时注释掉未使用的导入以便测试
// import io.github.zhaojingyan.model.service.GameManager;
import io.github.zhaojingyan.ui.gui.ReversiGUI;

/**
 * JavaFX应用程序主入口，启动黑白棋GUI界面
 */
public class MainFX {
    /**
     * 程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 临时移除加载存档功能以便测试
        // String savePath = System.getProperty("user.dir") + java.io.File.separator + "pj.game";
        // GameManager.getInstance().load(savePath);
        
        // 启动GUI
        ReversiGUI.main(args);
        
        // 临时移除保存功能以便测试
        // Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        //     GameManager.getInstance().save(savePath);
        // }));
    }
}
