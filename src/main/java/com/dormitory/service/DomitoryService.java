package com.dormitory.service;

import com.dormitory.dto.DormitoryDTO;
import com.dormitory.dto.RoomDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DomitoryService {

    public List<RoomDTO> getRoom() throws Exception;

}
