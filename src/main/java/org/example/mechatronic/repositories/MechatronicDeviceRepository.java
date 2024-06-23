package org.example.mechatronic.repositories;
import org.example.mechatronic.model.MechatronicDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MechatronicDeviceRepository extends JpaRepository<MechatronicDevice, Long> {
}
