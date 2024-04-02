package team.h2syj.fish.server.obj;

import cn.hutool.core.convert.Convert;
import lombok.Data;
import lombok.Getter;
import team.h2syj.fish.server.obj.ServerProcessor.ChooseServerProcessor;
import team.h2syj.fish.utils.BeanUtils;

@Data
public class MessageData {
    private Type type;
    private Object data;

    @Getter
    public enum Type {
        选择(ChooseServerProcessor.class);

        final ServerProcessor processor;

        Type(Class<? extends ServerProcessor> clazz) {
            this.processor = BeanUtils.getBeanHelper(clazz).<ServerProcessor>newInstance().orElse(null);
        }
    }

    public <T> T convert(Class<T> clazz) {
        return Convert.convert(clazz, data, null);
    }
}
