package it.itsincom.webdevd.persistence;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import it.itsincom.webdevd.persistence.model.ApplicationUser;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<ApplicationUser, Long> {

    public ApplicationUser authenticate(String username, String password) {
        ApplicationUser applicationUser = findByUsername(username);
        if (applicationUser != null) {
            boolean matches = BcryptUtil.matches(password, applicationUser.getPassword());
            System.out.println("password: " + password);
            System.out.println("applicationUserPW: " + applicationUser.getPassword());
            System.out.println("matches: " + matches);
            if (matches) {
                return applicationUser;
            } else {
                return null;
            }
        }
        return null;
    }

    public ApplicationUser findByUsername(String username) {
        return find(
                "SELECT u from ApplicationUser u where " +
                "u.username = :username ",
                Parameters.with("username", username)
        ).firstResult();
    }
}
