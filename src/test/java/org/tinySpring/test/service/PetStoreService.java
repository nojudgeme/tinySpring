package org.tinySpring.test.service;

import org.tinySpring.test.dao.AccountDao;
import org.tinySpring.test.dao.ItemDao;

public class PetStoreService {

    private AccountDao accountDao;
    private ItemDao itemDao;
    private int version;
    private int years;
    private String name;
    private String owner;

    public PetStoreService() {
    }

    public PetStoreService(AccountDao accountDao, ItemDao itemDao) {
        this.accountDao = accountDao;
        this.itemDao = itemDao;
    }

    public PetStoreService(AccountDao accountDao, ItemDao itemDao, int version) {
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.version = version;
    }

    public PetStoreService(AccountDao accountDao, ItemDao itemDao, String  version) {
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.version = Integer.parseInt(version);
    }

    public PetStoreService(AccountDao accountDao, ItemDao itemDao, int version, int years, String name, String owner) {
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.version = version;
        this.years = years;
        this.name = name;
        this.owner = owner;
    }

    public PetStoreService(int years, String name, String owner,AccountDao accountDao, ItemDao itemDao, int version) {
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.version = version;
        this.years = years;
        this.name = name;
        this.owner = owner;
    }

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
