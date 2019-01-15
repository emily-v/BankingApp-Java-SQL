package base.data;

import base.pojos.User;

public interface IUserDao {
    User getUserByUsername(String username);
}
