package com.mirea.app.services;

import com.mirea.app.models.StorageModel;
import com.mirea.app.storage.repositories.BookRepository;
import com.mirea.app.storage.repositories.StorageRepository;
import com.mirea.app.storage.repositories.TelephoneRepository;
import com.mirea.app.storage.repositories.WashingMachineRepository;
import org.springframework.stereotype.Service;

@Service
public class StorageService {
    private final StorageRepository storageRepository;
    private final TelephoneRepository telephoneRepository;
    private final WashingMachineRepository washingMachineRepository;
    private final BookRepository bookRepository;

    public StorageService(StorageRepository storageRepository, TelephoneRepository telephoneRepository, WashingMachineRepository washingMachineRepository, BookRepository bookRepository) {
        this.storageRepository = storageRepository;
        this.telephoneRepository = telephoneRepository;
        this.washingMachineRepository = washingMachineRepository;
        this.bookRepository = bookRepository;
    }


    public void addProduct(StorageModel model) {
        switch (model.productType()) {
            case "TELEPHONE" -> {
                telephoneRepository.getById(model.productId());
            }
            case "BOOK" -> {
                bookRepository.getById(model.productId());
            }
            case "WASHING_MACHINE" -> {
                washingMachineRepository.getById(model.productId());
            }
            default -> {
                return;
            }
        }

        var oldModel = storageRepository.get(model.productType(), model.productId());
        if (oldModel != null) {
            return;
        }
        storageRepository.add(model);
    }
}
