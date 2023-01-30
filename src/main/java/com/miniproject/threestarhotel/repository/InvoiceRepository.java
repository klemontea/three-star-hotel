package com.miniproject.threestarhotel.repository;

import com.miniproject.threestarhotel.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}