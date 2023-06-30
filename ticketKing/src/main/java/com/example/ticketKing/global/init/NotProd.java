package com.example.ticketKing.global.init;

import com.example.ticketKing.domain.Hall.entity.Hall;
import com.example.ticketKing.domain.Hall.service.HallService;
import com.example.ticketKing.domain.Member.controller.MemberController;
import com.example.ticketKing.domain.Member.entity.Member;
import com.example.ticketKing.domain.Member.service.MemberService;
import com.example.ticketKing.domain.Seat.entity.Seat;
import com.example.ticketKing.domain.Seat.service.SeatService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Profile({"local", "test"})
public class NotProd {
    @Bean
    CommandLineRunner initData(
            MemberService memberService,
            SeatService seatService,
            HallService hallService

    ) {
        return new CommandLineRunner() {
            @Override
            @Transactional
            public void run(String... args) throws Exception {
            Member memberUser1 = memberService.join("user1","1234","user1@gmail.com").getData();
            Member memberUser2 = memberService.join("user2","1234","user2@gmail.com").getData();
            Member memberUser3 = memberService.join("user3","1234","user3@gmail.com").getData();
            Member memberUser4 = memberService.join("user4","1234","user4@gmail.com").getData();

            Hall KSPO = hallService.register("KSPO",1000).getData();
            Hall OLYSDM = hallService.register("OLYSDM",1000).getData();

                 Seat seat1 = seatService.register("VIP",1,1,"valid", KSPO).getData();
                Seat seat2 = seatService.register("VIP",2,2,"valid", KSPO).getData();
                Seat seat3 = seatService.register("VIP",3,3,"valid", KSPO).getData();
                Seat seat4 = seatService.register("VIP",1,4,"valid", OLYSDM).getData();
                Seat seat5 = seatService.register("VIP",1,5,"valid", OLYSDM).getData();

//                Seat seat6 = seatService.register("VIP",2,1,"invalid").getData();
//                Seat seat7 = seatService.register("VIP",2,2,"invalid").getData();
//                Seat seat8 = seatService.register("VIP",2,3,"invalid").getData();
//                Seat seat9 = seatService.register("VIP",2,4,"invalid").getData();
//                Seat seat10 = seatService.register("VIP",2,5,"invalid").getData();
//
//                Seat seat11 = seatService.register("VIP",3,1,"valid").getData();
//                Seat seat12 = seatService.register("VIP",3,2,"valid").getData();
//                Seat seat13 = seatService.register("VIP",3,3,"valid").getData();
//                Seat seat14 = seatService.register("VIP",3,4,"valid").getData();
//                Seat seat15 = seatService.register("VIP",3,5,"valid").getData();
//
//                Seat seat16 = seatService.register("VIP",4,1,"valid").getData();
//                Seat seat17 = seatService.register("VIP",4,2,"valid").getData();
//                Seat seat18 = seatService.register("VIP",4,3,"valid").getData();
//                Seat seat19 = seatService.register("VIP",4,4,"valid").getData();
//                Seat seat20 = seatService.register("VIP",4,5,"valid").getData();
//
//                Seat seat21 = seatService.register("VIP",5,1,"valid").getData();
//                Seat seat22 = seatService.register("VIP",5,2,"valid").getData();
//                Seat seat23 = seatService.register("VIP",5,3,"valid").getData();
//                Seat seat25 = seatService.register("VIP",5,4,"valid").getData();
//                Seat seat26 = seatService.register("VIP",5,5,"valid").getData();

            }
        };
    }
}
