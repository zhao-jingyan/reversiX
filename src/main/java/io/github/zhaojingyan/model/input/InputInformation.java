package io.github.zhaojingyan.model.input;

import io.github.zhaojingyan.model.enums.InputType;

public interface InputInformation {
    // 获取输入类型
    InputType getInputType();
    // 获取信息
    Object getInfo();
}
