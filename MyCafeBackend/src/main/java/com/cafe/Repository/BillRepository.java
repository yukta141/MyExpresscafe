package com.cafe.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cafe.model.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

}
