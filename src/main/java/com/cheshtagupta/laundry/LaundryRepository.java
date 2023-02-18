package com.cheshtagupta.laundry;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LaundryRepository extends JpaRepository<Laundry, Integer> {
    List<Laundry> findAllByUser(User user);
}
