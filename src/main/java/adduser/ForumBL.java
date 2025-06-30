    package adduser;
    
    public class ForumBL {
        ForumDAOInterface dao;
        // API Usuarios

        public ForumBL(ForumDAOInterface dao) {
            this.dao = dao;
        }

        public User addUser(String id, String name, String tel) throws NullParameterException, UserNotFoundException {
            if (id == null || name == null)
                throw new NullParameterException("id or name is null");
            if (!dao.existUserDAO(id)) {
                dao.addUserDAO(id, name, tel);
                return dao.getUserDAO(id);
            } else
                throw new UserNotFoundException("id no in DB");
        }

    }
