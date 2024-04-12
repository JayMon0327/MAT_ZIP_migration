package MATZIP_ver3.repository;

import MATZIP_ver3.domain.Product;
import MATZIP_ver3.domain.order.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
