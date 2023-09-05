package com.example.ticketKing.global.init;

import com.example.ticketKing.domain.Hall.entity.Hall;
import com.example.ticketKing.domain.Hall.service.HallService;
import com.example.ticketKing.domain.Member.entity.Member;
import com.example.ticketKing.domain.Member.service.MemberService;
import com.example.ticketKing.domain.Seat.entity.Seat;
import com.example.ticketKing.domain.Seat.service.SeatService;
import com.example.ticketKing.domain.VirtualGame.entity.VirtualGame;
import com.example.ticketKing.domain.VirtualGame.service.VirtualGameService;
import com.example.ticketKing.domain.VirtualParticipant.entity.VirtualParticipant;
import com.example.ticketKing.domain.VirtualParticipant.service.VirtualParticipantService;
import com.example.ticketKing.domain.VirtualSeat.entity.VirtualSeat;
import com.example.ticketKing.domain.VirtualSeat.service.VirtualSeatService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile({"local", "test"})
public class NotProd {
    @Bean
    CommandLineRunner initData(
            MemberService memberService,
            SeatService seatService,
            HallService hallService,
            VirtualParticipantService virtualParticipantService,
            VirtualGameService virtualGameService,
            VirtualSeatService virtualSeatService

    ) {
        return new CommandLineRunner() {
            @Override
            @Transactional
            public void run(String... args) throws Exception {
                VirtualParticipant participant = VirtualParticipant.builder()
                        .ticketSuccess(false)
                        .build();
                virtualParticipantService.create(participant);

                VirtualGame game = VirtualGame.builder()
                        .virtualParticipant(participant)
                        .build();

                Hall KSPO = hallService.register("KSPO").getData();
                Hall OLYSDM = hallService.register("OLYSDM").getData();

                VirtualSeat seat1 = virtualSeatService.register("VIP", 1, 1, "valid", KSPO).getData();
                VirtualSeat seat2 = virtualSeatService.register("VIP", 2, 2, "valid", KSPO).getData();
                VirtualSeat seat3 = virtualSeatService.register("VIP", 3, 3, "valid", KSPO).getData();
                VirtualSeat seat4 = virtualSeatService.register("VIP", 1, 4, "valid", OLYSDM).getData();
                VirtualSeat seat5 = virtualSeatService.register("VIP", 1, 5, "valid", OLYSDM).getData();
                VirtualSeat seat6 = virtualSeatService.register("VIP", 2, 1, "valid", KSPO).getData();
                VirtualSeat seat7 = virtualSeatService.register("VIP", 5, 5, "valid", KSPO).getData();
                VirtualSeat seat8 = virtualSeatService.register("VIP", 2, 3, "valid", KSPO).getData();
                VirtualSeat seat9 = virtualSeatService.register("VIP", 2, 4, "valid", OLYSDM).getData();
                VirtualSeat seat10 = virtualSeatService.register("VIP", 2, 5, "valid", OLYSDM).getData();
                VirtualSeat seat11 = virtualSeatService.register("VIP", 5, 6, "valid", OLYSDM).getData();
                VirtualSeat seat12 = virtualSeatService.register("VIP", 5, 7, "valid", OLYSDM).getData();
                VirtualSeat seat13 = virtualSeatService.register("VIP", 6, 1, "valid", OLYSDM).getData();
                VirtualSeat seat14 = virtualSeatService.register("VIP", 6, 2, "valid", OLYSDM).getData();
                VirtualSeat seat15 = virtualSeatService.register("VIP", 6, 3, "valid", OLYSDM).getData();
                VirtualSeat seat16 = virtualSeatService.register("VIP", 6, 4, "valid", OLYSDM).getData();

                List<VirtualSeat> virtualSeats = new ArrayList<>();
                virtualSeats.add(seat1);
                virtualSeats.add(seat2);
                virtualSeats.add(seat3);
                virtualSeats.add(seat4);
                virtualSeats.add(seat5);
                virtualSeats.add(seat6);
                virtualSeats.add(seat7);
                virtualSeats.add(seat8);
                virtualSeats.add(seat9);
                virtualSeats.add(seat10);
                virtualSeats.add(seat11);
                virtualSeats.add(seat12);
                virtualSeats.add(seat13);
                virtualSeats.add(seat14);
                virtualSeats.add(seat15);
                virtualSeats.add(seat16);
                virtualGameService.create(game, virtualSeats, participant);


//                Member memberAdmin1 = Member.builder()
//                        .username("admin").password("admin").email("admin@admin.com")
//                        .authority(0)
//                        .build();
//
//                List<Member> members = List.of(
//                        Member.builder()
//                                .username("user1").password("1234").email("user1@test.com")
//                                .build(),
//                        Member.builder()
//                                .username("user2").password("1234").email("user2@test.com")
//                                .build(),
//                        Member.builder()
//                                .username("user3").password("1234").email("user3@test.com")
//                                .build()
//                );
//                memberService.create(memberAdmin1);
//                members.forEach(memberService::create);

//                Hall KSPO = hallService.register("KSPO",1000).getData();
//                Hall OLYSDM = hallService.register("OLYSDM",1000).getData();
//
//                Seat seat1 = seatService.register("VIP",1,1,"valid", KSPO).getData();
//                Seat seat2 = seatService.register("VIP",2,2,"valid", KSPO).getData();
//                Seat seat3 = seatService.register("VIP",3,3,"valid", KSPO).getData();
//                Seat seat4 = seatService.register("VIP",1,4,"valid", OLYSDM).getData();
//                Seat seat5 = seatService.register("VIP",1,5,"valid", OLYSDM).getData();
//                Seat seat6 = seatService.register("VIP",2,1,"valid",KSPO).getData();
//                Seat seat7 = seatService.register("VIP",5,5,"valid",KSPO).getData();
//                Seat seat8 = seatService.register("VIP",2,3,"valid",KSPO).getData();
//                Seat seat9 = seatService.register("VIP",2,4,"valid", OLYSDM).getData();
//                Seat seat10 = seatService.register("VIP",2,5,"valid", OLYSDM).getData();
//
//                Seat seat11 = seatService.register("VIP",5,6,"valid", OLYSDM).getData();
//                Seat seat12 = seatService.register("VIP",5,7,"valid", OLYSDM).getData();
//                Seat seat13 = seatService.register("VIP",6,1,"valid", OLYSDM).getData();
//                Seat seat14 = seatService.register("VIP",6,2,"valid", OLYSDM).getData();
//                Seat seat15 = seatService.register("VIP",6,3,"valid", OLYSDM).getData();
//                Seat seat16 = seatService.register("VIP",6,4,"valid", OLYSDM).getData();
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