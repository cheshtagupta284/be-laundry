package com.cheshtagupta.laundry;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClothRepository extends JpaRepository<Cloth, Integer> {
    List<Cloth> findAllByUser(User user);
}
