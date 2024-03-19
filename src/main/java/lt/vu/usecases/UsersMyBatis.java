package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mybatis.dao.UserMapper;
import lt.vu.mybatis.model.User;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class UsersMyBatis {
    @Inject
    private UserMapper userMapper;

    @Getter
    private List<User> allUsers;

    @Getter @Setter
    private User userToCreate = new User();

    @PostConstruct
    public void init() {
        this.loadAllUsers();
    }

    private void loadAllUsers() {
        this.allUsers = userMapper.selectAll();
    }

    @Transactional
    public String createUser() {
        userMapper.insert(userToCreate);
        return "/myBatis/users-products?faces-redirect=true";
    }
}
