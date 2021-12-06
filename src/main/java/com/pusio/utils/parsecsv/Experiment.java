package com.pusio.utils.parsecsv;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.File;
import java.util.List;

public class Experiment {
    public static void main(String[] args) {
        BeanListProcessor<ParsingBean> rowProcessor = new BeanListProcessor<>(ParsingBean.class);
        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.detectFormatAutomatically();
        parserSettings.setProcessor(rowProcessor);
        CsvParser parser = new CsvParser(parserSettings);
        parser.parse(new File("src/main/resources/day5_experiment.input"));

        List<ParsingBean> beans = rowProcessor.getBeans();
        for (ParsingBean bean : beans) {
            System.out.println(bean);
        }

    }
}
