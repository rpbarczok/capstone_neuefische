package org.example.backend.repositories;


import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@NoRepositoryBean
public interface CrudRepository<T, ID> extends Repository<T, ID> {

    <S extends T> S save(S entity);
//
//    <S extends T> List<S> saveAll(Iterable<S> entities);

    Optional<T> findById(ID id);


//    boolean existsById(ID id);

    Iterable<T> findAll();

//    Iterable<T> findAllById(Iterable<ID> ids);

//    int count();
//
//    void deleteById(ID id);
//
//    void delete(T entity);
//
//    void deleteAll(Iterable<? extends T> entities);
//
//    void deleteAll();
}
