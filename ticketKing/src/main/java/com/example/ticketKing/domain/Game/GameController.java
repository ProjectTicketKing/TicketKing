package com.example.ticketKing.domain.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class GameController {

    @GetMapping("/usr/{env}/concert/{hall}")
    public String showRealGamePage(
            @PathVariable("hall") String hall,
            @PathVariable("env") String env,
            Model model) {

        model.addAttribute("env", env);
        model.addAttribute("hallValue", hall);

        if(env.equals("realGame")){
            return "usr/concert/concertVer2";
        } else if (env.equals("virtualGame")) {
            return "usr/concert/concert";
        }
        return "usr/concert/concert";
    }

}
