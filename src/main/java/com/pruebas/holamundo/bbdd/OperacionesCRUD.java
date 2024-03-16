package com.pruebas.holamundo.bbdd;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OperacionesCRUD {
	int count();

	int save(Book book);

	int update(Book book);

	int deleteById(Long id);

	List<Book> findAll();

	List<Book> findByNameAndPrice(String name, BigDecimal price);

	Optional<Book> findById(Long id);

	String getNameById(Long id);

}
