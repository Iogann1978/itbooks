package ru.home.itbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.Descript;
import ru.home.itbooks.repository.DescriptRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DescriptService extends AbstractService<Descript, DescriptRepository> {
    @Autowired
    public DescriptService(DescriptRepository repository) {
        super(repository);
    }

    public List<Descript> findByHtml(String html) {
        return getRepository().findAll().stream()
                .filter(d -> (d.getHtml() != null && !d.getHtml().isEmpty() && d.getHtml().contains(html)))
                .collect(Collectors.toList());
    }

    @Override
    protected Sort getSort() {
        return null;
    }
}
