package ru.home.itbooks.converter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import ru.home.itbooks.service.ItemService;

@Slf4j
public abstract class AbstractStringIdConverter<T0, T1 extends ItemService<T0>>
        implements ItemStringIdConverter<T0>, Converter<String, T0> {
    private T1 service;
    private String className, itemName;

    public AbstractStringIdConverter(T1 service, String className, String  itemName) {
        this.service = service;
        this.className = className;
        this.itemName = itemName;
    }

    @Override
    @SneakyThrows
    public T0 convert(String s) {
        log.debug("{}: {}", className, s);
        T0 item = null;
        if(s != null && !s.isEmpty()) {
            if(s.matches("\\d+")) {
                item = service.findById(Long.valueOf(s))
                        .orElseThrow(() -> new Exception(String.format("%s %s не найден!", itemName, s)));
            } else {
                item = getNewItem();
            }
        }
        return item;
    }
}
