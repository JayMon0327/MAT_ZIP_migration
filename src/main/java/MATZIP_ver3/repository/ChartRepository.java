package MATZIP_ver3.repository;

import MATZIP_ver3.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChartRepository extends JpaRepository<Payment, Long> {

}
