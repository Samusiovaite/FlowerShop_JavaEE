package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.User;
import lt.vu.persistence.UsersDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Users {

    @Inject
    private UsersDAO usersDAO;

    @Getter @Setter
    private User userToCreate = new User();

    @Getter
    private List<User> allUsers;

    @PostConstruct
    public void init(){
        loadAllUsers();
    }

    @Transactional
    public void createUser(){
        this.usersDAO.persist(userToCreate);
    }

    private void loadAllUsers(){
        this.allUsers = usersDAO.loadAll();
    }

    @Transactional
    public void deleteUser(Integer userId) {
        User user = usersDAO.findOne(userId);
        if (user != null) {
            usersDAO.remove(user);
            loadAllUsers();
        }
    }
}
