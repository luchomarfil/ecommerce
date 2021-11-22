package lmarfil.ecommerce.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import lmarfil.ecommerce.model.Producto;

@Repository
public interface ProductoRepository extends PagingAndSortingRepository<Producto, String> {

}
