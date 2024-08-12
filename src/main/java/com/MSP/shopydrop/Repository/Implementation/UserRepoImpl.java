package com.MSP.shopydrop.Repository.Implementation;

import com.MSP.shopydrop.Entity.Users;
import com.MSP.shopydrop.Repository.DefaultProcedureRepo;
import com.MSP.shopydrop.Repository.UsersRepo;
import jakarta.persistence.Id;

import java.util.List;
import java.util.Optional;

public class UserRepoImpl implements UsersRepo {

    private final DefaultProcedureRepo defaultProcedureRepo;

    public UserRepoImpl(DefaultProcedureRepo defaultProcedureRepo) {
        this.defaultProcedureRepo = defaultProcedureRepo;
    }

    @Override
    public Optional<Users> findById(Long id) {
        List<Users> usersList = defaultProcedureRepo.getWithType("authentication.cfn_get_users", new Object[][] {
            {Long.class, id, "p_id"},
        }, Users.class);
        if (usersList.isEmpty()){
            return Optional.empty();
        }else{
            return Optional.of(usersList.get(0));
        }
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        List<Users>usersEmail =defaultProcedureRepo.getWithType("authenticate.cfn_get_users_by_email", new Object[][]{
                {String.class, email,"p_email"},
        }, Users.class);
        if (usersEmail.isEmpty()){
            return Optional.empty();
        }else{
            return Optional.of(usersEmail.get(0));
        }
    }

    @Override
    public List<Users> getAllUsers() {
        return defaultProcedureRepo.getWithType("authentication.cfn_get_users", new Object[][]{
                {Long.class, null, "p_id"},
        }, Users.class);
    }

    @Override
    public String saveUser(Users user) {
        Object id[] = defaultProcedureRepo.executeWithType("authentication.cfn_add_edit_users", new Object[][]{
                {Long.class, user.getId(), "p_id"},
                {String.class, user.getName(), "p_name"},
                {String.class, user.getEmail(), "p_email"},
                {String.class, user.getPassword(), "p_password"},
                {String.class, user.getUserType(), "p_user_type"},
                {String.class, user.getLoginType(), "p_login_type"},
        });
        return (String) id[0].toString();
    }

    @Override
    public void deleteUsers(Long Id) {
        Object id[] = defaultProcedureRepo.executeWithType("", new Object[][]{
                {Long.class, Id, "p_id"}
        });
    }
}
