package spring.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.entities.Contact;

public interface ContactRepo extends JpaRepository<Contact, Integer> {
    Contact findByEmail(String email);
    Contact findByPhoneNumber(String phoneNumber);
}
