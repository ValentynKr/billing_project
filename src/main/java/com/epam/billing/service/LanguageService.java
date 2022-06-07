package com.epam.billing.service;

import com.epam.billing.entity.Language;
import com.epam.billing.repository.LanguageRepository;

import java.util.List;

public class LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public List<Language> getAll() {
        return languageRepository.getAll();
    }

    public void save(Language language) {
        languageRepository.save(language);
    }

    public boolean delete(Language language) {
        return languageRepository.delete(language);
    }

    public boolean existById(long id) {
        return languageRepository.existById(id);
    }

    public Language getById(long id) { return languageRepository.getById(id); }

    public Language getByShortName(String name) { return languageRepository.getByShortName(name); }

    public Language update(Language language) {
        return languageRepository.update(language);
    }
}
