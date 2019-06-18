package ru.home.itbooks.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.Descript;
import ru.home.itbooks.model.form.DescriptForm;
import ru.home.itbooks.repository.DescriptRepository;

@Service
public class DescriptService extends AbstractService<Descript, DescriptForm, DescriptRepository> {
    @Autowired
    public DescriptService(DescriptRepository repository) {
        super(repository);
    }

    @Override
    public Descript save(DescriptForm descriptForm) {
        val desc = Descript.builder()
                .id(descriptForm.getId())
                .text(descriptForm.getText())
                .build();
        return repository.save(desc);
    }
}
