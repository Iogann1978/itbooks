package ru.home.itbooks.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.home.itbooks.model.Descript;
import ru.home.itbooks.service.DescriptService;

@Component
public class DescriptConverter extends AbstractStringIdConverter<Descript, DescriptService> {
    @Autowired
    public DescriptConverter(DescriptService descriptService) {
        super(descriptService, "Описание");
    }

    @Override
    public Descript getNewItem(String s) {
        return null;
    }
}
