package com.dormitory.controller;

import com.dormitory.dto.CancelDTO;
import com.dormitory.dto.DormitoryDTO;
import com.dormitory.dto.PaymentDTO;
import com.dormitory.dto.ReservationDTO;
import com.dormitory.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
@CrossOrigin(origins = {"http://localhost:3000/" })
@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class RestReservationController {
    private final MemberService service;

    //======================예약 화면

    //1. 숙소 + 객실 (JOIN + DTO)
    @GetMapping("/dormitoryRoom")
    public List<DormitoryDTO> getDormitoryRoom(DormitoryDTO dormitory) throws Exception{

        ;

        return service.getDormitoryRoom(dormitory.getD_code());
    }


    //2. 구매 회원 정보 입력 받아서 POST 처리만 하면됨
    @PostMapping("/memberInfo")
    public String postMemberInfo() {
        return null;
    }

    //3-1. 예약 정보 조회  -> OK

    @GetMapping("/reservationInfo")
    public ReservationDTO getReservationInfo(ReservationDTO reservation){

        return reservation;
    }

    //3-2. 예약 정보 받아서 POST 처리
    @PostMapping("/reservationInfo")
    public String posteservationInfo(@RequestBody ReservationDTO reservation){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //db에서 가져온 모든 checkin날짜/checkout날짜
        List<ReservationDTO> DB_reservation =service.posteservationInfo(reservation.getR_code());
        //원하는 날짜의 범위
        LocalDate reservation_checkin = reservation.getReservation_checkin();
        LocalDate reservation_checkout = reservation.getReservation_checkout();

        for(int i=0;i<DB_reservation.size();i++){
            LocalDate checkinDate = DB_reservation.get(i).getReservation_checkin();
            LocalDate checkoutDate = DB_reservation.get(i).getReservation_checkout();

            for(LocalDate date = reservation_checkin; !date.isAfter(reservation_checkout);date = date.plusDays(1)){
                if((date.isEqual(checkinDate) || date.isAfter(checkinDate)) && date.isBefore(checkoutDate)){

                    return "{\" message \" : \" FAIL \"}";
                }
            }
        }
        return "{\" message \" : \" GOOD \"}";
    }



    //4. 결제 정보 저장
    @PostMapping("/payment")
    public String getPayment(PaymentDTO payment){
        return null;
    }


    //5. 시즌별 환불규정 -> OK
    @GetMapping("/cancel")
    public List<CancelDTO> getCancel(DormitoryDTO dormitory) throws Exception{
        //테스트용
        //return service.getCancelPolicy("3002534");

        return service.getCancelPolicy(dormitory.getD_code());

    }


}
