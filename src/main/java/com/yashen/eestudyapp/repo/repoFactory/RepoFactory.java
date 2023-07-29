package com.yashen.eestudyapp.repo.repoFactory;

import com.yashen.eestudyapp.repo.SuperRepo;
import com.yashen.eestudyapp.repo.impl.CustomerRepoImpl;

public class RepoFactory {
    private static RepoFactory repoFactory;

    private RepoFactory(){}

    public static RepoFactory getRepoFactory(){
        return repoFactory == null?(repoFactory= new RepoFactory()):repoFactory;
    }
    public <T extends SuperRepo>T getRepo(RepoTypes repoType){
        switch (repoType){
            case CUSTOMER:
                return (T) new CustomerRepoImpl();
            default:
                return null;
        }
    }
}
