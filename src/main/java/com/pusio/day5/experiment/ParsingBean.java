package com.pusio.day5.experiment;

import com.univocity.parsers.annotations.Parsed;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ParsingBean {
        @Parsed(index = 0)
        private Integer fromX;

        @Parsed(index = 1)
        private Integer fromY;

        @Parsed(index = 3)
        private Integer toX;

        @Parsed(index = 4)
        private Integer toY;

}
