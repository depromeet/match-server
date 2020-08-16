package com.depromeet.match.study;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class Duration {

    private LocalDate start;
    private LocalDate end;

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }
}
