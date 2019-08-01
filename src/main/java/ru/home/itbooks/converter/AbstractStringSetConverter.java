package ru.home.itbooks.converter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.convert.converter.Converter;
import ru.home.itbooks.service.ItemService;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public abstract class AbstractStringSetConverter <T0, T1 extends ItemService<T0>>
        implements ItemStringIdConverter<T0>, Converter<String, Set<T0>> {
    private T1 service;
    private String itemName;

    public AbstractStringSetConverter(T1 service, String  itemName) {
        this.service = service;
        this.itemName = itemName;
    }

    @Override
    @SneakyThrows
    public Set<T0> convert(String s) {
        log.debug("{}: {}", getClass().getSimpleName(), s);
        Set<T0> list = new HashSet<>();
        if(s != null && !s.isEmpty()) {
            val items = s.split(",");
            for(val item : items) {
                list.add(getNewItem(item));
            }
        }
        return list;
    }

    protected T1 getService() {
        return service;
    }
}
