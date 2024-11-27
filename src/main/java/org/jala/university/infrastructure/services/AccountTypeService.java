package org.jala.university.infrastructure.services;

import org.jala.university.domain.entities.AccountType;
import org.jala.university.domain.repository.AccountRepository;
import org.jala.university.domain.repository.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountTypeService extends GenericService<AccountType, UUID> {

    private final AccountTypeRepository accountTypeRepository;

    @Autowired
    public AccountTypeService(AccountTypeRepository accountTypeRepository) {
        super(accountTypeRepository);
        this.accountTypeRepository = accountTypeRepository;
    }

    public AccountType findByTypeName(String typeName) {
        return accountTypeRepository.findByTypeName(typeName);
    }

}
