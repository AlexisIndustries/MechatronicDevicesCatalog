package org.example.mechatronic.repositories;
import org.example.mechatronic.model.MechatronicDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MechatronicDeviceRepository extends JpaRepository<MechatronicDevice, Long> {
    void deleteMechatronicDeviceByDeviceId(Long deviceId);
    MechatronicDevice findByDeviceId(Long deviceId);
}
