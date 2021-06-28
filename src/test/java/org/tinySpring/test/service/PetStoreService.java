package org.tinySpring.test.service;

import org.tinySpring.test.dao.AccountDao;
import org.tinySpring.test.dao.ItemDao;

public class PetStoreService {

    private AccountDao accountDao;
    private ItemDao itemDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }
}
