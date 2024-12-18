package ru.gb.my.market.auth.repositories;

import ru.gb.my.market.auth.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
