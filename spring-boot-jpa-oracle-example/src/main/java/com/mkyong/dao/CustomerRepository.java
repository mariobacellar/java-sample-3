package com.mkyong.dao;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mkyong.model.Customer;

@RepositoryRestResource(collectionResourceRel = "customer", path = "customer")
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    public List<Customer> findByEmail(String email);

    @Query("select c from Customer c where c.email = :email")
    public Stream<Customer> findByEmailReturnStream(@Param("email") String email);

    public List<Customer> findByDate(Date date);

    //@Query("select c from Customer c")
    //public Stream<Customer> findAllAndStream();

    //public List<Customer> findByDateBetween(Date from, Date to);

}
