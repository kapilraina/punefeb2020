package com.ms.training.SimpleWebAuditMS;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditEventRepository extends JpaRepository<AuditEvent, String>{

}
