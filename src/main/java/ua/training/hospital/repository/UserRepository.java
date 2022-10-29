package ua.training.hospital.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ua.training.hospital.entity.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long> {
    User findByEmail(String email);

    User findByIdUser(long id);

    @Query("SELECT u FROM User u " +
            " WHERE u.role='PATIENT'")
    Page<User> findAllPatients(Pageable pageable);
}
