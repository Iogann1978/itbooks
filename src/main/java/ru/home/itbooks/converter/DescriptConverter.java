package ru.home.itbooks.converter;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.home.itbooks.model.Descript;
import ru.home.itbooks.service.DescriptService;

@Component
@Slf4j
public class DescriptConverter implements Converter<String, Descript> {
    private DescriptService descriptService;

    @Override
    @SneakyThrows
    public Descript convert(String id) {
        log.debug("DescriptConverter");
        Descript desc = null;
        if(id != null && !id.isEmpty()) {
            desc = descriptService.findById(Long.valueOf(id))
                    .orElseThrow(() -> new Exception(String.format("Описание %s не найдено!", id)));
        }
        return desc;
    }

    @Autowired
    public void setDescriptService(DescriptService descriptService) {
        this.descriptService = descriptService;
    }
}
