package com.smartshine.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalendarController {

    @GetMapping("/calendar")
    public String showCalendar(Model model) {
        YearMonth currentMonth = YearMonth.now();
        List<List<LocalDate>> calendar = generateCalendar(currentMonth);
        model.addAttribute("calendar", calendar);
        model.addAttribute("month", currentMonth.getMonth());
        model.addAttribute("year", currentMonth.getYear());
        return "calendar";
    }

    private List<List<LocalDate>> generateCalendar(YearMonth month) {
        List<List<LocalDate>> weeks = new ArrayList<>();
        LocalDate firstOfMonth = month.atDay(1);
        LocalDate start = firstOfMonth.minusDays(firstOfMonth.getDayOfWeek().getValue() % 7);

        for (int i = 0; i < 6; i++) { // максимум 6 недель в месяце
            List<LocalDate> week = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
                week.add(start.plusDays(i * 7 + j));
            }
            weeks.add(week);
        }

        return weeks;
    }
}
