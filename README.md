# LAB 6 REPORT
---
lab 6进行了非常多细小的改动，如包体结构，输出模式，接下来详细分析

## 1. 包体结构
先前的lab中，对MVC架构的理解有偏差，这个项目其实没有CONTROLLER

现在的包体结构如下：
```
src
└── main
    └── java.io.github.zhaojingyan
         ├── App.java
         ├── model
         │   ├── entities
         │   ├── enums
         │   ├── input
         │   ├── output
         │   └── service
         └── ui
              ├── console
              └── interfaces
```
其中`entities`包用于存放游戏中的实体类，如棋盘、棋子等；`enums`包用于存放游戏中用到的枚举类型，如棋子颜色、游戏状态等；`input`和`output`包存储了交换信息的协议；`service`包用于存放业务逻辑相关的类。`ui`包则用于存放用户界面相关的类，包括控制台和未来的图形界面。
这样的设计减少了包的数量，避免了过多的包嵌套，同时也保持了代码的可读性和可维护性。

## 2. 输出模式
之前的输出模式是在一行中反复切换，现在的模式抽象出屏幕的概念，通过mergeScreen等方法，灵活的安排console的输出
四个屏幕分别是分别被构造，然后merge到一起，再统一display。
```java
public class ConOutput implements OutputInterface {
     private final ScreenBuilder screenBuilder = new ScreenBuilder();
     private final PromptBuilder promptBuilder = new PromptBuilder();
     public ConOutput() {
     }

     @Override
     public void print(OutputInformation output) {
          Screen[] screens = new Screen[4];
          screens[0] = screenBuilder.constructBoardPanel(output);
          screens[1] = screenBuilder.constructPlayerPanel(output);
          screens[2] = screenBuilder.constructGameInfoPanel(output);
          screens[3] = screenBuilder.constructGameListPanel(output);
          screens[0].extend();
          Screen outputScreen = new Screen(0, 0);
          for(Screen item : screens) {
                outputScreen = Screen.mergeScreens(outputScreen, item);
          }
          outputScreen.display();
          System.out.print("\n" + promptBuilder.buildPrompt(output) + " ");
     }

     @Override
     public void printError(GameException e, OutputInformation output) {
          System.err.printf("%s\n\n", e.getMessage());
          System.out.print(promptBuilder.buildPrompt(output) + " ");
     }
}
```

## 3. inputFile
playback模式要求从文件读入命令，将这个部分和现有的输入逻辑结合起来，自由切换。通过回显，让显示效果更好。cmd文件放在jar包的resource目录下
```java
public InputInformation getInput() {
     // 第一步：读取输入
     if(!isReadingFromFile){
          while (!scanner.hasNextLine()) {}
          rawInput = scanner.nextLine();
          if(rawInput.length() >= 8 && rawInput.substring(0,8).toLowerCase().equals("playback")){
                isReadingFromFile = fileReader.openFile(rawInput.substring(9));
          }
     } else {
          try {
                TimeUnit.MILLISECONDS.sleep(1000);
                rawInput = fileReader.getOneRawString();
                System.out.println(rawInput);   //在控制台上显示这个输入
                TimeUnit.MILLISECONDS.sleep(300);
                isReadingFromFile = !fileReader.isEndOfFile();
          } catch(InterruptedException e) {
                System.err.println("Thread sleep err! Exiting...");
                System.exit(1);
          }
     }
     // 第二步：判断输入类型
     InputType infoType = determineType(rawInput);
     // 第三步：根据输入类型创建对应的信息对象
     return InputInformationFactory.create(infoType, rawInput);
}
```

## 4. 项目管理
使用了maven来管理项目，pom.xml中包含了所有的依赖项和插件配置。现在通过run.sh脚本可以直接编译，打包，运行项目。
```bash
mvn clean install
java -jar target/*.jar
```

## 5. uml图表示类关系
```
package "model" {
     package "entities" {
          class Board
          class Piece
          class Player
     }
     package "enums" {
          enum PieceColor
          enum GameStatus
     }
     package "input" {
          class InputInformation
          class InputInformationFactory
          class FileReader
     }
     package "output" {
          class OutputInformation
          class OutputInterface
          class ConOutput
          class Screen
          class ScreenBuilder
          class PromptBuilder
     }
     package "service" {
          class GameService
          class RuleChecker
     }
}

package "ui" {
     package "console" {
          class ConsoleUI
     }
     package "interfaces" {
          interface UIInterface
     }
}

App --> ConsoleUI
ConsoleUI ..|> UIInterface
ConsoleUI --> OutputInterface
ConsoleUI --> InputInformation
ConOutput ..|> OutputInterface
ConOutput --> ScreenBuilder
ConOutput --> PromptBuilder
ScreenBuilder --> Screen
GameService --> Board
GameService --> Player
GameService --> RuleChecker
Board --> Piece
Player --> PieceColor
GameService --> OutputInformation
GameService --> InputInformation
InputInformationFactory --> InputInformation
FileReader --> InputInformation
OutputInformation --> Screen
