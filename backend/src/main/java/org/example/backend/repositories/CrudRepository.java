package org.example.backend.repositories;


import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CrudRepository<T, ID> extends Repository<T, ID> {

    <S extends T> S save(S entity);

//    <S extends T> List<S> saveAll(List<S> entities);
//
    Optional<T> findById(ID id);
//
//    boolean existsById(ID id);

    List<T> findAll();

//    List<T> findAllById(List<ID> ids);
//
//    int count();
//
//    void deleteById(ID id);
//
//    void delete(T entity);
//
//    void deleteAll(List<? extends T> entities);
//
//    void deleteAll();
}
