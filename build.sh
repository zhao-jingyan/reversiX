#!/bin/zsh
# 自动构建并清理，只保留 fat jar 并重命名
set -e

JAR_NAME="reversi"
FAT_JAR="target/${JAR_NAME}-jar-with-dependencies.jar"
FINAL_JAR="target/${JAR_NAME}.jar"

# 1. 构建
mvn clean package

# 2. 检查 fat jar 是否生成
if [ ! -f "$FAT_JAR" ]; then
  echo "[错误] 未找到 fat jar: $FAT_JAR"
  exit 1
fi

# 3. 删除所有普通 jar（不带 dependencies 的 jar）
find target -maxdepth 1 -type f -name "${JAR_NAME}.jar" -exec rm -f {} +

# 4. 重命名 fat jar
mv "$FAT_JAR" "$FINAL_JAR"

# 5. 删除所有残留的 .game 文件
find . -type f -name "*.game" -exec rm -f {} +

echo "[完成] 可运行 jar: $FINAL_JAR"
